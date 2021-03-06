package uberinjector;

import uberinjector.Annotations.Singleton;
import uberinjector.Exceptions.BindingException;
import uberinjector.Exceptions.InjectorException;
import uberinjector.Utils.PrimitivesMapper;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ImplementationsMap {
    private Map<Class<?>, Class<?>> implementations;
    private Map<Class<?>, Object> implementationObjects;
    private SingletonsMap singletonsMap;


    public ImplementationsMap(InstanceAssembler instanceAssembler) {
        implementations = new HashMap<>();
        implementationObjects = new HashMap<>();
        this.singletonsMap = new SingletonsMap(instanceAssembler);
    }

    public Object get(Class<?> cls) throws InjectorException {
        if (cls.isPrimitive()) {
            cls = PrimitivesMapper.getBox(cls);
        }

        if (cls.getAnnotation(Singleton.class) != null)
        {
            return singletonsMap.get(cls);
        }

        if (implementationObjects.containsKey(cls)) {
            return implementationObjects.get(cls);
        }

        Class<?> impl = implementations.get(cls);
        if(impl == null)
        {
            return null;
        }
        if (impl.getAnnotation(Singleton.class) != null)
        {
            return singletonsMap.get(impl);
        }
        int clsModifiers = impl.getModifiers();
        if (Modifier.isInterface(clsModifiers) || Modifier.isAbstract(clsModifiers)) {
            return this.get(impl);
        }
        return impl;
    }

    public void bind(Class<?> iface, Class<?> cls) throws InjectorException {
        int ifaceModifiers = iface.getModifiers();
        if (Modifier.isInterface(ifaceModifiers) || Modifier.isAbstract(ifaceModifiers)) {
            implementations.put(iface, cls);
        } else {
            throw new BindingException(iface, cls);
        }

        // Create an instance of an eager singleton if necessary
        Singleton singletonAnnotation = cls.getAnnotation(Singleton.class);
        if (singletonAnnotation != null && singletonAnnotation.eager()) {
            try {
                singletonsMap.register(cls);
            } catch (Exception e) {
                throw new InjectorException(String.format("Cannot insantiate an eager singleton %s bound to %s.", cls.getName(), iface.getName()));
            }
        }
    }

    public void bind(Class<?> cls, Object object) throws InjectorException {
        if (cls.isPrimitive()) {
            cls = PrimitivesMapper.getBox(cls);
        }
        if (object.getClass() == cls) {
            implementationObjects.put(cls, object);
        }
    }

    public void initializeEagerSingletons() throws InjectorException {
        singletonsMap.initiateEagerSingletonClasses();
    }
}
