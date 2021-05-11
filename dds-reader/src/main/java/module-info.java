module io.github.ititus.dds {
    requires java.desktop;

    exports io.github.ititus.dds;

    provides javax.imageio.spi.ImageReaderSpi with io.github.ititus.dds.imageio.DdsImageReaderSpi;
}
