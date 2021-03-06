package simple_binding_test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tests_helper_classes.ChristmasGreeter;
import tests_helper_classes.HanukkahGreeter;
import tests_helper_classes.WinterGreeter;
import uberinjector.Exceptions.InjectorException;
import uberinjector.UberInjector;

import static org.junit.Assert.assertEquals;

public class SimpleBindingTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private UberInjector injector = new UberInjector();

    @Test
    public void testChristmas() throws InjectorException {
        injector.bind(WinterGreeter.class, ChristmasGreeter.class);
        WinterGreeter greeter = injector.getInstance(WinterGreeter.class);
        assertEquals("Merry Christmas!", greeter.getWinterGreetings());
    }

    @Test
    public void testHanukkah() throws InjectorException {
        injector.bind(WinterGreeter.class, HanukkahGreeter.class);
        WinterGreeter greeter = injector.getInstance(WinterGreeter.class);
        assertEquals("Hanukkah Sameach!", greeter.getWinterGreetings());
    }

    @Test
    public void testMissingBinding() throws InjectorException {
        thrown.expect(InjectorException.class);
        thrown.expectMessage("Cannot instantiate tests_helper_classes.WinterGreeter");
        WinterGreeter greeter = injector.getInstance(WinterGreeter.class);
    }

}
