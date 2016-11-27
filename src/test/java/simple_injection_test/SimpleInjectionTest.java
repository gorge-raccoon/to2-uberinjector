package simple_injection_test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uberinjector.InjectorException;
import uberinjector.UberInjector;

import static org.junit.Assert.assertEquals;

public class SimpleInjectionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private UberInjector injector = new UberInjector();

    @Test
    public void testChristmas() throws InjectorException {
        injector.bind(WinterGreeter.class, ChristmasGreeter.class);
        GreeterUser greeter = injector.getInstance(GreeterUser.class);
        assertEquals("Merry Christmas!", greeter.welcome());
    }

    @Test
    public void testHanukkah() throws InjectorException {
        injector.bind(WinterGreeter.class, HanukkahGreeter.class);
        GreeterUser greeter = injector.getInstance(GreeterUser.class);
        assertEquals("Merry Christmas!", greeter.welcome());
    }

    @Test
    public void testMissingBinding() throws InjectorException {
        thrown.expect(InjectorException.class);
        thrown.expectMessage("Cannot instantiate simple_binding_test.WinterGreeter (unbound). Does it have a nullary constructor?");
        GreeterUser greeter = injector.getInstance(GreeterUser.class);
    }

}
