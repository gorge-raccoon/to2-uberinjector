package uberinjector;

import uberinjector.Exceptions.InjectorException;
import uberinjector.Exceptions.NoBindingException;

import java.util.HashMap;
import java.util.Map;



public class NamedImplementationsMap {

    private Map<Class<?>, ImplementationsMap> namedMap;
    private SingletonsMap singletonsMap;

    public NamedImplementationsMap(SingletonsMap singletonMap)
    {
        namedMap = new HashMap<>();
        this.singletonsMap = singletonMap;
    }

    public void bind(Class<?> iface, Class<?> cls, Class<?> annotation) throws InjectorException {
        if(!this.namedMap.containsKey(annotation))
        {
            this.namedMap.put(annotation, new ImplementationsMap(singletonsMap));
        }

        ImplementationsMap mapForAnnotation = namedMap.get(annotation);
        mapForAnnotation.bind(iface, cls);
    }

    public void bind(Class<?> cls, Object object, Class<?> annotation) throws InjectorException {
        if(!this.namedMap.containsKey(annotation))
        {
            this.namedMap.put(annotation, new ImplementationsMap(singletonsMap));
        }

        ImplementationsMap mapForAnnotation = namedMap.get(annotation);
        mapForAnnotation.bind(cls, object);
    }


    public <T> Object get(Class<T> cls, Class<?> annotation) throws InjectorException {
        if(!this.namedMap.containsKey(annotation))
        {
            return null; //throw new NoBindingException(annotation, cls);
        }

        ImplementationsMap mapForAnnotation = namedMap.get(annotation);
        try{
            return mapForAnnotation.get(cls);
        }
        catch(Exception e)
        {
            throw new NoBindingException(annotation, cls);
        }

    }
}
