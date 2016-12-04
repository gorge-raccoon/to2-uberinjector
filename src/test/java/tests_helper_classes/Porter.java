package tests_helper_classes;

import uberinjector.Inject;

public class Porter implements Person {
    private WinterGreeter greeter;

    @Inject
    public Porter(WinterGreeter greeter) {
        this.greeter = greeter;
    }

    public String Welcome() {
        return this.greeter.getWinterGreetings();
    }

    public String GetAction() {
        return "Takes your luggage";
    }
}