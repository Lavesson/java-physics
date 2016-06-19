package Rendering;

import Physics.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class MainWindow extends JFrame {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    private final PhysicsEngine engine;

    public MainWindow(PhysicsEngine engine) {
        super("Simple Physics Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setBounds(0, 0, 500, 500);
        setVisible(true);
        setLocationRelativeTo(null);

        this.engine = engine;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;

        for (Body b : engine.getAllBodies()) {
            Physics.Rectangle rect = b.occupiedArea;

            g2d.setColor(Color.blue);
            g2d.fill(new Rectangle2D.Float(rect.x, rect.y, rect.width, rect.height));
        }
    }
}
