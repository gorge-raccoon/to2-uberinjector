package no_bindings_test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uberinjector.InjectorException;
import uberinjector.UberInjector;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class NoBindingsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private UberInjector injector = new UberInjector();

    @Test
    public void testGetString() throws InjectorException {
        String string = injector.getInstance(String.class);
        assertEquals(string, "");
    }

    @Test
    public void testGetInterface() throws InjectorException {
        thrown.expect(InjectorException.class);
        thrown.expectMessage("Cannot instantiate java.util.Map: it's an interface");
        injector.getInstance(Map.class);
    }

}
