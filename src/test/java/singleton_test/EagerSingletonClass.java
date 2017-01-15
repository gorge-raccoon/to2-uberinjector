package singleton_test;

import uberinjector.Annotations.Singleton;

@Singleton(eager=true)
public class EagerSingletonClass implements SomeInterface {
}
