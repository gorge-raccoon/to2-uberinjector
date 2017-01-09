package nested_injection_test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uberinjector.Exceptions.InjectorException;
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
        assertEquals("Merry Christmas!", person.welcome());
        assertEquals("Opens the door", person.getAction());
    }

    @Test
    public void testHanukkahPorter() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(WinterGreeter.class, HanukkahGreeter.class);
        injector.bind(Person.class, Porter.class);
        Person person = injector.getInstance(Person.class);
        assertEquals("Hanukkah Sameach!", person.welcome());
        assertEquals("Takes your luggage", person.getAction());
    }

    @Test
    public void hotelTest() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(WinterGreeter.class, HanukkahGreeter.class);
        injector.bind(Person.class, Porter.class);
        injector.bind(Hotel.class, HiltonHotel.class);
        Hotel hotel = injector.getInstance(Hotel.class);
        assertEquals("welcome to Hilton Hotel. Hanukkah Sameach!Takes your luggage", hotel.doHotelStuff());
    }
}
