package method_injection_test;

import uberinjector.Inject;

public class RacistBall extends Ball {
    @Inject
    public void setColor(@Racist Color color) {
        this.color = color;
    }
}
