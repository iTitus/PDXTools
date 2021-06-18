package io.github.ititus.stellaris.lwjgl.viewer.engine.texture;

import io.github.ititus.stellaris.lwjgl.viewer.engine.GlObject;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL32C.*;

public abstract class Texture<T extends Texture<T>> extends GlObject<T> {

    private final int target;

    protected Texture(int target) {
        this.target = target;
    }

    private static ByteBuffer getPixels(BufferedImage img, boolean alpha) {
        ByteBuffer bb = ByteBuffer.allocateDirect(img.getWidth() * img.getHeight() * (alpha ? 4 : 3));
        for (int y = img.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < img.getHeight(); x++) {
                int argb = img.getRGB(x, y);
                bb.put((byte) ((argb >> 16) & 0xff));
                bb.put((byte) ((argb >> 8) & 0xff));
                bb.put((byte) (argb & 0xff));
                if (alpha) {
                    bb.put((byte) ((argb >> 24) & 0xff));
                }
            }
        }

        bb.flip();
        return bb;
    }

    public static void activeTexture(int index) {
        glActiveTexture(GL_TEXTURE0 + index);
    }

    public void bind() {
        glBindTexture(target, id);
    }

    public void parameter(int pname, int param) {
        glTexParameteri(target, pname, param);
    }

    public void texImage2d(int level, int border, ImageSource imageSource) {
        BufferedImage img = imageSource.getImage();
        boolean alpha = img.getColorModel().hasAlpha();
        texImage2d(level, alpha ? GL_RGBA : GL_RGB, img.getWidth(), img.getHeight(), border, alpha ? GL_RGBA : GL_RGB, GL_UNSIGNED_BYTE, getPixels(img, alpha));
    }

    public void texImage2d(int level, int internalformat, int width, int height, int border, int format, int type, ByteBuffer pixels) {
        glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
    }

    public void generateMipmap() {
        glGenerateMipmap(target);
    }

    @Override
    protected int create() {
        return glGenTextures();
    }

    @Override
    protected void init() {
        // TODO: this
    }

    @Override
    protected void delete() {
        glDeleteTextures(id);
    }
}
