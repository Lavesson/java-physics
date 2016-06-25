package Rendering.Common;

/**
 * Created by Eric on 2016-06-25.
 */
public interface Renderer {
    void onRender(UpdateLoop render);

    void start();

    void shutdown();
}
