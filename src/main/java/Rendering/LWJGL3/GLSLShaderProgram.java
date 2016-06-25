package Rendering.LWJGL3;

import Rendering.Common.ShaderException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

// Note that the LWJGL3 package is pretty much an aggregate. The only thing that isn't package-local is
// the renderer itself
class GLSLShaderProgram implements AutoCloseable {
    private final int program;

    public GLSLShaderProgram(String vertexSource, String fragmentSource) throws ShaderException {
        // Create a new shader program
        program = glCreateProgram();

        // Create and compile each shader and link them together into a program
        link(compile(glCreateShader(GL_VERTEX_SHADER), vertexSource),
             compile(glCreateShader(GL_FRAGMENT_SHADER), fragmentSource));
    }

    public void bind() {
        glUseProgram(program);
    }

    // Attaches the vertex and fragment shaders to the program and links it
    private void link(int vert, int frag) throws ShaderException {
        // Attach the shaders
        glAttachShader(program, vert);
        glAttachShader(program, frag);

        // Link the program
        glLinkProgram(program);

        // Detach and delete the shaders - we're done with them and can use the program from now on
        glDetachShader(program, vert);
        glDetachShader(program, frag);

        if (glGetShaderi(program, GL_LINK_STATUS) == GL_FALSE) {
            throw new ShaderException("Could not link shader program", glGetProgramInfoLog(program));
        }
    }

    // Compile a shader (used regardless of whether we're dealing with a fragment or a vertex shader)
    private int compile(int shader, String sourceCode) throws ShaderException {
        glShaderSource(shader, sourceCode);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new ShaderException("Could not compile shader", glGetShaderInfoLog(shader));
        }

        return shader;
    }

    @Override
    public void close() throws Exception {
        // Delete the program when closing
        glDeleteProgram(program);
    }
}
