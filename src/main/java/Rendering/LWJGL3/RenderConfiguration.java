package Rendering.LWJGL3;

import Input.InputEventHandler;

public class RenderConfiguration {
    public final int width;
    public final int height;
    public final String title;
    public final float scale;
    public final InputEventHandler input;

    public RenderConfiguration(int width, int height, String title, float scale, InputEventHandler input) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.scale = scale;
        this.input = input;
    }
}
