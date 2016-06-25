package Rendering.LWJGL3;

import Input.InputEventHandler;

public class RenderConfiguration {
    private final int width;
    private final int height;
    private final String title;
    private final InputEventHandler input;

    public RenderConfiguration(int width, int height, String title, InputEventHandler input) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.input = input;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public InputEventHandler getInput() {
        return input;
    }
}
