package Rendering.Common;

import Rendering.Scene.Node;

public interface Renderer {
    void onRender(UpdateLoop render);
    void start();
    void shutdown();
    void useShader(String name) throws ShaderException;
    void addToRenderList(Node node);
}
