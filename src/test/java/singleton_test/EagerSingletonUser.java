package singleton_test;

import uberinjector.Annotations.Inject;

public class EagerSingletonUser {
    public SomeInterface singleton;

    @Inject
    public EagerSingletonUser(SomeInterface singleton) {
        this.singleton = singleton;
    }
}
