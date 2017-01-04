package method_injection_test;

import uberinjector.Inject;

public class Ball {
    private Size size;
    private Color color;

    @Inject
    void setSize(Size size) {
        this.size = size;
    }

    @Inject
    void setColor(Color color) {
        this.color = color;
    }

    public String getDescription() {
        return String.format("The ball is {} and {}.", this.size.getName(), this.color.getName());
    }
}
