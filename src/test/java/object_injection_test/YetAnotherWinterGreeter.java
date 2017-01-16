package object_injection_test;

import uberinjector.Annotations.Inject;

public class YetAnotherWinterGreeter implements IWinterGreeter {

    private int number;
    private String greeting;

    @Inject
    public YetAnotherWinterGreeter(@Number int number, @Russell String greeting) {
        this.number = number;
        this.greeting = greeting;
    }

    @Override
    public String getGreeting() {
        return greeting + number;
    }
}
