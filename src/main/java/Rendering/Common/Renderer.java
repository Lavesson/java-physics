package Rendering.Common;

public interface Renderer {
    void onRender(UpdateLoop render);
    void start();
    void shutdown();
    void useShader(String name) throws ShaderException;
}
