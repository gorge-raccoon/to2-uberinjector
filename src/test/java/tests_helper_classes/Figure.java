package tests_helper_classes;

import uberinjector.Annotations.Inject;

public class Figure {
    private Color color;
    private Shape shape;

    @Inject
    public Figure(Color color, Shape shape) {
        this.color = color;
        this.shape = shape;
    }

    public String getDescription() {
        return String.format("I'm a %s %s.", color.getName(), shape.getName());
    }
}
