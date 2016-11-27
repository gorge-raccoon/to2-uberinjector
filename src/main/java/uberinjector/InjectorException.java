package uberinjector;

public class InjectorException extends Exception {
    InjectorException(String format, String... variables) {
        super(String.format(format, (Object[]) variables));
    }
}
