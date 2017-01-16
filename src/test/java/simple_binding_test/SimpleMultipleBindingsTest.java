package simple_binding_test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tests_helper_classes.*;
import uberinjector.Exceptions.InjectorException;
import uberinjector.Exceptions.InstantiationException;
import uberinjector.UberInjector;

import static org.junit.Assert.assertEquals;

public class SimpleMultipleBindingsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private UberInjector injector = new UberInjector();

    @Test
    public void testRedSquare() throws InjectorException {
        injector.bind(Color.class, Red.class);
        injector.bind(Shape.class, Square.class);
        Figure figure = injector.getInstance(Figure.class);
        assertEquals("I'm a red square.", figure.getDescription());
    }

    @Test
    public void testGreenCircle() throws InjectorException {
        injector.bind(Color.class, Green.class);
        injector.bind(Shape.class, Circle.class);
        Figure figure = injector.getInstance(Figure.class);
        assertEquals("I'm a green circle.", figure.getDescription());
    }

    @Test
    public void testMissingBindings() throws InjectorException {
        thrown.expect(InstantiationException.class);
        Figure figure = injector.getInstance(Figure.class);
    }

}
