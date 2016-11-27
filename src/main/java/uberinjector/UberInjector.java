package uberinjector;

import java.util.HashMap;
import java.util.Map;

public class UberInjector {
    private Map<Class<?>, Class<?>> implementations;

    public UberInjector() {
        implementations = new HashMap<Class<?>, Class<?>>();
    }

    public <T> T getInstance(Class<T> cls) throws InjectorException {
        T instance;
        if (implementations.containsKey(cls)) {
            // Binding defined
            Class<?> implementation = implementations.get(cls);
            try {
                instance = cls.cast(implementation.newInstance());
            } catch (InstantiationException e) {
                throw new InjectorException("Cannot instantiate %s (bound from %s). Does it have a nullary constructor?", implementation.getName(), cls.getName());
            } catch (IllegalAccessException e) {
                throw new InjectorException("Cannot access %s's nullary constructor (bound from %s).", implementation.getName(), cls.getName());
            }
        } else {
            // No binding defined
            try {
                instance = cls.newInstance();
            } catch (InstantiationException e) {
                throw new InjectorException("Cannot instantiate %s (unbound). Does it have a nullary constructor?", cls.getName());
            } catch (IllegalAccessException e) {
                throw new InjectorException("Cannot access %s's nullary constructor (unbound).", cls.getName());
            }
        }
        return instance;
    }

    public void bind(Class<?> iface, Class<?> cls) {
        implementations.put(iface, cls);
    }
}
