package uberinjector;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;


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
        Object[] argValues = getArgValues(constructor);

        // Create a new instance
        Object instance = constructor.newInstance(argValues);

        invokeSettersOn(instance);
        initiateFieldsOn(instance);

        return instance;
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

    private Object[] getArgValues(Executable executable) throws InjectorException {
        Parameter[] parameters = executable.getParameters();
        Annotation[][] annotations = executable.getParameterAnnotations();
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
        return argValues;
    }

    private void initiateFieldsOn(Object instance) throws InjectorException, IllegalAccessException {
        Class<?> cls = instance.getClass();
        for(Field field: cls.getFields()) {
            //Annotation[] annotations = field.getAnnotations();
            if(field.getAnnotation(Inject.class) != null)
            {
                Class<?> fieldCls = field.getType();
                Object fieldValue = null;

                for(Annotation annotation: field.getAnnotations()){
                    if(annotation.annotationType() != Inject.class)
                    {
                        fieldValue = uberInjector.getInstance(fieldCls, annotation.annotationType());
                    }
                }
                if(fieldValue == null)
                {
                    fieldValue = uberInjector.getInstance(fieldCls);
                }

                field.set(instance, fieldValue);
            }

        }
    }

    private void invokeSettersOn(Object instance) throws InjectorException, InvocationTargetException, IllegalAccessException {
        //TODO singletons

        // Collect all @Inject setters
        List<Method> injectionMethods = new ArrayList();
        for (Method method : instance.getClass().getMethods()) {
            Annotation injectAnnotation = method.getAnnotation(Inject.class);
            if (injectAnnotation != null) {
                // This is a setter method
                injectionMethods.add(method);
            }
        }

        // Invoke the setters
        for (Method method : injectionMethods) {
            Object argValues[] = getArgValues(method);
            method.invoke(instance, argValues);
        }
    }
}
