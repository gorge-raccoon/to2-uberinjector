package object_injection_test;

import uberinjector.Annotations.Inject;

public class RussellFieldGreeter implements IWinterGreeter {

    @Inject
    @Russell
    public String greeting;

    @Override
    public String getGreeting() {
        return greeting;
    }
}
