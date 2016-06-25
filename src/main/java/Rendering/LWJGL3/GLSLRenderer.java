package Rendering.LWJGL3;

import Input.*;

import Rendering.Common.Renderer;
import Rendering.Common.ShaderException;
import Rendering.Common.UpdateLoop;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.io.IOException;
import java.nio.DoubleBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GLSLRenderer implements Renderer {
    private final int width;
    private final int height;
    private final String title;
    private InputEventHandler input;
    private long window;
    private UpdateLoop onRenderInstance = null;
    private double lastTime = 0.0;
    private GLSLShaderProgram shader;

    public GLSLRenderer(RenderConfiguration renderSetup) {
        this.width = renderSetup.getWidth();
        this.height = renderSetup.getHeight();
        this.title = renderSetup.getTitle();
        this.input = renderSetup.getInput();
    }

    @Override
    public void onRender(UpdateLoop render) {
        onRenderInstance = render;
    }

    @Override
    public void start() {
        try {
            init();
            loop();

            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
        } finally {
            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    }

    // Calculate a time delta (in seconds) between this and the previous frame
    private double calculateTimeDelta() {
        double time = glfwGetTime();
        double delta = time - lastTime;
        lastTime = time;
        return delta;
    }

    // Main render loop, dispatches to an optional update function
    private void loop() {
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        while ( !glfwWindowShouldClose(window) ) {
            if (onRenderInstance != null)
                onRenderInstance.update(calculateTimeDelta());

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            if (shader != null) {
                // TODO: Render crap here
            }

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    // Initialize the renderer
    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Window configuration
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);

        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        setupInput();

        GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                window,
                (mode.width() - width) / 2,
                (mode.height() - height) / 2
        );

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    // We're dispatching input to our own set of event classes. Not strictly necessary, but I like the abstraction
    // since it allows us to switch input systems further down the road if necessary
    private void setupInput() {
        if (input == null) return;

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            // ... Create a key event
            KeyEvent evt = new KeyEvent(GLSLRenderer.mapKeyInput(key));

            // ... and pass it on to the correct callback
            switch (action) {
                case GLFW_PRESS: input.keyPressed(evt); break;
                default: input.keyReleased(evt); break;
            }
        });

        glfwSetMouseButtonCallback(window, GLFWMouseButtonCallback.create((window, button, action, mods) -> {
            // ... Create a mouse event
            DoubleBuffer bufferX = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer bufferY = BufferUtils.createDoubleBuffer(1);
            glfwGetCursorPos(window, bufferX, bufferY);
            MouseEvent evt = new MouseEvent(
                    GLSLRenderer.mapMouseInput(button), (float)bufferX.get(), (float)bufferY.get());

            // ... and pass it on to the correct callback
            switch (action) {
                case GLFW_PRESS: this.input.mousePressed(evt); break;
                default: this.input.mouseReleased(evt); break;
            }
        }));
    }

    private static MouseInput mapMouseInput(int button) {
        switch (button) {
            case GLFW_MOUSE_BUTTON_LEFT: return MouseInput.LEFT;
            case GLFW_MOUSE_BUTTON_RIGHT: return MouseInput.RIGHT;
            case GLFW_MOUSE_BUTTON_MIDDLE: return MouseInput.MIDDLE;
            default: return MouseInput.UNKNOWN;
        }
    }

    private static KeyInput mapKeyInput(int key) {
        switch (key) {
            case GLFW_KEY_ESCAPE: return KeyInput.ESCAPE;
            default: return KeyInput.UNKNOWN;
        }
    }

    @Override
    public void shutdown() {
        glfwSetWindowShouldClose(window, true);
    }

    @Override
    public void useShader(String name) throws ShaderException {
        try {
            String vertexSource = new String(Files.readAllBytes(Paths.get(String.format("%s.%s", name, ".glsl"))));
            String fragmentSource = new String(Files.readAllBytes(Paths.get(String.format("%s.%s", name, ".glsl"))));
            shader = new GLSLShaderProgram(vertexSource, fragmentSource);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ShaderException("Could not read shader file", e.toString());
        }
    }
}
