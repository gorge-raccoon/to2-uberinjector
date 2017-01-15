package tests_helper_classes;

import uberinjector.Annotations.Inject;

public class Doorman implements Person {
    private WinterGreeter greeter;

    @Inject
    public Doorman(WinterGreeter greeter) {
        this.greeter = greeter;
    }

    public String welcome() {
        return this.greeter.getWinterGreetings();
    }

    public String getAction() {
        return "Opens the door";
    }
}