package uberinjector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ImplementationsMap {
    private Map<Class<?>, Class<?>> implementations;
    private SingletonsMap singletonsMap;


    public ImplementationsMap(SingletonsMap singletonsMap)
    {
        implementations = new HashMap<>();
        this.singletonsMap = singletonsMap;
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

        // Create an instance of an eager singleton if necessary
        Singleton singletonAnnotation = cls.getAnnotation(Singleton.class);
        if (singletonAnnotation.eager()) {
            try {
                singletonsMap.get(cls);
            } catch (Exception e) {
                throw new InjectorException(String.format("Cannot insantiate an eager singleton %s bound to %s.", cls.getName(), iface.getName()));
            }
        }
    }
}
