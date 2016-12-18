package singleton_test;

import org.junit.Test;
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

    @Test
    public void testEagerSingleton() throws InjectorException {
        UberInjector injector = new UberInjector();
        EagerSingletonClass a = injector.getInstance(EagerSingletonClass.class);
        EagerSingletonClass b = injector.getInstance(EagerSingletonClass.class);
        assertEquals(a, b);
    }
}
