package io.github.ititus.stellaris.lwjgl.viewer.engine.texture;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class BufferedImageSource implements ImageSource {

    private final BufferedImage image;

    public BufferedImageSource(BufferedImage image) {
        this.image = Objects.requireNonNull(image);
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }
}
