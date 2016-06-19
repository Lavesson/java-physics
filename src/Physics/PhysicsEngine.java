package Physics;

import java.util.ArrayList;
import java.util.List;

// This is the main physics engine. Notice that NOTHING in here knows anything about how
// to actually draw stuff. It just calculates coordinates in the "world" based on all
// parameters

public class PhysicsEngine {
    private List<Body> bodies = new ArrayList<Body>();
    private float gravity = -9.82f;

    public void add(Body body) {
        bodies.add(body);
    }

    public List<Body> getAllBodies() {
        return new ArrayList<Body>(bodies);
    }

    // dt: The time delta to step the simulation forward with
    public void step(double dt) {
        for (Body b : bodies) {
            if (!b.hasGravity) continue;

            //
        }
    }
}
