package Input;

public interface InputEventHandler {
    void mousePressed(MouseInput input);
    void mouseReleased(MouseInput input);
    void keyPressed(KeyInput input);
    void keyReleased(KeyInput input);
}
