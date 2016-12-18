package tests_helper_classes;

import uberinjector.Inject;

public class Porter implements Person {
    private WinterGreeter greeter;

    @Inject
    public Porter(WinterGreeter greeter) {
        this.greeter = greeter;
    }

    public String welcome() {
        return this.greeter.getWinterGreetings();
    }

    public String getAction() {
        return "Takes your luggage";
    }
}