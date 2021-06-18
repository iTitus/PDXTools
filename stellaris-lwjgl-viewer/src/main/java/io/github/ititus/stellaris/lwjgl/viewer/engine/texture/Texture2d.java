package io.github.ititus.stellaris.lwjgl.viewer.engine.texture;

import org.lwjgl.opengl.GL32C;

public class Texture2d extends Texture<Texture2d> {

    public Texture2d() {
        super(GL32C.GL_TEXTURE_2D);
    }
}
