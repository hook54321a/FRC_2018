package real_time_model;

public class Locator {
    public MovingObject moving_object;
    int x;
    int y;
    float direction;    // angle in radians

    public Locator(MovingObject moving_object, int x, int y, float direction) {
        this.moving_object = moving_object;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
}
