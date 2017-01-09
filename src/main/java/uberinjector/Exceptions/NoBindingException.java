package uberinjector.Exceptions;

import java.lang.annotation.Annotation;

public class NoBindingException extends InjectorException {

    public static final String messageNoAnnotation = "No bindings for class %s";
    public static final String messageWithAnnotation = "No bindings for annotation: %s for class: %s";

    public NoBindingException(Class<?> annotation, Class<?> cls)
    {
        super(messageWithAnnotation, annotation.getName(), cls.getName());
    }

    public NoBindingException(Class<?> cls)
    {
        super(messageNoAnnotation, cls.getName());
    }
}
