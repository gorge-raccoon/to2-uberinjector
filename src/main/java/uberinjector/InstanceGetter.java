package uberinjector;

public abstract class InstanceGetter {
    protected final InstanceAssembler instanceAssembler;

    public InstanceGetter(InstanceAssembler instanceAssembler) {
        this.instanceAssembler = instanceAssembler;
    }
}
