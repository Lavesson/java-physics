package Physics;

import java.util.ArrayList;
import java.util.List;

public class PhysicsEngine {
    private List<Body> bodies = new ArrayList<Body>();

    public void add(Body body) {
        bodies.add(body);
    }

    public List<Body> getAllBodies() {
        return new ArrayList<Body>(bodies);
    }
}
