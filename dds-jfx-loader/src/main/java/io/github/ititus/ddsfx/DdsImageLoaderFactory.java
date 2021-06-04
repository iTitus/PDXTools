package io.github.ititus.ddsfx;

import com.sun.javafx.iio.ImageFormatDescription;
import com.sun.javafx.iio.ImageLoader;
import com.sun.javafx.iio.ImageLoaderFactory;

import java.io.InputStream;

public class DdsImageLoaderFactory implements ImageLoaderFactory {

    private static final DdsImageLoaderFactory INSTANCE = new DdsImageLoaderFactory();

    private DdsImageLoaderFactory() {
    }

    public static ImageLoaderFactory getInstance() {
        return INSTANCE;
    }

    public ImageFormatDescription getFormatDescription() {
        return DdsDescriptor.getInstance();
    }

    public ImageLoader createImageLoader(InputStream input) {
        return new DdsImageLoader(input);
    }
}