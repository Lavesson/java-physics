package Rendering;

import Input.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class LWJGL3Renderer {
    private final int width;
    private final int height;
    private final String title;
    private InputEventHandler input;
    private long window;

    public LWJGL3Renderer(int width, int height, String title, InputEventHandler input) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.input = input;
    }

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

    private void loop() {
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

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
            KeyEvent evt = new KeyEvent(LWJGL3Renderer.mapKeyInput(key));

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
                    LWJGL3Renderer.mapMouseInput(button), (float)bufferX.get(), (float)bufferY.get());

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

    public void shutdown() {
        glfwSetWindowShouldClose(window, true);
    }
}
