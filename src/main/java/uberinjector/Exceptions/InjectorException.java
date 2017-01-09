package uberinjector.Exceptions;

public class InjectorException extends Exception {

    public InjectorException(String format, String... variables) {
        super(String.format(format, (Object[]) variables));
    }
}
