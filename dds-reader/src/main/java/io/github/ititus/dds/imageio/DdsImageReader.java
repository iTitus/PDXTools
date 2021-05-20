package io.github.ititus.dds.imageio;

import io.github.ititus.dds.D3dFormat;
import io.github.ititus.dds.DdsFile;
import io.github.ititus.dds.DdsResource;
import io.github.ititus.dds.DdsSurface;
import io.github.ititus.dds.internal.DdsHelper;

import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;
import java.util.List;

public class DdsImageReader extends ImageReader {

    private ImageInputStream stream;
    private DdsFile dds;

    public DdsImageReader(DdsImageReaderSpi originator) {
        super(originator);
    }

    private static void bc1(int h, int w, WritableRaster raster, ByteBuffer b) {
        for (int y = 0; y < h; y += 4) {
            for (int x = 0; x < w; x += 4) {
                short c0 = b.getShort();
                short c1 = b.getShort();

                int[] colors = new int[4];
                colors[0] = 0x10000 | Short.toUnsignedInt(c0);
                colors[1] = 0x10000 | Short.toUnsignedInt(c1);
                if (Short.compareUnsigned(c0, c1) > 0) {
                    colors[2] = 0x10000 | Short.toUnsignedInt(DdsHelper.ip_third_565(c0, c1));
                    colors[3] = 0x10000 | Short.toUnsignedInt(DdsHelper.ip_third_565(c1, c0));
                } else {
                    colors[2] = 0x10000 | Short.toUnsignedInt(DdsHelper.ip_half_565(c0, c1));
                    colors[3] = 0;
                }

                byte[] table = new byte[4];
                b.get(table);

                for (int y_ = 0; y_ < Math.min(4, h - y); y_++) {
                    for (int x_ = 0; x_ < Math.min(4, w - x); x_++) {
                        raster.setDataElements(x + x_, y + y_, new int[] { colors[(table[y_] >>> (2 * x_)) & 0x3] });
                    }
                }
            }
        }
    }

    private static void bc2(int h, int w, WritableRaster raster, ByteBuffer b) {
        for (int y = 0; y < h; y += 4) {
            for (int x = 0; x < w; x += 4) {
                byte[] alpha = new byte[8];
                b.get(alpha);

                short c0 = b.getShort();
                short c1 = b.getShort();

                short[] colors = new short[4];
                colors[0] = c0;
                colors[1] = c1;
                colors[2] = DdsHelper.ip_third_565(c0, c1);
                colors[3] = DdsHelper.ip_third_565(c1, c0);

                byte[] table = new byte[4];
                b.get(table);

                for (int y_ = 0; y_ < Math.min(4, h - y); y_++) {
                    for (int x_ = 0; x_ < Math.min(4, w - x); x_++) {
                        int index = (table[y_] >>> (2 * x_)) & 0x3;
                        int a = (alpha[2 * y_ + x_ / 2] >>> (4 * (x_ & 0x1))) & 0xf;
                        int color = (a << 16) | Short.toUnsignedInt(colors[index]);
                        raster.setDataElements(x + x_, y + y_, new int[] { color });
                    }
                }
            }
        }
    }

    private static void bc3(int h, int w, WritableRaster raster, ByteBuffer b) {
        for (int y = 0; y < h; y += 4) {
            for (int x = 0; x < w; x += 4) {
                byte a0 = b.get();
                byte a1 = b.get();

                byte[] alpha = new byte[8];
                alpha[0] = a0;
                alpha[1] = a1;
                if (Byte.compareUnsigned(a0, a1) > 0) {
                    alpha[2] = DdsHelper.ip_seventh(a0, a1, 1);
                    alpha[3] = DdsHelper.ip_seventh(a0, a1, 2);
                    alpha[4] = DdsHelper.ip_seventh(a0, a1, 3);
                    alpha[5] = DdsHelper.ip_seventh(a0, a1, 4);
                    alpha[6] = DdsHelper.ip_seventh(a0, a1, 5);
                    alpha[7] = DdsHelper.ip_seventh(a0, a1, 6);
                } else {
                    alpha[2] = DdsHelper.ip_fifth(a0, a1, 1);
                    alpha[3] = DdsHelper.ip_fifth(a0, a1, 2);
                    alpha[4] = DdsHelper.ip_fifth(a0, a1, 3);
                    alpha[5] = DdsHelper.ip_fifth(a0, a1, 4);
                    alpha[6] = 0;
                    alpha[7] = -1;
                }

                int[] alphaTable = { DdsHelper.read24(b), DdsHelper.read24(b) };

                short c0 = b.getShort();
                short c1 = b.getShort();

                short[] colors = new short[4];
                colors[0] = c0;
                colors[1] = c1;
                colors[2] = DdsHelper.ip_third_565(c0, c1);
                colors[3] = DdsHelper.ip_third_565(c1, c0);

                byte[] colorTable = new byte[4];
                b.get(colorTable);

                for (int y_ = 0; y_ < Math.min(4, h - y); y_++) {
                    for (int x_ = 0; x_ < Math.min(4, w - x); x_++) {
                        int colorIndex = (colorTable[y_] >>> (2 * x_)) & 0x3;
                        int alphaIndex = (alphaTable[(4 * y_ + x_) / 8] >>> (3 * ((4 * y_ + x_) % 8))) & 0x7;
                        int color = (Byte.toUnsignedInt(alpha[alphaIndex]) << 16) | Short.toUnsignedInt(colors[colorIndex]);
                        raster.setDataElements(x + x_, y + y_, new int[] { color });
                    }
                }
            }
        }
    }

