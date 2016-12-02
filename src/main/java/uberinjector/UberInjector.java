package uberinjector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;

public class UberInjector {
    private Map<Class<?>, Class<?>> implementations;

    public UberInjector() {
        implementations = new HashMap<>();
    }

    public <T> T getInstance(Class<T> cls) throws InjectorException {
        T instance;

        Class<?> implementation = implementations.get(cls);
        if (implementation != null) {
            // Bound class: return new implementation instance
            try {
                instance = cls.cast(assembleInstance(implementation));
            } catch (InjectorException e) {
                throw new InjectorException("Cannot instantiate %s (bound to %s): %s", implementation.getName(), cls.getName(), e.toString());
            }
        } else {
            // Unbound class: return new cls instance
            int clsModifiers = cls.getModifiers();
            if (Modifier.isInterface(clsModifiers)) {
                throw new InjectorException("Cannot instantiate %s: it's an interface.", cls.getName());
            }
            if (Modifier.isAbstract(clsModifiers)) {
                throw new InjectorException("Cannot instantiate %s: it's an abstract class.", cls.getName());
            }
            try {
                instance = cls.cast(assembleInstance(cls));
            } catch (Exception e) {
                throw new InjectorException("Cannot instantiate %s (unbound): %s", cls.getName(), e.toString());
            }
        }

        return instance;
    }

    private Object assembleInstance(Class<?> implementation) throws InjectorException {
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
        for (int i=0; i<argTypes.length; i++) {
            argValues[i] = getInstance((Class) argTypes[i]);
        }

        // Return an instance
        try {
            return constructor.newInstance(argValues);
        } catch (Exception e) {
            throw new InjectorException("Caught %s.", e.toString());
        }
    }

    public void bind(Class<?> iface, Class<?> cls) throws InjectorException {
        int ifaceModifiers = iface.getModifiers();
        if (Modifier.isInterface(ifaceModifiers) || Modifier.isAbstract(ifaceModifiers)) {
            implementations.put(iface, cls);
        } else {
            throw new InjectorException("Cannot bind %s to %s: %s is neither an interface nor an abstract class.", iface.getName(), cls.getName(), iface.getName());
        }
    }
}
