package Rendering.Common;

public class ShaderException extends Throwable {
    public final String message;
    public final String compilerError;

    public ShaderException(String message, String compilerError) {
        this.message = message;
        this.compilerError = compilerError;
    }
}
