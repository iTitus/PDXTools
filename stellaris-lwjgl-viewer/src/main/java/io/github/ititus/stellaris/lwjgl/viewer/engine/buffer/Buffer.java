package io.github.ititus.stellaris.lwjgl.viewer.engine.buffer;

import io.github.ititus.stellaris.lwjgl.viewer.engine.GlObject;

import static org.lwjgl.opengl.GL32C.*;

public abstract class Buffer<T extends Buffer<T>> extends GlObject<T> {

    private final int target;

    protected Buffer(int target) {
        this.target = target;
    }

    public void bind() {
        glBindBuffer(target, id);
    }

    public void bufferData(float[] data, int usage) {
        glBufferData(target, data, usage);
    }

    @Override
    protected int create() {
        return glGenBuffers();
    }

    @Override
    protected void delete() {
        glDeleteBuffers(id);
    }
}
