package singleton_test;

import org.junit.Test;
import uberinjector.Annotations.Singleton;
import uberinjector.Exceptions.InjectorException;
import uberinjector.UberInjector;

import static org.junit.Assert.assertSame;

public class SingletonTest {
    @Test
    public void testUnboundSingleton() throws InjectorException {
        UberInjector injector = new UberInjector();
        SingletonClass a = injector.getInstance(SingletonClass.class);
        SingletonClass b = injector.getInstance(SingletonClass.class);
        assertSame(a, b);
    }

    @Test
    public void testBoundSingleton() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(SomeInterface.class, SingletonClass.class);
        SomeInterface a = injector.getInstance(SomeInterface.class);
        SomeInterface b = injector.getInstance(SomeInterface.class);
        assertSame(a, b);
    }

    @Test
    public void testBoundEagerSingleton() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(SomeInterface.class, EagerSingletonClass.class);
        injector.initializeEagerSingletons();
        SomeInterface a = injector.getInstance(SomeInterface.class);
        SomeInterface b = injector.getInstance(SomeInterface.class);
        assertSame(a, b);
    }

    @Test
    public void testSingletonInjection() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(SomeInterface.class, EagerSingletonClass.class);
        injector.initializeEagerSingletons();
        SingletonUser u1 = injector.getInstance(SingletonUser.class);
        SingletonUser u2 = injector.getInstance(SingletonUser.class);
        assertSame(u1.singleton, u2.singleton);
    }

    @Test
    public void testEagerSingletonInjection() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(SomeInterface.class, EagerSingletonClass.class);
        injector.initializeEagerSingletons();
        EagerSingletonUser u1 = injector.getInstance(EagerSingletonUser.class);
        EagerSingletonUser u2 = injector.getInstance(EagerSingletonUser.class);
        assertSame(u1.singleton, u2.singleton);
    }
}
