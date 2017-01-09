package nested_bindings_test;

import org.junit.Test;
import uberinjector.Exceptions.InjectorException;
import uberinjector.UberInjector;

import static org.junit.Assert.assertEquals;

public class NestedBindingsTest {
    @Test
    public void testNestedBindings() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(Animal.class, Mammal.class);
        injector.bind(Mammal.class, Dog.class);
        Animal animal = injector.getInstance(Animal.class);
        assertEquals(animal.getClass(), Dog.class);
    }
}
