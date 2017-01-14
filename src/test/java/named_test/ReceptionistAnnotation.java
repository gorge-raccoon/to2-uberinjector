package named_test;

import uberinjector.Named;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Named
@Retention(RetentionPolicy.RUNTIME)
public @interface ReceptionistAnnotation {
}
