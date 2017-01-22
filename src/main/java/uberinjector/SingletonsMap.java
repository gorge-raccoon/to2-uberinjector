package uberinjector;

import uberinjector.Exceptions.InjectorException;
import uberinjector.Exceptions.InstantiationException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SingletonsMap {
    private InstanceAssembler instanceAssembler;
    private HashSet<Class> classes;
    private Map<Class<?>, Object> instances;


    public SingletonsMap(InstanceAssembler instanceAssembler) {
        instances = new HashMap<>();
        classes = new HashSet<>();
        this.instanceAssembler = instanceAssembler;
    }

    public Object get(Class<?> cls) throws InjectorException {
        Object instance = instances.get(cls);
        if (instance == null) {
            try
            {
                instance = instanceAssembler.assembleInstance(cls);
                instances.put(cls, instance);
            }
            catch(IllegalAccessException| java.lang.InstantiationException|InvocationTargetException ex)
            {
                throw new InstantiationException(cls);
            }

        }
        return instance;
    }

    public void register(Class<?> cls){
        classes.add(cls);
    }



    public void initiateEagerSingletonClasses() throws InjectorException{
        for(Class<?> cls: classes) {
            try
            {
            instances.put(cls, instanceAssembler.assembleInstance(cls));
            }
            catch(IllegalAccessException| java.lang.InstantiationException|InvocationTargetException ex)
            {
                throw new InstantiationException(cls);
            }
        }
    }

}
