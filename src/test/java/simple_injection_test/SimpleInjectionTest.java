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

    @Test
    public void testChristmasDoorman() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(WinterGreeter.class, ChristmasGreeter.class);
        Doorman doorman = injector.getInstance(Doorman.class);
        assertEquals("Merry Christmas!", doorman.welcome());
    }

    @Test
    public void testHanukkahDoorman() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(WinterGreeter.class, HanukkahGreeter.class);
        Doorman doorman = injector.getInstance(Doorman.class);
        assertEquals("Hanukkah Sameach!", doorman.welcome());
    }

    @Test
    public void testNoBinding() throws InjectorException {
        thrown.expect(InjectorException.class);
        thrown.expectMessage("Cannot instantiate simple_injection_test.Doorman (unbound): uberinjector.InjectorException: Cannot instantiate simple_injection_test.WinterGreeter: it's an interface.");
        UberInjector injector = new UberInjector();
        Doorman doorman = injector.getInstance(Doorman.class);
    }

}
