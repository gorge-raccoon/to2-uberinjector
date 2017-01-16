package uberinjector;

import uberinjector.Exceptions.InjectorException;
import uberinjector.Exceptions.NoBindingException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


public class NamedImplementationsMap {

    private Map<Class<?>, ImplementationsMap> namedMap;
    private InstanceAssembler instanceAssembler;

    public NamedImplementationsMap(InstanceAssembler instanceAssembler) {
        namedMap = new HashMap<>();
        this.instanceAssembler = instanceAssembler;
    }

    public void bind(Class<?> iface, Class<?> cls, Class<?> annotation) throws InjectorException {
        if (!this.namedMap.containsKey(annotation)) {
            this.namedMap.put(annotation, new ImplementationsMap(instanceAssembler));
        }

        ImplementationsMap mapForAnnotation = namedMap.get(annotation);
        mapForAnnotation.bind(iface, cls);
    }

    public void bind(Class<?> cls, Object object, Class<?> annotation) throws InjectorException {
        if (!this.namedMap.containsKey(annotation)) {
            this.namedMap.put(annotation, new ImplementationsMap(instanceAssembler));
        }

        ImplementationsMap mapForAnnotation = namedMap.get(annotation);
        mapForAnnotation.bind(cls, object);
    }


    public Object get(Class<?> cls, Class<?> annotation) throws InjectorException {
        if (!this.namedMap.containsKey(annotation)) {
            return null; //throw new NoBindingException(annotation, cls);
        }

        ImplementationsMap mapForAnnotation = namedMap.get(annotation);
        try {
            return mapForAnnotation.get(cls);
        } catch (Exception e) {
            throw new NoBindingException(annotation, cls);
        }

    }

    public void InitializeEagerSingletons() throws InjectorException {
        for(ImplementationsMap map: namedMap.values())
        {
            map.InitializeEagerSingletons();
        }
    }
}
