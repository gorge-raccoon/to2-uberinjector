package object_injection_test;

import uberinjector.Annotations.Inject;

public class RussellMethodGreeter implements IWinterGreeter {

    private String greeting;

    @Override
    public String getGreeting() {
        return greeting;
    }

    @Inject
    public void setString(@Russell String greeting) {
        this.greeting = greeting;
    }
}
