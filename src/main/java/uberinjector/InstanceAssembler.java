package uberinjector;

import uberinjector.Annotations.Inject;
import uberinjector.Annotations.Named;
import uberinjector.Exceptions.InjectorException;
import uberinjector.Exceptions.NoConstructorException;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;


public class InstanceAssembler {

    private final UberInjector uberInjector;


    public InstanceAssembler(UberInjector uberInjector) {
        this.uberInjector = uberInjector;
    }

    public Object assembleInstance(Class<?> implementation) throws InjectorException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // Get an @Inject constructor
        Constructor constructor = getConstructor(implementation);

        // If there's neither @Inject nor a no-argument constructor, throw an exception
        if (constructor == null) {
            throw new NoConstructorException(implementation);
        }

        // Prepare the constructor's arguments
        Object[] argValues = getArgValues(constructor);

        // Create a new instance
        Object instance = constructor.newInstance(argValues);

        invokeSettersOn(instance);
        initiateFieldsOn(instance);

        return instance;
    }

    private Constructor getConstructor(Class<?> implementation) {
        Constructor constructor = null;
        for (Constructor c : implementation.getConstructors()) {
            if (c.getAnnotation(Inject.class) != null) {
                constructor = c;
                break;
            }
        }

        // If no @Inject constructor found, find a no-argument constructor
        if (constructor == null) {
            for (Constructor c : implementation.getConstructors()) {
                if (c.getParameterCount() == 0) {
                    constructor = c;
                    break;
                }
            }
        }
        return constructor;
    }

    private Object[] getArgValues(Executable executable) throws InjectorException {
        Parameter[] parameters = executable.getParameters();
        Annotation[][] annotations = executable.getParameterAnnotations();
        Object[] argValues = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Class<?> type = parameter.getType();

            Class<? extends Annotation> name = null;
            for (Annotation annotation : annotations[i]) {
                Annotation[] annotationAnnotations = annotation.annotationType().getAnnotations();
                for (Annotation a : annotationAnnotations) {
                    if (a instanceof Named) {
                        name = annotation.annotationType();
                        break;
                    }
                }
                if (name != null) {
                    break;
                }
            }

            if (name == null) {
                argValues[i] = uberInjector.getInstance(type);
            } else {
                argValues[i] = uberInjector.getInstance(type, name);
            }
        }
        return argValues;
    }

    private void initiateFieldsOn(Object instance) throws InjectorException, IllegalAccessException {
        Class<?> cls = instance.getClass();
        for (Field field : cls.getFields()) {
            //Annotation[] annotations = field.getAnnotations();
            if (field.getAnnotation(Inject.class) != null) {
                Class<?> fieldCls = field.getType();
                Object fieldValue = null;

                for (Annotation annotation : field.getAnnotations()) {
                    if (annotation.annotationType().getAnnotationsByType(Named.class).length > 0) {
                        fieldValue = uberInjector.getInstance(fieldCls, annotation.annotationType());
                    }
                }
                if (fieldValue == null) {
                    fieldValue = uberInjector.getInstance(fieldCls);
                }

                field.set(instance, fieldValue);
            }

        }
    }

    private void invokeSettersOn(Object instance) throws InjectorException, InvocationTargetException, IllegalAccessException {

        // Collect all @Inject setters
        List<Method> injectionMethods = new ArrayList();
        for (Method method : instance.getClass().getMethods()) {
            Annotation injectAnnotation = method.getAnnotation(Inject.class);
            if (injectAnnotation != null) {
                // This is a setter method
                injectionMethods.add(method);
            }
        }

        // Invoke the setters
        for (Method method : injectionMethods) {
            Object argValues[] = getArgValues(method);
            method.invoke(instance, argValues);
        }
    }
}
