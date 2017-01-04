package field_injection_test;

import method_injection_test.Ball;
import method_injection_test.*;
import method_injection_test.Color;
import method_injection_test.Size;
import org.junit.Test;
import uberinjector.InjectorException;
import uberinjector.UberInjector;

import static org.junit.Assert.assertEquals;


public class FieldInjectionTest {
    @Test
    public void testFieldInjection() throws InjectorException {
        UberInjector injector = new UberInjector();
        injector.bind(Size.class, Big.class);
        injector.bind(Color.class, Black.class);
        Ball ball = injector.getInstance(Ball.class);
        assertEquals("The ball is big and black.", ball.getDescription());
    }

}
