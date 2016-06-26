package Physics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// This is the main physics engine. Notice that NOTHING in here knows anything about how
// to actually draw stuff. It just calculates coordinates in the "world" based on all
// parameters

public class PhysicsWorld {
    private List<Body> bodies = new ArrayList<Body>();
    private float gravity = 9.82f;
    private EdgeSeparation edgeSeparation = new EdgeSeparation();

    public void add(Body body) {
        bodies.add(body);
    }

    public List<Body> getAllBodies() {
        return new ArrayList<Body>(bodies);
    }

    // dt: The time delta to step the simulation forward with. Expressed in seconds
    public void step(double dt) {
        // Keep a hash set to avoid resolving the same pair twice (but reversed)
        HashSet<Body> resolved = new HashSet<>();

        for (Body b : bodies) {
            if (resolved.contains(b)) continue;
            resolved.add(b);
            if (!b.hasGravity) continue;

            // We'll start by just applying some simple gravity in y and update both x and y based on velocity
            updateInstantState(dt, b);

            // Next, we'll resolve some collisions between items
            resolveCollisionsForAllPairs(b);
        }
    }

    private void resolveCollisionsForAllPairs(Body body1) {
        // NOTE: There's a couple of limitations here
        // 1. To avoid tunneling, we should probably construct something a bit more robust, such as constructing a new
        //    shape which is the union of all rectangles between our original point and the one we're moving to.
        // 2. We're checking EVERYTHING against EVERYTHING ELSE. Every single frame. Instead of a simple list, something
        //    like a quad tree would be a much better choice.
        //
        // All in all, this is a vast simplification, but it'll do for our simple engine :)
        for (Body body2 : bodies) {
            if (body1 == body2) return;

            Rectangle b1Area = body1.occupiedArea;
            Rectangle b2Area = body2.occupiedArea;

            if (Rectangle.hasOverlap(b1Area, b2Area)) {
                resolveCollisionBetween(body1, body2);
            }
        }
    }

    private void resolveCollisionBetween(Body b1, Body b2) {
        // This can be a bit tricky, and I'm really doing the SIMPLEST thing possible here. Really, it's ugly.
        // I just move the rectangles out of each other. You'd probably want to use something like an R-tree
        // structure to do this properly

        // Helper class that finds the smallest separation in x and y between two areas
        Vector2D separation = edgeSeparation.findSmallestSeparations(b1.occupiedArea, b2.occupiedArea);

        if (Math.abs(separation.x) < Math.abs(separation.y)) {
            // Resolve by moving apart in x
            b1.occupiedArea.x += separation.x;
            b1.velocity.x = 0;
        } else {
            // Resolve by moving apart in y
            b1.occupiedArea.y += separation.y;
            b1.velocity.y = 0;
        }
    }

    private void updateInstantState(double dt, Body b) {
        // Current velocity:
        float v0 = b.velocity.y;

        // Calculate the new velocity for this frame:
        b.velocity.y = (float) (v0 + gravity * dt);

        // Calculate the distance that we've moved in this frame:
        float s = (float) (v0*dt + 0.5f * gravity * dt * dt);
        b.occupiedArea.y += s;
        b.occupiedArea.x += b.velocity.x*dt;
    }
}
