package io.github.ititus.dds;

import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.util.Locale;

public class DdsImageReaderSpi extends ImageReaderSpi {

    @Override
    public boolean canDecodeInput(Object source) throws IOException {
        if (!(source instanceof ImageInputStream stream)) {
            return false;
        }

        byte[] b = new byte[4];
        stream.mark();
        stream.readFully(b);
        stream.reset();

        return b[0] == (byte) 'D'
                && b[1] == (byte) 'D'
                && b[2] == (byte) 'S'
                && b[3] == (byte) ' ';
    }

    @Override
    public ImageReader createReaderInstance(Object extension) throws IOException {
        return new DdsReader(this);
    }

    @Override
    public String getDescription(Locale locale) {
        return "DDS Image Reader";
    }
}
