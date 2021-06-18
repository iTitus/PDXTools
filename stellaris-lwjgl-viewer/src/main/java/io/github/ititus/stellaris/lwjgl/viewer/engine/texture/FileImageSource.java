package io.github.ititus.stellaris.lwjgl.viewer.engine.texture;

import io.github.ititus.stellaris.lwjgl.viewer.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Objects;

public class FileImageSource implements ImageSource {

    private final String path;

    public FileImageSource(String path) {
        this.path = Objects.requireNonNull(path);
    }

    @Override
    public BufferedImage getImage() {
        BufferedImage img;
        try (InputStream is = Main.class.getResourceAsStream(path)) {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return img;
    }
}
