package object_injection_test;


import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
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
        assertEquals("Happy Russell's Teapot Day", test.getGreeting());
    }

    @Test
    public void testPrimitivesInjection() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(int.class, 1);
        int test = injector.getInstance(int.class);
        assertEquals(1, test);
    }

    @Test
    public void testNamedPrimitivesInjection() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(IWinterGreeter.class, YetAnotherWinterGreeter.class);
        injector.bind(String.class , "Happy Russell's Teapot Day, distance of teapot from mars in kilometers is ", Russell.class);
        injector.bind(int.class, 50000, Number.class);
        IWinterGreeter test = injector.getInstance(IWinterGreeter.class);
        assertEquals(test.getGreeting(), "Happy Russell's Teapot Day, distance of teapot from mars in kilometers is 50000");
    }

    @Test
    public void testNamedFieldObjectInjection() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(IWinterGreeter.class, RussellFieldGreeter.class);
        injector.bind(String.class, "Happy Russell's Teapot Day", Russell.class);
        IWinterGreeter test = injector.getInstance(IWinterGreeter.class);
        assertEquals("Happy Russell's Teapot Day", test.getGreeting());
    }

    @Test
    public void testNamedMethodObjectInjection() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(IWinterGreeter.class, RussellMethodGreeter.class);
        injector.bind(String.class, "Happy Russell's Teapot Day", Russell.class);
        IWinterGreeter test = injector.getInstance(IWinterGreeter.class);
        assertEquals("Happy Russell's Teapot Day", test.getGreeting());
    }

}
