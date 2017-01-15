package named_test;

import uberinjector.Annotations.Named;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Named
@Retention(RetentionPolicy.RUNTIME)
public @interface ReceptionistAnnotation {
}
