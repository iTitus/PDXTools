package io.github.ititus.stellaris.lwjgl.viewer.engine.shader;

import io.github.ititus.math.matrix.Mat4f;

public class DefaultShaderProgram extends ShaderProgram<DefaultShaderProgram> {

    private static final String PATH = "/shader/default";

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
        return 0;
    }

    public int getTexposLocation() {
        return 1;
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
