import Input.InputEventHandler;
import Input.KeyInput;
import Input.MouseInput;
import Rendering.LWJGL3Renderer;

public class Game implements InputEventHandler {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 960;
    private final LWJGL3Renderer renderer;

    public Game() {
        renderer = new LWJGL3Renderer(WIDTH, HEIGHT, "Physics Demo", this);
        renderer.start();
    }

    @Override
    public void mousePressed(MouseInput input) {
        System.out.println("Mouse pressed: " + input);
    }

    @Override
    public void mouseReleased(MouseInput input) {
        System.out.println("Mouse released: " + input);
    }

    @Override
    public void keyPressed(KeyInput input) {
        System.out.println("Key pressed: " + input);
    }

    @Override
    public void keyReleased(KeyInput input) {
        if (input == KeyInput.ESCAPE)
            renderer.shutdown();
    }
}
