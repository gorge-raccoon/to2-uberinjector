package uberinjector.Exceptions;

public class NoConstructorException extends InjectorException {
    public static final String message = "No valid constructor found for: %s";
    public NoConstructorException(Class<?> cls)
    {
        super(message, cls.getName());
    }
}
