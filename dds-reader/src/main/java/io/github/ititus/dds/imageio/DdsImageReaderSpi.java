package io.github.ititus.dds.imageio;

import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.util.Locale;

import static io.github.ititus.dds.DataReader.readDword;
import static io.github.ititus.dds.DdsConstants.DDS_MAGIC;

public class DdsImageReaderSpi extends ImageReaderSpi {

    public DdsImageReaderSpi() {
        super(
                "iTitus",
                "1.0.0",
                new String[] { "dds", "DDS" },
                new String[] { "dds" },
                new String[] { "image/vnd-ms.dds" },
                DdsImageReader.class.getName(),
                new Class<?>[] { ImageInputStream.class },
                null,
                false,
                null,
                null,
                null,
                null,
                false,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public boolean canDecodeInput(Object source) throws IOException {
        if (!(source instanceof ImageInputStream stream)) {
            return false;
        }

        stream.mark();
        int dwMagic = readDword(stream);
        stream.reset();

        return dwMagic == DDS_MAGIC;
    }

    @Override
    public ImageReader createReaderInstance(Object extension) throws IOException {
        return new DdsImageReader(this);
    }

    @Override
    public String getDescription(Locale locale) {
        return "DDS Image Reader";
    }
}
