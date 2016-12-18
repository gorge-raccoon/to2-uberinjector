package uberinjector;

public class BoundInstanceGetter {
    private final InstanceAssembler instanceAssembler;

    public BoundInstanceGetter(InstanceAssembler instanceAssembler)
    {
        this.instanceAssembler = instanceAssembler;
    }

    public <T> T getInstance(Class<T> cls, Class<?> implementation) throws InjectorException {
        // Bound class: return new implementation instance
        T instance;
        try {
            instance = cls.cast(instanceAssembler.assembleInstance(implementation));
        } catch (Exception e) {
            throw new InjectorException("Cannot instantiate %s (bound to %s): %s", implementation.getName(), cls.getName(), e.toString());
        }
        return instance;
    }
}
