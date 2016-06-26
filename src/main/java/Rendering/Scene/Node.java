package Rendering.Scene;

import Physics.Body;

public class Node {
    private Body body;

    public Node(Body body) {
        this.body = body;
    }

    public float x() {
        return body.occupiedArea.x;
    }

    public float y() {
        return body.occupiedArea.y;
    }

    public float width() {
        return body.occupiedArea.width;
    }

    public float height() {
        return body.occupiedArea.height;
    }
}
