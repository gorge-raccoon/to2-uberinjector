package method_injection_test;

public class RacistBall extends Ball {
    public void setColor(@Racist Color color) {
        this.setColor(color);
    }
}
