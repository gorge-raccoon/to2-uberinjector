package uberinjector.Exceptions;

public class InstantiationException extends InjectorException {
    public static final String message = "Cannot instantiate %s";

    public InstantiationException(Class<?> cls) {
        super(message, cls.getName());
    }

}
