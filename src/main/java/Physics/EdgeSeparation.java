package Physics;

public class EdgeSeparation {
    public Vector2D findSmallestSeparations(Rectangle r1, Rectangle r2) {
        // We need to determine the axis to do the separation on.
        // We'll check four cases and pick the one with the LEAST overlap:
        // 1. left edge (r1) to right edge (r2) -> separate in x by moving r1 to the right
        // 2. right edge (r1) to left edge (r2) -> separate in x by moving r1 to the left
        // 3. top edge (r1) to bottom edge (r2) -> separate in y by moving r1 down
        // 4. bottom edge (r1) to top edge (r2) -> separate in y by moving r1 up
        // There's definitely more robust ways to do this (google for "separating axis theorem" for better implementations)

        Vector2D results = new Vector2D(-1, -1);

        // Left edge (r1) to right edge (r2)
        results.x = Math.abs(r1.x - (r2.x + r2.width));

        // Right edge (r1) to left edge (r2). Pick the smallest between the previous calculation and our new one
        // The minus sign in the second part is to make sure we translate in the correct direction (left)
        results.x = Math.min(results.x, -Math.abs(r1.x + r1.width - r2.x));

        // Top edge (r1) to bottom edge (r2)
        results.y = Math.abs(r1.y - (r2.y + r2.height));

        // Bottom edge (r1) to top edge (r2) - same deal as for the second case in x
        results.y = Math.min(results.y, -Math.abs(r1.y+r1.height-r2.y));

        return results;
    }

}
