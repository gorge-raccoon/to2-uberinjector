package uberinjector.Exceptions;

public class InvalidAnnotationException extends InjectorException {
    public static final String message = "Annotation %s has to be annotated with @Named.";

    public InvalidAnnotationException(Class<?> cls) {
        super(message, cls.getName());
    }
}
