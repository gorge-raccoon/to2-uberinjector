package uberinjector;

public class BoundInstanceGetter extends InstanceGetter {

    public BoundInstanceGetter(InstanceAssembler instanceAssembler) {
        super(instanceAssembler);
    }

    public <T> T getInstance(Class<T> cls, Class<?> implementation) throws InjectorException {
        T instance;
        try {
            instance = cls.cast(instanceAssembler.assembleInstance(implementation));
        } catch (Exception e) {
            throw new InjectorException("Cannot instantiate %s (bound to %s): %s", implementation.getName(), cls.getName(), e.toString());
        }
        return instance;
    }
}
