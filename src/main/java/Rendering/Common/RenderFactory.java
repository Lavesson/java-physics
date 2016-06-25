package Rendering.Common;

import Rendering.LWJGL3.GLSLRenderer;
import Rendering.LWJGL3.RenderConfiguration;

public class RenderFactory {
    public static Renderer createSubSystem(RenderConfiguration config, String systemName) {
        switch (systemName) {
            case "LWJGL3": return new GLSLRenderer(config);
            default: return null;
        }
    }
}
