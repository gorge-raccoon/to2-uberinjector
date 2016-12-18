package uberinjector;

import java.lang.reflect.Modifier;

public class UnboundInstanceGetter extends InstanceGetter {

    public UnboundInstanceGetter(InstanceAssembler instanceAssembler) {
        super(instanceAssembler);
    }

    public <T> T getInstance(Class<T> cls) throws InjectorException {
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
