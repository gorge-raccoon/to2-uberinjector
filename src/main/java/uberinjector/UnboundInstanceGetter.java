package uberinjector;

import java.lang.reflect.Modifier;

public class UnboundInstanceGetter {

    private final InstanceAssembler instanceAssembler;

    public UnboundInstanceGetter(InstanceAssembler instanceAssembler)
    {
        this.instanceAssembler = instanceAssembler;
    }

    public <T> T getInstance(Class<T> cls) throws InjectorException
    {
        // Unbound class: return new cls instance
        int clsModifiers = cls.getModifiers();
        if (Modifier.isInterface(clsModifiers)) {
            throw new InjectorException("Cannot instantiate %s: it's an interface.", cls.getName());
        }
        if (Modifier.isAbstract(clsModifiers)) {
            throw new InjectorException("Cannot instantiate %s: it's an abstract class.", cls.getName());
        }
        try {
            T instance = cls.cast(instanceAssembler.assembleInstance(cls));
            return instance;
        } catch (Exception e) {
            throw new InjectorException("Cannot instantiate %s (unbound): %s", cls.getName(), e.toString());
        }
    }

}
