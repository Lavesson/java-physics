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
import java.awt.image.BufferStrategy;
import java.util.Random;

public class RenderPanel extends JPanel implements MouseInputListener {
    private static final float SCALE = 100;
    private final PhysicsEngine engine;
    private final BufferStrategy buffer;

    public void mainLoop()
    {
        long lastLoopTime = System.nanoTime();

        while (true)
        {
            long now = System.nanoTime();
            long delta = now - lastLoopTime;
            lastLoopTime = now;

            // Convert the time delta from nano seconds to seconds
            engine.step(delta * 1.0E-09);

            // draw everyting
            render();
        }
    }

    private void render() {
        Graphics2D g2d = (Graphics2D)buffer.getDrawGraphics();
        g2d.clearRect(0, 0, getWidth(), getHeight());

        // Render all bodies in the physics engine. Notice that NOTHING in this class
        // actually knows anything about physics. It just draws whatever the engine
        // feeds it.
        for (Body b : engine.getAllBodies()) {
            Physics.Rectangle rect = b.occupiedArea;

            g2d.setColor(Color.blue);
            g2d.fill(new Rectangle2D.Float(rect.x*SCALE, rect.y*SCALE, rect.width*SCALE, rect.height*SCALE));
        }

        g2d.dispose();
        buffer.show();
    }

    public RenderPanel(PhysicsEngine engine, BufferStrategy bufferStrategy) {
        this.buffer = bufferStrategy;
        this.engine = engine;
        addMouseListener(this);

        // Add a ground "box" to the engine
        Body ground = new Body(
                new Rectangle(0.0f / SCALE, 760.0f / SCALE, 1280.0f / SCALE, 200.0f / SCALE));

        ground.hasGravity = false;
        engine.add(ground);

        Thread main = new Thread(() -> mainLoop());
        main.start();
    }

    @Override public void mousePressed(MouseEvent e) {
        Body body = new Body(
                Rectangle.fromCenter(e.getX() / SCALE, e.getY() / SCALE, randomBodyLength(), randomBodyLength()));

        body.velocity = randomVelocityForBody();
        engine.add(body);

        repaint();
    }

    private Vector2D randomVelocityForBody() {
        Random r = new Random();
        return new Vector2D((r.nextFloat()-0.5f)*2.0f, -r.nextFloat()*5.0f);
    }

    // Generate a random width and height between 30 and 80
    private float randomBodyLength() {
        Random r = new Random();
        return (r.nextInt(51) + 30) / SCALE;
    }

    // Unused crap that the interface bloats our class with:
    @Override public void mouseClicked(MouseEvent e) { /* Not used */ }
    @Override public void mouseReleased(MouseEvent e) {  /* Not used */  }
    @Override public void mouseEntered(MouseEvent e) {  /* Not used */  }
    @Override public void mouseExited(MouseEvent e) {  /* Not used */  }
    @Override public void mouseDragged(MouseEvent e) {  /* Not used */  }
    @Override public void mouseMoved(MouseEvent e) {  /* Not used */  }
}
