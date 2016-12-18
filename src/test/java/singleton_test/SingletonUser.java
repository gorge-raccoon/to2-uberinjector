package singleton_test;

import uberinjector.Inject;
import uberinjector.Singleton;

public class SingletonUser {
    public SingletonClass singleton;

    @Inject
    public SingletonUser(SingletonClass singleton) {
        this.singleton = singleton;
    }
}
