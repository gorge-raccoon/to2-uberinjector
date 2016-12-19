package uberinjector;

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


    public <T> Class<?> get(Class<T> cls, Class<?> annotation) throws InjectorException {
        if(!this.namedMap.containsKey(annotation))
        {
            throw new InjectorException("No bindings for annotation: " + annotation.getName() + " for class:" + cls.getName());
        }

        ImplementationsMap mapForAnnotation = namedMap.get(annotation);
        try{
            return mapForAnnotation.get(cls);
        }
        catch(Exception e)
        {
            throw new InjectorException("No bindings for annotation: " + annotation.getName() + " for class:" + cls.getName());
        }

    }
}
