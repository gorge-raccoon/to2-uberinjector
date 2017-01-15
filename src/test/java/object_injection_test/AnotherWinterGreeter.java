package object_injection_test;

import uberinjector.Annotations.Inject;

public class AnotherWinterGreeter implements IWinterGreeter {
    private String greeting;

    @Inject
    public AnotherWinterGreeter(@Russell String greeting)
    {
        this.greeting = greeting;
    }

    public String getGreeting()
    {
        return greeting;
    }
}