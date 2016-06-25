import Input.InputEventHandler;
import Input.KeyEvent;
import Input.KeyInput;
import Input.MouseEvent;
import Physics.Body;
import Physics.PhysicsEngine;
import Physics.Rectangle;
import Physics.Vector2D;
import Rendering.LWJGL3Renderer;

import java.util.Random;

public class Game implements InputEventHandler {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 960;
    private static final float SCALE = 100;
    private final LWJGL3Renderer renderer;
    private final PhysicsEngine physics;

    public Game() {
        renderer = new LWJGL3Renderer(WIDTH, HEIGHT, "Physics Demo", this);
        physics = new PhysicsEngine();
        renderer.start();
    }

    // Helper method to generate a random velocity
    private Vector2D randomVelocityForBody() {
        Random r = new Random();
        return new Vector2D((r.nextFloat()-0.5f)*2.0f, -r.nextFloat()*5.0f);
    }

    // Generate a random width and height between 30 and 80
    private float randomBodyLength() {
        Random r = new Random();
        return (r.nextInt(51) + 30) / SCALE;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed: " + e);
        Body body = new Body(
                Rectangle.fromCenter(e.posX / SCALE, e.posY / SCALE, randomBodyLength(), randomBodyLength()));

        body.velocity = randomVelocityForBody();
        physics.add(body);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse released: " + e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // NOP
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.key == KeyInput.ESCAPE)
            renderer.shutdown();
    }
}
