package singleton_test;

import uberinjector.Singleton;

@Singleton(eager=true)
public class EagerSingletonClass implements SomeInterface {
}
