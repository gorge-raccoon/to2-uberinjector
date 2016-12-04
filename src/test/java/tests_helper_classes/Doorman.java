package tests_helper_classes;

import uberinjector.Inject;

public class Doorman implements Person {
    private WinterGreeter greeter;

    @Inject
    public Doorman(WinterGreeter greeter) {
        this.greeter = greeter;
    }

    public String Welcome() {
        return this.greeter.getWinterGreetings();
    }

    public String GetAction() {
        return "Opens the door";
    }
}