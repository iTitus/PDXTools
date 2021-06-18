package io.github.ititus.stellaris.lwjgl.viewer.engine.buffer;

import static org.lwjgl.opengl.GL32C.GL_ELEMENT_ARRAY_BUFFER;

public class ElementArrayBuffer extends Buffer<ElementArrayBuffer> {

    public ElementArrayBuffer() {
        super(GL_ELEMENT_ARRAY_BUFFER);
    }

    @Override
    protected void init() {
    }
}
