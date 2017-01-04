package field_injection_test;

import method_injection_test.Color;
import method_injection_test.Racist;
import uberinjector.Inject;

public class RacistBall extends Ball {

    @Inject @Racist public Color color;
}
