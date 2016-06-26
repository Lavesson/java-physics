package Rendering.Surface;

import Physics.Body;

public class Box {
    private Body body;

    public Box(Body body) {
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
