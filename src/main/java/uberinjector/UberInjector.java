package uberinjector;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class UberInjector {
    private Map<Class<?>, Class<?>> implementations;

    public UberInjector() {
        implementations = new HashMap<Class<?>, Class<?>>();
    }

    public <T> T getInstance(Class<T> cls) throws InjectorException {
        T instance;

        Class<?> implementation = implementations.get(cls);
        if (implementation != null) {
            // Bound class: return new implementation instance
            try {
                instance = cls.cast(implementation.newInstance());
            } catch (Exception e) {
                throw new InjectorException("Cannot instantiate %s (bound to %s): %s caught.", implementation.getName(), cls.getName(), e.getClass().getName());
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
                instance = cls.newInstance();
            } catch (Exception e) {
                throw new InjectorException("Cannot instantiate %s (unbound): %s caught.", cls.getName(), e.getClass().getName());
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
}
