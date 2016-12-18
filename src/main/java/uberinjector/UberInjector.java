package uberinjector;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Modifier;

public class UberInjector {

    private InstanceAssembler instanceAssembler;
    private ImplementationsMap implementationsMap;

    public UberInjector() {

        this.instanceAssembler = new InstanceAssembler(this);
        this.implementationsMap = new ImplementationsMap();
    }

    public <T> T getInstance(Class<T> cls) throws InjectorException {
        T instance;

        Class<?> implementation = implementationsMap.get(cls);
        if (implementation != null) {
            // Bound class: return new implementation instance
            try {
                instance = cls.cast(instanceAssembler.assembleInstance(implementation));
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
                instance = cls.cast(instanceAssembler.assembleInstance(cls));
            } catch (Exception e) {
                throw new InjectorException("Cannot instantiate %s (unbound): %s", cls.getName(), e.toString());
            }
        }

        return instance;
    }

    public void bind(Class<?> iface, Class<?> cls) throws InjectorException {
        implementationsMap.bind(iface, cls);
    }

    public void bind(Class<?> iface, Class<?> cls, Class<?> named) throws InjectorException {
        throw new NotImplementedException();
    }
}
