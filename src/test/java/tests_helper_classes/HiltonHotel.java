package tests_helper_classes;

import uberinjector.Annotations.Inject;

public class HiltonHotel implements Hotel {

    private Person worker;

    @Inject
    public HiltonHotel(Person worker)
    {
        this.worker=worker;
    }

    @Override
    public String doHotelStuff() {
        return "welcome to Hilton Hotel. " + worker.welcome() + worker.getAction();
    }

}
