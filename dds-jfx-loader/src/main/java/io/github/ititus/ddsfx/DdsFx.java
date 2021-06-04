package io.github.ititus.ddsfx;

import com.sun.javafx.iio.ImageStorage;

public final class DdsFx {

    private static boolean initialized = false;

    private DdsFx() {
    }

    public static synchronized void setup() {
        if (!initialized) {
            ImageStorage.addImageLoaderFactory(DdsImageLoaderFactory.getInstance());
            initialized = true;
        }
    }
}
