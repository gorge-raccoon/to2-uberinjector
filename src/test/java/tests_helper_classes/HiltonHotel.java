package tests_helper_classes;

import uberinjector.Inject;

public class HiltonHotel implements Hotel {

    private Person worker;

    @Inject
    public HiltonHotel(Person worker)
    {
        this.worker=worker;
    }

    @Override
    public String doHotelStuff() {
        return "Welcome to Hilton Hotel. " + worker.Welcome() + worker.GetAction();
    }

}
