package named_test;

import org.junit.Test;
import uberinjector.InjectorException;
import uberinjector.UberInjector;
import tests_helper_classes.*;

import static org.junit.Assert.assertEquals;

public class NamedTest {

    @Test
    public void testHanukkahPorter() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(WinterGreeter.class, ChristmasGreeter.class);
        injector.bind(WinterGreeter.class, HanukkahGreeter.class, Hanukkah.class);
        injector.bind(Person.class, Receptionist.class);
        Person person = injector.getInstance(Person.class);
        assertEquals("Hanukkah Sameach!", person.welcome());
        assertEquals("Does receptionist stuff", person.getAction());
    }
}
