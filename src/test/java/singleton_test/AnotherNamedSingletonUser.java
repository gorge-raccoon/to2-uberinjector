package singleton_test;

import uberinjector.Annotations.Inject;

public class AnotherNamedSingletonUser {
    public SomeInterface singleton;

    @Inject
    public AnotherNamedSingletonUser(@AnotherNamedTest SomeInterface singleton) {
        this.singleton = singleton;
    }
}