    @Override
    public int getNumImages(boolean allowSearch) throws IOException {
        if (seekForwardOnly && allowSearch) {
            throw new IllegalStateException("seekForwardOnly and allowSearch can't both be true!");
        }

        load();
        return dds.resourceCount();
    }

    @Override
    public int getWidth(int imageIndex) throws IOException {
        loadAndCheckIndex(imageIndex);
        return dds.width();
    }

    @Override
    public int getHeight(int imageIndex) throws IOException {
        loadAndCheckIndex(imageIndex);
        return dds.height();
    }

    @Override
    public Iterator<ImageTypeSpecifier> getImageTypes(int imageIndex) throws IOException {
        loadAndCheckIndex(imageIndex);
        return List.of(dds.imageType()).iterator();
    }

    @Override
    public IIOMetadata getStreamMetadata() {
        return null;
    }

    @Override
    public IIOMetadata getImageMetadata(int imageIndex) throws IOException {
        loadAndCheckIndex(imageIndex);
        return null;
    }

    @Override
    public BufferedImage read(int imageIndex, ImageReadParam param) throws IOException {
        loadAndCheckIndex(imageIndex);

        if (dds.header10() != null) {
            throw new UnsupportedOperationException("dx10 header not supported");
        }

        D3dFormat d3dFormat = dds.d3dFormat();
        DdsResource resource = dds.resources().get(imageIndex);
        DdsSurface surface = resource.getSurfaces().get(0);

        int bpp = d3dFormat.getBitsPerPixel();

        int h = getHeight(imageIndex);
        int w = getWidth(imageIndex);

        BufferedImage img = getDestination(param, getImageTypes(imageIndex), w, h);
        WritableRaster raster = img.getRaster();

        ByteBuffer b = surface.getBuffer();
        b.order(ByteOrder.LITTLE_ENDIAN);

        if (d3dFormat.isBlockCompressed()) {
            switch (d3dFormat) {
                case DXT1 -> bc1(h, w, raster, b);
                case DXT2, DXT3 -> bc2(h, w, raster, b);
                case DXT4, DXT5 -> bc3(h, w, raster, b);
                default -> throw new RuntimeException("illegal block compression");
            }
        } else {
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    Object arr = switch (bpp) {
                        case 8 -> new byte[] { b.get() };
                        case 16 -> new short[] { b.getShort() };
                        case 24 -> new int[] { DdsHelper.read24(b) };
                        case 32 -> new int[] { b.getInt() };
                        default -> throw new RuntimeException("illegal bpp");
                    };
                    raster.setDataElements(x, y, arr);
                }
            }
        }

        return img;
    }

    @Override
    public void setInput(Object input, boolean seekForwardOnly, boolean ignoreMetadata) {
        super.setInput(input, seekForwardOnly, ignoreMetadata);
        this.stream = (ImageInputStream) input;
        _reset();
    }

    @Override
    public void reset() {
        super.reset();
        _reset();
    }

    private void checkSource() {
        if (stream == null) {
            throw new IllegalStateException("No input source set!");
        }
    }

    private void loadAndCheckIndex(int imageIndex) throws IOException {
        load();
        if (imageIndex < 0 || imageIndex >= dds.resourceCount()) {
            throw new IndexOutOfBoundsException("imageIndex " + imageIndex + " out of bounds: only " + dds.resourceCount() + "image(s) available!");
        }
    }

    private void _reset() {
        dds = null;
    }

    private void load() throws IOException {
        checkSource();
        if (dds != null) {
            return;
        }

        dds = DdsFile.load(stream);
    }
}
