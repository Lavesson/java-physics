package Physics;

public class Rectangle {
    public float x;
    public float y;
    public float width;
    public float height;

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

    public static Point getCenter(Rectangle r) {
        return new Point(
                r.x + r.width*0.5f,
                r.y + r.height*0.5f);
    }

    public static boolean hasOverlap(Rectangle b1Area, Rectangle b2Area) {
        return b1Area.x < b2Area.x + b2Area.width &&
               b1Area.x + b1Area.width > b2Area.x &&
               b1Area.y < b2Area.y + b2Area.height &&
               b1Area.y + b1Area.height > b2Area.y;
    }
}
