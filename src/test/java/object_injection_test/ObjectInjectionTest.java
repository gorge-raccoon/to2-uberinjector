package object_injection_test;


import org.junit.Test;
import uberinjector.Exceptions.InjectorException;
import uberinjector.UberInjector;

import static junit.framework.Assert.assertEquals;

public class ObjectInjectionTest {
    @Test
    public void testFieldInjection() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(String.class, "test123");
        String test = injector.getInstance(String.class);
        assertEquals("test123", test);
    }

    @Test
    public void testGreetingInjections() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(String.class, "Happy Flying Spaghetti Monster Day");
        WinterGreeter test = injector.getInstance(WinterGreeter.class);
        assertEquals("Happy Flying Spaghetti Monster Day", test.GetGreeting());
    }

    @Test
    public void testNamedGreetingInjection() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(IWinterGreeter.class, AnotherWinterGreeter.class);
        injector.bind(String.class, "Happy Russell's Teapot Day", Russell.class);
        IWinterGreeter test = injector.getInstance(IWinterGreeter.class);
        assertEquals("Happy Russell's Teapot Day", test.GetGreeting());
    }


}
