package singleton_test;

import uberinjector.Annotations.Inject;

public class SingletonUser {
    public SingletonClass singleton;

    @Inject
    public SingletonUser(SingletonClass singleton) {
        this.singleton = singleton;
    }
}
