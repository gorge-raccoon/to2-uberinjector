package singleton_test;

import uberinjector.Annotations.Inject;

public class NamedSingletonUser {
    public SomeInterface singleton;

    @Inject
    public NamedSingletonUser(@NamedTest SomeInterface singleton) {
        this.singleton = singleton;
    }
}
