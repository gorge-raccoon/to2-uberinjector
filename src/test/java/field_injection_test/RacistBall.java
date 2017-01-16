package field_injection_test;

import method_injection_test.Color;
import method_injection_test.Racist;
import method_injection_test.Size;
import uberinjector.Annotations.Inject;

public class RacistBall {
    @Inject
    public Size size;
    @Inject
    @Racist
    public Color color;

    public String getDescription() {
        return String.format("The ball is %s and %s.", this.size.getName(), this.color.getName());
    }
}
