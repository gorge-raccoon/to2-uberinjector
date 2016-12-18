package uberinjector;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;


public class InstanceAssembler {

    private final UberInjector uberInjector;

    public InstanceAssembler(UberInjector uberInjector) {
        this.uberInjector = uberInjector;
    }

    public Object AssembleInstance(Class<?> implementation) throws InjectorException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // Get an @Inject constructor
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

        // If there's neither @Inject nor a no-argument constructor, throw an exception
        if (constructor == null) {
            throw new InjectorException("Class %s has neither @Inject nor a no-argument constructor.", implementation.getName());
        }

        // Prepare it's arguments
        Type[] argTypes = constructor.getGenericParameterTypes();
        Object[] argValues = new Object[argTypes.length];
        for (int i = 0; i < argTypes.length; i++) {
            argValues[i] = uberInjector.getInstance((Class) argTypes[i]);
        }

        // Return an instance
        return constructor.newInstance(argValues);
    }
}
