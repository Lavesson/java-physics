package Rendering.Common;

import Rendering.Surface.Box;

public interface Renderer {
    void onRender(UpdateLoop render);
    void start();
    void shutdown();
    void useShader(String name) throws ShaderException;
    void addToRenderList(Box box);
}
