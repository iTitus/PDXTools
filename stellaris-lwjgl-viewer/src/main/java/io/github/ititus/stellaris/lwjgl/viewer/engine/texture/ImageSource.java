package io.github.ititus.stellaris.lwjgl.viewer.engine.texture;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface ImageSource {

    BufferedImage getImage();

}
