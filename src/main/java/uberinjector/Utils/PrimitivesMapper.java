package uberinjector.Utils;

import java.util.HashMap;

public class PrimitivesMapper {
    private static HashMap<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();
    static
    {
        map.put(byte.class, Byte.class);
        map.put(short.class, Short.class);
        map.put(char.class, Character.class);
        map.put(int.class, Integer.class);
        map.put(long.class, Long.class);
        map.put(float.class, Float.class);
        map.put(double.class, Double.class);
        map.put(boolean.class, Boolean.class);
    }

    public static Class<?> getBox(Class<?> cls)
    {
        return map.get(cls);
    }
}
