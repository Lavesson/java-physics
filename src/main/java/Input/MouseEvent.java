package Input;

public class MouseEvent {
    public final MouseInput button;
    public final float posX;
    public final float posY;

    public MouseEvent(MouseInput button, float posX, float posY) {
        this.button = button;
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public String toString() {
        return "MouseEvent{" +
                "button=" + button +
                ", posX=" + posX +
                ", posY=" + posY +
                '}';
    }
}
