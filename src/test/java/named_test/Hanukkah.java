package named_test;

import uberinjector.Annotations.Named;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Named
public @interface Hanukkah {
}
