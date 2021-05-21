package io.github.ititus.dds;

import javax.imageio.ImageTypeSpecifier;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static io.github.ititus.dds.DdsConstants.DDS_MAGIC;

public record DdsFile(
        DdsHeader header,
        DdsHeaderDxt10 header10,
        List<DdsResource> resources
) {

    public static DdsFile load(Path path) throws IOException {
        try (InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
            return load(is);
        }
    }

    public static DdsFile load(InputStream is) throws IOException {
        return load(new DataReader() {
            @Override
            public byte readByte() throws IOException {
                int n = is.read();
                if (n == -1) {
                    throw new EOFException();
                }

                return (byte) n;
            }

            @Override
            public void read(ByteBuffer target, int size) throws IOException {
                if (target.hasArray()) {
                    if (is.readNBytes(target.array(), target.arrayOffset() + target.position(), size) != size) {
                        throw new EOFException();
                    }
                    target.position(target.position() + size);
                } else {
                    byte[] arr = new byte[size];
                    if (is.readNBytes(arr, 0, size) != size) {
                        throw new EOFException();
                    }
                    target.put(arr, 0, size);
                }
            }
        });
    }

    public static DdsFile load(DataInput di) throws IOException {
        return load(new DataReader() {
            @Override
            public byte readByte() throws IOException {
                return di.readByte();
            }

            @Override
            public void read(ByteBuffer target, int size) throws IOException {
                if (target.hasArray()) {
                    di.readFully(target.array(), target.arrayOffset() + target.position(), size);
                    target.position(target.position() + size);
                } else {
                    byte[] arr = new byte[size];
                    di.readFully(arr, 0, size);
                    target.put(arr, 0, size);
                }
            }
        });
    }

    public static DdsFile load(DataReader r) throws IOException {
        int dwMagic = r.readDword();
        if (dwMagic != DDS_MAGIC) {
            throw new IOException("invalid dds magic");
        }

        DdsHeader header = DdsHeader.load(r);
        if (!header.isValid()) {
            throw new IOException("invalid dds header");
        }

        DdsHeaderDxt10 header10;
        if (header.shouldLoadHeader10()) {
            header10 = DdsHeaderDxt10.load(r);
        } else {
            header10 = null;
        }

        int resourceCount = header10 != null ? header10.resourceCount() : 1;
        DdsResource[] resources = new DdsResource[resourceCount];
        for (int i = 0; i < resourceCount; i++) {
            resources[i] = DdsResource.load(r, header, header10);
        }

        int unconsumed = 0;
        try {
            for (; ; unconsumed++) {
                r.readByte();
            }
        } catch (EOFException ignored) {
            if (unconsumed > 0) {
                throw new IOException(unconsumed + " unconsumed bytes");
            }
        }

        return new DdsFile(header, header10, List.of(resources));
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

    public boolean isFlatTexture() {
        return header.isFlatTexture();
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

    public D3dFormat d3dFormat() {
        return header.d3dFormat();
    }

    public DxgiFormat dxgiFormat() {
        return header10 != null ? header10.dxgiFormat() : DxgiFormat.UNKNOWN;
    }

    public int resourceCount() {
        return header10 != null ? header10.resourceCount() : 1;
    }

    public ImageTypeSpecifier imageType() {
        if (header10 != null) {
            return header10.imageType();
        }

        return header.imageType();
    }

    @Override
    public String toString() {
        return "DdsFile[" +
                "header=" + header +
                (header10 == null ? "" : ", header10=" + header10) +
                ']';
    }
}
