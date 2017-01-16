package named_test;

import tests_helper_classes.Hotel;
import tests_helper_classes.Person;
import uberinjector.Annotations.Inject;

public class AnotherHotel implements Hotel {

    private Person worker;

    @Inject
    public AnotherHotel(@ReceptionistAnnotation Person worker) {
        this.worker = worker;
    }

    @Override
    public String doHotelStuff() {
        return "welcome to Another Hotel. " + worker.welcome() + worker.getAction();
    }

}