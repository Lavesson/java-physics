package Rendering;

import Input.InputEventHandler;
import Input.KeyInput;
import Input.MouseInput;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

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

    public LWJGL3Renderer(int width, int height, String title) {
        this(width, height, title, null);
    }

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

    private void setupInput() {
        if (input == null) return;

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            KeyInput mappedKey = LWJGL3Renderer.mapKeyInput(key);
            switch (action) {
                case GLFW_PRESS: input.keyPressed(mappedKey); break;
                default: input.keyReleased(mappedKey); break;
            }
        });

        glfwSetMouseButtonCallback(window, GLFWMouseButtonCallback.create((window, button, action, mods) -> {
            MouseInput mappedBtn = LWJGL3Renderer.mapMouseInput(button);
            switch (action) {
                case GLFW_PRESS: input.mousePressed(mappedBtn); break;
                default: input.mouseReleased(mappedBtn); break;
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
