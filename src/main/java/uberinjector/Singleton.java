package uberinjector;

public @interface Singleton {
    boolean eager() default false;
}
