package object_injection_test;

import uberinjector.Inject;

public class AnotherWinterGreeter implements IWinterGreeter {
    private String greeting;

    @Inject
    public AnotherWinterGreeter(@Russell String greeting)
    {
        this.greeting = greeting;
    }

    public String GetGreeting()
    {
        return greeting;
    }
}
