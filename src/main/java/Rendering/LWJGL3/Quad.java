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

        float[] vertices = new float[]
        {
                +0.0f, +0.8f,    // Top coordinate
                -0.8f, -0.8f,    // Bottom-left coordinate
                +0.8f, -0.8f     // Bottom-right coordinate
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
        glDrawArrays(GL_TRIANGLES, 0, 3);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
}
