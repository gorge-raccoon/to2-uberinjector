package uberinjector;

import uberinjector.Annotations.Named;
import uberinjector.Annotations.Singleton;
import uberinjector.Exceptions.InjectorException;
import uberinjector.Exceptions.InstantiationException;
import uberinjector.Exceptions.InvalidAnnotationException;
import uberinjector.Exceptions.NoBindingException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class UberInjector {
    private InstanceAssembler instanceAssembler;
    private ImplementationsMap implementationsMap;
    private NamedImplementationsMap namedImplementationsMap;

    public UberInjector() {
        this.instanceAssembler = new InstanceAssembler(this);
        this.implementationsMap = new ImplementationsMap(instanceAssembler);
        this.namedImplementationsMap = new NamedImplementationsMap(instanceAssembler);
    }

    public <T> T getInstance(Class<T> cls) throws InjectorException {
        return getInstance(cls, null);
    }

    public <T> T getInstance(Class<T> cls, Class<?> annotation) throws InjectorException {

        Object implementation = getImplementation(cls, annotation);

        return getInstanceForImplementation(cls, implementation);
    }


    public void bind(Class<?> iface, Class<?> cls) throws InjectorException {
        implementationsMap.bind(iface, cls);
    }

    public void bind(Class<?> iface, Class<?> cls, Class<?> annotation) throws InjectorException {
        if (annotation.getAnnotation(Named.class) == null) {
            throw new InvalidAnnotationException(annotation);
        }
        namedImplementationsMap.bind(iface, cls, annotation);
    }

    public void bind(Class<?> iface, Object object) throws InjectorException {
        implementationsMap.bind(iface, object);
    }

    public void bind(Class<?> iface, Object object, Class<?> annotation) throws InjectorException {
        if (annotation.getAnnotation(Named.class) == null) {
            throw new InvalidAnnotationException(annotation);
        }
        namedImplementationsMap.bind(iface, object, annotation);
    }

    private Object getImplementation(Class<?> cls, Class<?> annotation) throws InjectorException {
        Object implementation;

        int clsModifiers = cls.getModifiers();
        if (annotation != null) {
            if (annotation.getAnnotation(Named.class) == null) {
                throw new InvalidAnnotationException(annotation);
            }
            implementation = namedImplementationsMap.get(cls, annotation);
        } else {
            implementation = implementationsMap.get(cls);
        }

        if (implementation == null && !Modifier.isInterface(clsModifiers) && !Modifier.isAbstract(clsModifiers)) {
            implementation = cls;
        } else if (implementation == null) {
            if (annotation == null) {
                throw new InstantiationException(cls);
            } else {
                throw new NoBindingException(cls, annotation);
            }
        }
        return implementation;
    }

    private <T> T getInstanceForImplementation(Class<T> cls, Object implementation) throws InstantiationException {
        T instance;

        if (implementation instanceof Class) {
            try {
                    instance = cls.cast(instanceAssembler.assembleInstance((Class<T>) implementation));
                } catch (Exception e) {
                throw new InstantiationException(cls);
            }
        } else {
            instance = (T) implementation;
        }
        return instance;
    }

    public void InitializeEagerSingletons() throws InjectorException{
        implementationsMap.InitializeEagerSingletons();
        namedImplementationsMap.InitializeEagerSingletons();
    }
}
