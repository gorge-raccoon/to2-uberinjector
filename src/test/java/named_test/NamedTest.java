package named_test;

import org.junit.Test;
import uberinjector.Exceptions.InjectorException;
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

    @Test
    public void hotelTest() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(WinterGreeter.class, ChristmasGreeter.class);
        injector.bind(WinterGreeter.class, HanukkahGreeter.class, Hanukkah.class);
        injector.bind(Person.class, Porter.class);
        injector.bind(Person.class, Receptionist.class, ReceptionistAnnotation.class);
        injector.bind(Hotel.class, AnotherHotel.class);
        Hotel hotel = injector.getInstance(Hotel.class);
        assertEquals("welcome to Another Hotel. Hanukkah Sameach!Does receptionist stuff", hotel.doHotelStuff());
    }

    @Test
    public void hotelTestChristmas() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(WinterGreeter.class, ChristmasGreeter.class);
        injector.bind(Person.class, Porter.class);
        injector.bind(Person.class, Receptionist.class, ReceptionistAnnotation.class);
        injector.bind(Hotel.class, AnotherHotel.class);
        Hotel hotel = injector.getInstance(Hotel.class);
        assertEquals("welcome to Another Hotel. Merry Christmas!Does receptionist stuff", hotel.doHotelStuff());
    }

    @Test
    public void namedAndNormalTest() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(WinterGreeter.class, ChristmasGreeter.class);
        injector.bind(WinterGreeter.class, HanukkahGreeter.class, Hanukkah.class);
        injector.bind(Person.class, Porter.class);
        injector.bind(Person.class, Receptionist.class, ReceptionistAnnotation.class);
        injector.bind(Hotel.class, AnotherHotel.class);
        Hotel hotel = injector.getInstance(Hotel.class);
        Hotel hiltonHotel = injector.getInstance(HiltonHotel.class);
        assertEquals("welcome to Another Hotel. Hanukkah Sameach!Does receptionist stuff", hotel.doHotelStuff());
        assertEquals("welcome to Hilton Hotel. Merry Christmas!Takes your luggage", hiltonHotel.doHotelStuff());
    }

}
