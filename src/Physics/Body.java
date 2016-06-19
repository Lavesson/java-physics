package Physics;

// Just a simple data structure
public class Body {
    public final Rectangle occupiedArea;
    public boolean hasGravity = true;
    public final Vector2D velocity = new Vector2D(0,0);

    // We're restricting ourselves to rectangles, but another option would be to
    // pass an interface of a generic shape to the constructor
    public Body(Rectangle rectangle) {
        this.occupiedArea = rectangle;
    }
}
