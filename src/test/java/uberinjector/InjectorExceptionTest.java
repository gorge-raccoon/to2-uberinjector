package uberinjector;

import org.junit.Test;
import uberinjector.Exceptions.InjectorException;

import static org.junit.Assert.assertEquals;

public class InjectorExceptionTest {

    @Test
    public void testMessageFormatting() throws InjectorException {
        try {
            throw new InjectorException("My name is %s.", InjectorException.class.getSimpleName());
        } catch (InjectorException e) {
            assertEquals("My name is InjectorException.", e.getMessage());
        }
    }

}
