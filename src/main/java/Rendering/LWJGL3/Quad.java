package Rendering.LWJGL3;
import Rendering.Surface.Box;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

class Quad {
    private final int vao;      // Vertex array object
    private final int vbo;      // Vertex buffer object

    public Quad(Box box) {
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        float[] vertices = new float[] {
                // Triangle #1
                -0.5f,  0.5f,    // Top left
                -0.5f, -0.5f,    // Bottom left
                 0.5f, -0.5f,    // Bottom right

                // Triangle #2
                -0.5f,  0.5f,    // Top left
                 0.5f, -0.5f,    // Bottom right
                 0.5f,  0.5f     // Top right
        };

        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        glBindVertexArray(0);
    }

    public void render() {
        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
}
