package named_test;

import tests_helper_classes.Person;
import tests_helper_classes.WinterGreeter;
import uberinjector.Annotations.Inject;

public class Receptionist implements Person {
    private WinterGreeter greeter;

    @Inject
    public Receptionist(@Hanukkah WinterGreeter greeter) {
        this.greeter = greeter;
    }

    public String welcome() {
        return this.greeter.getWinterGreetings();
    }

    public String getAction() {
        return "Does receptionist stuff";
    }
}
