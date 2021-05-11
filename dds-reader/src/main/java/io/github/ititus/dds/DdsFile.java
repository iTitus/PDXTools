package io.github.ititus.dds;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.github.ititus.dds.DdsConstants.DDS_MAGIC;

public record DdsFile(
        DdsHeader header,
        DdsHeaderDxt10 header10
) {

    public static DdsFile load(Path path) {
        try (InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
            return load(() -> {
                int n;
                try {
                    n = is.read();
                    if (n == -1) {
                        throw new EOFException();
                    }
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }

                return (byte) n;
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static DdsFile load(DataReader r) {
        int dwMagic = r.readDword();
        if (dwMagic != DDS_MAGIC) {
            throw new RuntimeException("invalid dds magic");
        }

        DdsHeader header = DdsHeader.load(r);
        if (!header.isValid()) {
            throw new RuntimeException("invalid dds header");
        }

        DdsHeaderDxt10 header10;
        if (header.shouldLoadHeader10()) {
            header10 = DdsHeaderDxt10.load(r);
        } else {
            header10 = null;
        }

        return new DdsFile(header, header10);
    }

    public int height() {
        return header.dwHeight();
    }

    public int width() {
        return header.dwWidth();
    }

    public boolean hasMipmaps() {
        return header.hasMipmaps();
    }

    public boolean isCubemap() {
        return header.isCubemap();
    }

    public boolean isVolumeTexture() {
        return header.isVolumeTexture();
    }

    public boolean isDx10() {
        return header10 != null;
    }

    public int resourceCount() {
        return header10 != null ? header10.resourceCount() : 1;
    }

    @Override
    public String toString() {
        return "DdsFile[" +
                "header=" + header +
                (header10 == null ? "" : ", header10=" + header10) +
                ']';
    }
}
