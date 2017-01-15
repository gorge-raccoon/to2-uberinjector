package object_injection_test;

import uberinjector.Annotations.Inject;

public class WinterGreeter {

    private String greeting;

    @Inject
    public WinterGreeter(String greeting)
    {
        this.greeting = greeting;
    }

    public String GetGreeting()
    {
        return greeting;
    }
}
