package uberinjector;

import java.util.HashMap;
import java.util.Map;

public class SingletonsMap {
    private Map<Class<?>, Object> instances;


    public SingletonsMap() {
        instances = new HashMap<>();
    }

    public Object get(Class<?> cls) throws IllegalAccessException, InstantiationException {
        Object instance = instances.get(cls);
        if (instance == null) {
            instance = cls.newInstance();
            instances.put(cls, instance);
        }
        return instance;
    }


}
