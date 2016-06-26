package Input;

public class KeyEvent {
    public KeyInput key;

    public KeyEvent(KeyInput key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "KeyEvent{" +
                "key=" + key +
                '}';
    }
}
