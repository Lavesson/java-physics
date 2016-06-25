import Rendering.LWJGL3Renderer;

public class Main {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 960;

    public static void main(String[] args) {
        new LWJGL3Renderer(WIDTH, HEIGHT).run();
    }
}