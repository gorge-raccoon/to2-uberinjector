package simple_injection_test;

public class GreeterUser {
    private WinterGreeter greeter;

    public GreeterUser(WinterGreeter greeter) {
        this.greeter = greeter;
    }

    public String welcome() {
        return this.greeter.getWinterGreetings();
    }
}
