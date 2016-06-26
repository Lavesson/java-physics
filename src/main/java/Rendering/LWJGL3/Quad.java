package Rendering.LWJGL3;
import Math.*;
import Rendering.Surface.Box;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

class Quad {
    private final int vao;          // Vertex array object
    private final int vbo;          // Vertex buffer object
    private final int translation;  // Uniform translation location in shader
    private Box box;                // High level representation of this quad

    public Quad(Box box, int shaderProgram) {
        this.box = box;
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

        translation = glGetUniformLocation(shaderProgram, "translate");
        glUniformMatrix4fv(translation, false, new Matrix4f().getBuffer());
    }

    public void render() {
        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);

        glDrawArrays(GL_TRIANGLES, 0, 6);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    public void destroy() {
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
    }
}
