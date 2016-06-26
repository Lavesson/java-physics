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

        /* Note: We're currently drawing by keeping duplicate vertices in memory. In most situations, you'd probably
         * want to use an index buffer. The common thing is to build a vertex buffer with the 4 actual vertices of the
         * quad, like this:
         * [ 0: top left, 1: bottom left, 2: bottom right, 3: top right ]
         *
         * The next thing you would do is to create an array of ints to hold the indices, like this:
         * [ 0, 1, 2, 0, 2, 3 ]
         *
         * ... And bind that one and instead reuse the indices when drawing
         * In this case though, we're just using an array with duplicates instead (for now), because I'm lazy
         * and it doesn't really matter in this simple example */
        float[] vertices = new float[] {
                // Triangle #1
                // Vertices:                                    // Colors (r,g,b,a)
                initialX, initialY,                             1, 0, 0, 1,                 // Top left
                box.x(), box.y() + box.height(),                0, 1, 0, 1,                 // Bottom left
                box.x() + box.width(), box.y() + box.height(),  0, 0, 1, 1f,                // Bottom right

                // Triangle #2
                initialX, initialY,                             1, 0, 0, 1,                 // Top left
                box.x() + box.width(), box.y() + box.height(),  0, 0, 1, 1,                 // Bottom right
                box.x() + box.width(), box.y(),                 1, 0, 1, 1                  // Top right
        };

        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices).flip();

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        // Buffer stride - We calculate the stride by finding the total size in bytes of each vertex in the buffer
        // We're interleaving position and color data for each vertex. Basically, we just need to take the size
        // of a float in bytes and multiply by the number of components per "row" in our buffer:
        int stride = ( Float.SIZE / 8 ) * 6;

        // Position - Has 2 floating point components and starts 0 bytes into each row
        glVertexAttribPointer(0, 2, GL_FLOAT, false, stride, 0);

        // Color - Has 4 floating point components and starts 2x32 bytes into each row (after the position)
        glVertexAttribPointer(1, 4, GL_FLOAT, false, stride, 2* (Float.SIZE / 8));

        // We're done setting the array up - release it
        glBindVertexArray(0);

        // Set an initial identity matrix
        translation = glGetUniformLocation(shaderProgram, "translation");
        glUniformMatrix4fv(translation, false, new Matrix4f().getBuffer());
    }

    public void render() {
        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

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
