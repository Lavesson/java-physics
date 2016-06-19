package Rendering;

import Physics.*;
import Physics.Rectangle;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class RenderPanel extends JPanel implements MouseInputListener {
    private final PhysicsEngine engine;

    public RenderPanel(PhysicsEngine engine) {
        this.engine = engine;
        addMouseListener(this);

        // Add a ground "box" to the engine
        Body ground = new Body(new Rectangle(0, 760, 1280, 200));
        ground.hasGravity = false;
        engine.add(ground);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;

        // Render all bodies in the physics engine. Notice that NOTHING in this class
        // actually knows anything about physics. It just draws whatever the engine
        // feeds it.
        for (Body b : engine.getAllBodies()) {
            Physics.Rectangle rect = b.occupiedArea;

            g2d.setColor(Color.blue);
            g2d.fill(new Rectangle2D.Float(rect.x, rect.y, rect.width, rect.height));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    // Unused crap that the interface bloats our class with:
    @Override public void mousePressed(MouseEvent e) {
        Random r = new Random();
        // Generate a random width and height between 30 and 80
        int w = r.nextInt(51) + 30, h = r.nextInt(51) + 30;
        engine.add(new Body(Rectangle.fromCenter(e.getX(), e.getY(), w, h)));
        repaint();
    }
    @Override public void mouseReleased(MouseEvent e) {  /* Not used */  }
    @Override public void mouseEntered(MouseEvent e) {  /* Not used */  }
    @Override public void mouseExited(MouseEvent e) {  /* Not used */  }
    @Override public void mouseDragged(MouseEvent e) {  /* Not used */  }
    @Override public void mouseMoved(MouseEvent e) {  /* Not used */  }
}
