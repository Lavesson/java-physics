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
    private final float initialX;   // Initial placement in X
    private final float initialY;   // Initial placement in Y
    private Box box;                // High level representation of this quad
    private float scale;

    public Quad(Box box, int shaderProgram, float scale) {
        this.box = box;
        this.scale = scale;
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        initialX = box.x();
        initialY = box.y();
        float[] vertices = new float[] {
                // Triangle #1
                initialX, initialY,                               // Top left
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

        // Set an initial identity matrix
        translation = glGetUniformLocation(shaderProgram, "translation");
        glUniformMatrix4fv(translation, false, new Matrix4f().getBuffer());
    }

    public void render() {
        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);

        // Update the translation according to the attached entity
        Matrix4f t = Matrix4f.translate(box.x() - initialX, box.y() - initialY, 0);
        glUniformMatrix4fv(this.translation, false, t.getBuffer());

        glDrawArrays(GL_TRIANGLES, 0, 6);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    public void destroy() {
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
    }
}
