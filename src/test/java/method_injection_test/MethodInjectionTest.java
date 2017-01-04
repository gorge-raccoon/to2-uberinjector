package method_injection_test;

import org.junit.Test;
import uberinjector.InjectorException;
import uberinjector.UberInjector;

import static org.junit.Assert.assertEquals;


public class MethodInjectionTest {
    @Test
    public void testMethodInjection() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(Size.class, Big.class);
        injector.bind(Color.class, Black.class);
        Ball ball = injector.getInstance(Ball.class);
        assertEquals("The ball is big and black.", ball.getDescription());
    }

}
