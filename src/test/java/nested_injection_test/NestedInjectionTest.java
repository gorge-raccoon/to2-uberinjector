package nested_injection_test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uberinjector.InjectorException;
import uberinjector.UberInjector;
import tests_helper_classes.*;

import static org.junit.Assert.assertEquals;

public class NestedInjectionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testChristmasDoorman() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(WinterGreeter.class, ChristmasGreeter.class);
        injector.bind(Person.class, Doorman.class);
        Person person = injector.getInstance(Person.class);
        assertEquals("Merry Christmas!", person.Welcome());
        assertEquals("Opens the door", person.GetAction());
    }



}
