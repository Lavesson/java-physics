package Physics;

public class Rectangle {
    public final float x;
    public final float y;
    public final float width;
    public final float height;

    // x: upper left corner
    // y: upper right corner
    // width: width of rectangle from upper left corner
    // height: height of rectangle from upper left corner
    public Rectangle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // A helper to create rectangles from a center point
    // cx: center x coordinate
    // cy: center y coordinate
    public static Rectangle fromCenter(float cx, float cy, float width, float height) {
        return new Rectangle(
                cx - width*0.5f, cy - height*0.5f,
                width, height);
    }
}
