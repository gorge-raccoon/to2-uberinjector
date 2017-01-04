package field_injection_test;

import method_injection_test.Color;
import method_injection_test.Size;
import uberinjector.Inject;

public class Ball {
    @Inject public Size size;
    @Inject public Color color;

    public String getDescription() {
        return String.format("The ball is %s and %s.", this.size.getName(), this.color.getName());
    }
}
