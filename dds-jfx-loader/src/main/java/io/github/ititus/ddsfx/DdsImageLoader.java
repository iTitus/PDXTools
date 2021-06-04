package io.github.ititus.ddsfx;

import com.sun.javafx.iio.ImageFrame;
import com.sun.javafx.iio.ImageMetadata;
import com.sun.javafx.iio.ImageStorage;
import com.sun.javafx.iio.common.ImageLoaderImpl;
import com.sun.javafx.iio.common.ImageTools;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class DdsImageLoader extends ImageLoaderImpl {

    private final InputStream input;

    DdsImageLoader(InputStream input) {
        super(DdsDescriptor.getInstance());
        this.input = input;
    }

    @Override
    public void dispose() {
    }

    @Override
    public ImageFrame load(int imageIndex, int width, int height, boolean preserveAspectRatio, boolean smooth) throws IOException {
        ImageReader reader = ImageIO.getImageReadersByFormatName("dds").next();
        reader.setInput(ImageIO.createImageInputStream(input));
        BufferedImage img = reader.read(imageIndex);

        ColorModel cm = img.getColorModel();
        int w = img.getWidth();
        int h = img.getHeight();

        int[] out = ImageTools.computeDimensions(w, h, width, height, preserveAspectRatio);
        width = out[0];
        height = out[1];

        boolean hasAlpha = cm.hasAlpha();
        int bpp = hasAlpha ? 4 : 3;
        ImageStorage.ImageType type = hasAlpha ? (img.isAlphaPremultiplied() ? ImageStorage.ImageType.RGBA_PRE : ImageStorage.ImageType.RGBA) : ImageStorage.ImageType.RGB;

        ByteBuffer data = ByteBuffer.allocate(w * h * bpp);
        WritableRaster raster = img.getRaster();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Object pixel = raster.getDataElements(x, y, null);
                data.put((byte) cm.getRed(pixel));
                data.put((byte) cm.getGreen(pixel));
                data.put((byte) cm.getBlue(pixel));
                if (hasAlpha) {
                    data.put((byte) cm.getAlpha(pixel));
                }
            }
        }
        data.flip();

        ImageMetadata metadata = new ImageMetadata(null, null, null, null, null, null, null, width, height, null, null, null);

        ImageFrame frame = new ImageFrame(type, data, w, h, w * bpp, null, metadata);
        if (width != w || height != h) {
            frame = ImageTools.scaleImageFrame(frame, width, height, smooth);
        }

        return frame;
    }
}
