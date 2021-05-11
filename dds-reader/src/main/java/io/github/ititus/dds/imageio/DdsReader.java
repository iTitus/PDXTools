package io.github.ititus.dds.imageio;

import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

public class DdsReader extends ImageReader {

    private ImageInputStream stream;

    public DdsReader(DdsImageReaderSpi originator) {
        super(originator);
    }

    @Override
    public int getNumImages(boolean allowSearch) throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public int getWidth(int imageIndex) throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public int getHeight(int imageIndex) throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public Iterator<ImageTypeSpecifier> getImageTypes(int imageIndex) throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public IIOMetadata getStreamMetadata() throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public IIOMetadata getImageMetadata(int imageIndex) throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public BufferedImage read(int imageIndex, ImageReadParam param) throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public void setInput(Object input, boolean seekForwardOnly, boolean ignoreMetadata) {
        super.setInput(input, seekForwardOnly, ignoreMetadata);
        this.stream = (ImageInputStream) input;
    }
}
