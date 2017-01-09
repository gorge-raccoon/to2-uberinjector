package uberinjector;

import uberinjector.Exceptions.InjectorException;
import uberinjector.Exceptions.InstantiationException;

import java.lang.reflect.Modifier;

public class UberInjector {
    private SingletonsMap singletonsMap;
    private InstanceAssembler instanceAssembler;
    private ImplementationsMap implementationsMap;
    private NamedImplementationsMap namedImplementationsMap;

    public UberInjector() {
        this.singletonsMap = new SingletonsMap();
        this.instanceAssembler = new InstanceAssembler(this, singletonsMap);
        this.implementationsMap = new ImplementationsMap(singletonsMap);
        this.namedImplementationsMap = new NamedImplementationsMap(singletonsMap);
    }

    public <T> T getInstance(Class<T> cls) throws InjectorException {
        return getInstance(cls, null);
    }

    public <T> T getInstance(Class<T> cls, Class<?> annotation) throws InjectorException{
        T instance;

        int clsModifiers = cls.getModifiers();
        Class<?> implementation;
        if (!Modifier.isInterface(clsModifiers) && !Modifier.isAbstract(clsModifiers)) {
            implementation = cls;
        }
        else
        {
            if(annotation != null)
            {
                implementation = namedImplementationsMap.get(cls, annotation);
            }
            else
            {
                implementation = implementationsMap.get(cls);
            }
        }

        try {
            instance = cls.cast(instanceAssembler.assembleInstance(implementation));
        } catch (Exception e) {
            throw new InstantiationException(cls);
        }

        return instance;
    }

    public void bind(Class<?> iface, Class<?> cls) throws InjectorException {
        implementationsMap.bind(iface, cls);
    }

    public void bind(Class<?> iface, Class<?> cls, Class<?> annotation) throws InjectorException {
        namedImplementationsMap.bind(iface, cls, annotation);
    }
}
