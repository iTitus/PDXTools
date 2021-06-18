package io.github.ititus.stellaris.lwjgl.viewer.engine.buffer;

import static org.lwjgl.opengl.GL32C.GL_ARRAY_BUFFER;

public class ArrayBuffer extends Buffer<ArrayBuffer> {

    public ArrayBuffer() {
        super(GL_ARRAY_BUFFER);
    }

    @Override
    protected void init() {
    }
}
