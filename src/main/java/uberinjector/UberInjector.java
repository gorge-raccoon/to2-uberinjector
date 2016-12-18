package uberinjector;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UberInjector {
    private Map<Class<?>, Class<?>> implementations;
    private InstanceAssembler instanceAssembler;

    public UberInjector() {
        implementations = new HashMap<>();
        this.instanceAssembler = new InstanceAssembler(this);
    }

    public <T> T getInstance(Class<T> cls) throws InjectorException {
        T instance;

        Class<?> implementation = implementations.get(cls);
        if (implementation != null) {
            // Bound class: return new implementation instance
            try {
                instance = cls.cast(instanceAssembler.AssembleInstance(implementation));
            } catch (Exception e) {
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
                instance = cls.cast(instanceAssembler.AssembleInstance(cls));
            } catch (Exception e) {
                throw new InjectorException("Cannot instantiate %s (unbound): %s", cls.getName(), e.toString());
            }
        }

        return instance;
    }


    public void bind(Class<?> iface, Class<?> cls) throws InjectorException {
        int ifaceModifiers = iface.getModifiers();
        if (Modifier.isInterface(ifaceModifiers) || Modifier.isAbstract(ifaceModifiers)) {
            implementations.put(iface, cls);
        } else {
            throw new InjectorException("Cannot bind %s to %s: %s is neither an interface nor an abstract class.", iface.getName(), cls.getName(), iface.getName());
        }
    }

    public void bind(Class<?> iface, Class<?> cls, Class<?> named) throws InjectorException {
        throw new NotImplementedException();
    }
}
