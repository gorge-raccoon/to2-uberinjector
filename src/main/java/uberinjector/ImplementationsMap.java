package uberinjector;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ImplementationsMap {
    private Map<Class<?>, Class<?>> implementations;


    public ImplementationsMap()
    {
        implementations = new HashMap<>();
    }

    public Class<?> get(Class<?> cls)
    {
        return implementations.get(cls);
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
