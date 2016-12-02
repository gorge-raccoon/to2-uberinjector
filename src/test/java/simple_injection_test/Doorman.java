package simple_injection_test;

import uberinjector.Inject;

public class Doorman {
    private WinterGreeter greeter;

    @Inject
    public Doorman(WinterGreeter greeter) {
        this.greeter = greeter;
    }

    public String welcome() {
        return this.greeter.getWinterGreetings();
    }
}
