package uberinjector;

public class UberInjector {
    private SingletonsMap singletonsMap;
    private InstanceAssembler instanceAssembler;
    private ImplementationsMap implementationsMap;
    private BoundInstanceGetter boundInstanceGetter;
    private UnboundInstanceGetter unboundInstanceGetter;

    public UberInjector() {
        this.singletonsMap = new SingletonsMap();
        this.instanceAssembler = new InstanceAssembler(this, singletonsMap);
        this.implementationsMap = new ImplementationsMap(singletonsMap);
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
