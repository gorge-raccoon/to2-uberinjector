package uberinjector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;


public class InstanceAssembler {

    private final UberInjector uberInjector;
    private SingletonsMap singletonsMap;


    public InstanceAssembler(UberInjector uberInjector, SingletonsMap singletonsMap) {
        this.uberInjector = uberInjector;
        this.singletonsMap = singletonsMap;
    }

    public Object assembleInstance(Class<?> implementation) throws InjectorException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // Get an @Inject constructor
        Constructor constructor = getConstructor(implementation);

        // If there's neither @Inject nor a no-argument constructor, throw an exception
        if (constructor == null) {
            throw new InjectorException("Class %s has neither @Inject nor a no-argument constructor.", implementation.getName());
        }

        // If it's a singleton, return it
        Annotation singletonAnnotation = implementation.getAnnotation(Singleton.class);
        if (singletonAnnotation != null) {
            return singletonsMap.get(implementation);
        }

        // Prepare the constructor's arguments
        Parameter[] parameters = constructor.getParameters();
        Annotation[][] annotations = constructor.getParameterAnnotations();
        Object[] argValues = new Object[parameters.length];
        for (int i=0; i<parameters.length; i++) {
            Parameter parameter = parameters[i];
            Class<?> type = parameter.getType();

            for(int j=0; j<annotations[i].length && argValues[i] == null; j++)
            {
                try
                {
                    argValues[i] = uberInjector.getInstance(type, annotations[i][j].annotationType());
                }
                catch(Exception e)
                {
                    //todo - how to handle other annotations on parameters
                }

            }
            if(argValues[i] == null)
            {
                argValues[i] = uberInjector.getInstance(type);
            }
        }

        // Return a new instance
        Object instance = constructor.newInstance(argValues);

        invokeSettersOn(instance);

        return instance;
    }

    private void invokeSettersOn(Object instance) {
        //TODO
    }

    private Constructor getConstructor(Class<?> implementation) {
        Constructor constructor = null;
        for (Constructor c : implementation.getConstructors()) {
            if (c.getAnnotation(Inject.class) != null) {
                constructor = c;
                break;
            }
        }

        // If no @Inject constructor found, find a no-argument constructor
        if (constructor == null) {
            for (Constructor c : implementation.getConstructors()) {
                if (c.getParameterCount() == 0) {
                    constructor = c;
                    break;
                }
            }
        }
        return constructor;
    }
}
