package Physics;

import Rendering.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Physics.PhysicsEngine engine = new Physics.PhysicsEngine();

        // Let's assume all units are in centimeters
        Body ground = new Body(new Rectangle(0, 400, 500, 100));
        ground.hasGravity = false;

        engine.add(ground);

        new MainWindow(engine);
    }
}