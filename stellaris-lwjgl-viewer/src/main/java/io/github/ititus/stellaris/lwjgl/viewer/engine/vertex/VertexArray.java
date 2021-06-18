package io.github.ititus.stellaris.lwjgl.viewer.engine.vertex;

import io.github.ititus.stellaris.lwjgl.viewer.engine.GlObject;

import static org.lwjgl.opengl.GL32C.*;

public class VertexArray extends GlObject<VertexArray> {

    public void bind() {
        glBindVertexArray(id);
    }

    public void enableVertexAttribArray(int index) {
        glEnableVertexAttribArray(index);
    }

    public void vertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long pointer) {
        glVertexAttribPointer(index, size, type, normalized, stride, pointer);
    }

    public void draw(int mode, int first, int count) {
        glDrawArrays(mode, first, count);
    }

    @Override
    protected int create() {
        return glGenVertexArrays();
    }

    @Override
    protected void init() {
    }

    @Override
    protected void delete() {
        glDeleteVertexArrays(id);
    }
}
