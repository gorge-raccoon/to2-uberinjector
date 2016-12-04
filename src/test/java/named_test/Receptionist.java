package named_test;

import tests_helper_classes.Person;
import tests_helper_classes.WinterGreeter;
import uberinjector.Inject;

public class Receptionist implements Person {
    private WinterGreeter greeter;

    @Inject
    public Receptionist(@Hanukkah WinterGreeter greeter) {
        this.greeter = greeter;
    }

    public String Welcome() {
        return this.greeter.getWinterGreetings();
    }

    public String GetAction() {
        return "Does receptionist stuff";
    }
}
