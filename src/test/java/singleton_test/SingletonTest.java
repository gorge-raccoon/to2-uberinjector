package singleton_test;

import org.junit.Test;
import tests_helper_classes.Red;
import uberinjector.InjectorException;
import uberinjector.UberInjector;

import static org.junit.Assert.assertEquals;

public class SingletonTest {
    @Test
    public void testSingleton() throws InjectorException {
        UberInjector injector = new UberInjector();
        SingletonClass a = injector.getInstance(SingletonClass.class);
        SingletonClass b = injector.getInstance(SingletonClass.class);
        assertEquals(a, b);
    }
}
