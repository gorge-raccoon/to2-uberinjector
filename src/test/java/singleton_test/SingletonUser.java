package singleton_test;

import uberinjector.Annotations.Inject;

public class SingletonUser {
    public SomeInterface singleton;

    @Inject
    public SingletonUser(SomeInterface singleton) {
        this.singleton = singleton;
    }
}
