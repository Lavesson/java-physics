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
    private Box box;                // High level representation of this quad

    public Quad(Box box, int shaderProgram) {
        this.box = box;
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        float width = 50.0f;
        float[] vertices = new float[] {
                // Triangle #1
                box.x(), box.y(),                               // Top left
                box.x(), box.y() + box.height(),                // Bottom left
                box.x() + box.width(), box.y() + box.height(),  // Bottom right

                // Triangle #2
                box.x(), box.y(),                               // Top left
                box.x() + box.width(), box.y() + box.height(),  // Bottom right
                box.x() + box.width(), box.y()                  // Top right
        };

        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        glBindVertexArray(0);

        // Uniform translation location in shader
        int translation = glGetUniformLocation(shaderProgram, "translation");

        // Set an initial identity matrix
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
