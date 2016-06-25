import Input.InputEventHandler;
import Input.KeyEvent;
import Input.KeyInput;
import Input.MouseEvent;
import Physics.Body;
import Physics.PhysicsWorld;
import Physics.Rectangle;
import Physics.Vector2D;
import Rendering.LWJGL3Renderer;

import java.util.Random;

public class Game implements InputEventHandler {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 960;
    private static final float SCALE = 100;
    private final LWJGL3Renderer renderer;
    private final PhysicsWorld world;

    public Game() {
        renderer = new LWJGL3Renderer(WIDTH, HEIGHT, "Physics Demo", this);
        world = new PhysicsWorld();
        renderer.onRender(this::mainLoop);
        renderer.start();

        // Add a ground box to the world
        Body ground = new Body(
                new Rectangle(0.0f / SCALE, 760.0f / SCALE, 1280.0f / SCALE, 200.0f / SCALE));

        ground.hasGravity = false;
        world.add(ground);
    }

    private void mainLoop(double dt) {
        world.step(dt);
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
        Body body = new Body(
                Rectangle.fromCenter(e.posX / SCALE, e.posY / SCALE, randomBodyLength(), randomBodyLength()));

        body.velocity = randomVelocityForBody();
        world.add(body);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.key == KeyInput.ESCAPE)
            renderer.shutdown();
    }

    @Override public void mouseReleased(MouseEvent e) { /* NOP */ }
    @Override public void keyPressed(KeyEvent e) { /* NOP */ }
}
