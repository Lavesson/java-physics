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

public class RenderPanel extends JPanel implements MouseInputListener {
    private final PhysicsEngine engine;

    public RenderPanel(PhysicsEngine engine) {
        this.engine = engine;
        addMouseListener(this);
        setupEngine();
    }

    private void setupEngine() {
        // Let's assume all units are in centimeters
        Body ground = new Body(new Rectangle(0, 400, 500, 100));
        ground.hasGravity = false;
        engine.add(ground);
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

    @Override
    public void mouseClicked(MouseEvent e) {}

    // Unused crap that the interface bloats our class with:
    @Override public void mousePressed(MouseEvent e) {
        engine.add(new Body(Rectangle.fromCenter(e.getX(), e.getY(), 30, 30)));
        repaint();
    }
    @Override public void mouseReleased(MouseEvent e) {  /* Not used */  }
    @Override public void mouseEntered(MouseEvent e) {  /* Not used */  }
    @Override public void mouseExited(MouseEvent e) {  /* Not used */  }
    @Override public void mouseDragged(MouseEvent e) {  /* Not used */  }
    @Override public void mouseMoved(MouseEvent e) {  /* Not used */  }
}
