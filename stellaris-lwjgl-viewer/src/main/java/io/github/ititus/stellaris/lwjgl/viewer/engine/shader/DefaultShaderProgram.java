package io.github.ititus.stellaris.lwjgl.viewer.engine.shader;

public class DefaultShaderProgram extends ShaderProgram {

    private static final String PATH = "/shader/default";

    private int modelLocation;
    private int viewLocation;
    private int projectionLocation;
    private int texLocation;
    private int posLocation;
    // private int colorLocation;
    private int texposLocation;

    public DefaultShaderProgram() {
        super(
                new VertexShader(new FileShaderSource(PATH + VertexShader.EXTENSION)),
                new FragmentShader(new FileShaderSource(PATH + FragmentShader.EXTENSION))
        );
    }

    @Override
    protected void findLocations() {
        modelLocation = getUniformLocation("model");
        viewLocation = getUniformLocation("view");
        projectionLocation = getUniformLocation("projection");
        texLocation = getUniformLocation("tex");
        posLocation = getAttributeLocation("pos");
        // colorLocation = getAttributeLocation("color");
        texposLocation = getAttributeLocation("texpos");
    }
}
