package uberinjector;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Modifier;

public class UberInjector {

    private InstanceAssembler instanceAssembler;
    private ImplementationsMap implementationsMap;
    private BoundInstanceGetter boundInstanceGetter;
    private UnboundInstanceGetter unboundInstanceGetter;

    public UberInjector() {

        this.instanceAssembler = new InstanceAssembler(this);
        this.implementationsMap = new ImplementationsMap();
        this.boundInstanceGetter = new BoundInstanceGetter(instanceAssembler);
        this.unboundInstanceGetter = new UnboundInstanceGetter(instanceAssembler);
    }

    public <T> T getInstance(Class<T> cls) throws InjectorException {
        T instance;

        Class<?> implementation = implementationsMap.get(cls);
        if (implementation != null) {
            // Bound class: return new implementation instance
            instance = boundInstanceGetter.getInstance(cls, implementation);
        } else {
            // Unbound class: return new cls instance
            instance = unboundInstanceGetter.getInstance(cls);
        }

        return instance;
    }

    public void bind(Class<?> iface, Class<?> cls) throws InjectorException {
        implementationsMap.bind(iface, cls);
    }

    public void bind(Class<?> iface, Class<?> cls, Class<?> name) throws InjectorException {

    }
}
