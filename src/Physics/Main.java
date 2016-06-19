package Physics;

import Rendering.RenderPanel;
import javax.swing.*;
import java.awt.image.BufferStrategy;

public class Main {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 960;

    public static void main(String[] args) {
        JFrame mainWindow = new JFrame("Simple Physics Demo");

        // Java Swing initialization crap:
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(WIDTH, HEIGHT);
        mainWindow.setVisible(true);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setIgnoreRepaint(true);
        mainWindow.createBufferStrategy(2);

        RenderPanel renderPanel = new RenderPanel(new PhysicsEngine(), mainWindow.getBufferStrategy());
        mainWindow.add(renderPanel);
    }
}