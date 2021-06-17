package io.github.ititus.stellaris.lwjgl.viewer.engine.shader;

import io.github.ititus.math.matrix.Mat4f;

public class DefaultShaderProgram extends ShaderProgram {

    private static final String PATH = "/shader/default";

    private final int posLocation = 0;
    private final int texposLocation = 1;
    private int modelLocation;
    private int viewLocation;
    private int projectionLocation;
    private int texLocation;

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
    }

    public void setModel(Mat4f model) {
        setUniform(modelLocation, model);
    }

    public void setView(Mat4f view) {
        setUniform(viewLocation, view);
    }

    public void setProjection(Mat4f projection) {
        setUniform(projectionLocation, projection);
    }

    public void setTex(int tex) {
        setUniform(texLocation, tex);
    }

    public int getPosLocation() {
        return posLocation;
    }

    public int getTexposLocation() {
        return texposLocation;
    }

    public int getModelLocation() {
        return modelLocation;
    }

    public int getViewLocation() {
        return viewLocation;
    }

    public int getProjectionLocation() {
        return projectionLocation;
    }
}
