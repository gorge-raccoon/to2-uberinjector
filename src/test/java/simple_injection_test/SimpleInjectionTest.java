package simple_injection_test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tests_helper_classes.*;
import uberinjector.Exceptions.InjectorException;
import uberinjector.Exceptions.InstantiationException;
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
        thrown.expect(InstantiationException.class);
        UberInjector injector = new UberInjector();
        Doorman doorman = injector.getInstance(Doorman.class);
    }

}
