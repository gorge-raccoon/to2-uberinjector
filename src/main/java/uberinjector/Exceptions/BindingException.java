package uberinjector.Exceptions;

public class BindingException extends InjectorException {
    public static final String message = "Cannot bind %s to %s: %s is neither an interface nor an abstract class.";

    public BindingException(Class<?> iface, Class<?> cls) {
        super(message, iface.getName(), cls.getName(), iface.getName());
    }
}
