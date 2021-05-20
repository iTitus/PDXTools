package io.github.ititus.dds.internal;

import io.github.ititus.dds.D3dFormat;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class DdsHelper {

    private DdsHelper() {
    }

    public static int calculatePitch(D3dFormat d3dFormat, int width) {
        return switch (d3dFormat) {
            case DXT1 -> Math.max(1, ((width + 3) / 4)) * 8;
            case DXT2, DXT3, DXT4, DXT5 -> Math.max(1, ((width + 3) / 4)) * 16;
            case R8G8_B8G8, G8R8_G8B8, UYVY, YUY2 -> ((width + 1) >> 1) * 4;
            default -> (width * d3dFormat.getBitsPerPixel() + 7) / 8;
        };
    }

    public static int calculateSurfaceSize(int height, int width, D3dFormat d3dFormat) {
        int pitch = DdsHelper.calculatePitch(d3dFormat, width);
        return switch (d3dFormat) {
            case DXT1, DXT2, DXT3, DXT4, DXT5 -> pitch * Math.max(1, (height + 3) / 4);
            default -> pitch * height;
        };
    }

    public static int read24(ByteBuffer b) {
        return b.order() == ByteOrder.BIG_ENDIAN ? read24BE(b) : read24LE(b);
    }

    public static int read24BE(ByteBuffer b) {
        byte[] bytes = new byte[3];
        b.get(bytes);
        return (Byte.toUnsignedInt(bytes[0]) << 16) | (Byte.toUnsignedInt(bytes[1]) << 8) | Byte.toUnsignedInt(bytes[2]);
    }

    public static int read24LE(ByteBuffer b) {
        byte[] bytes = new byte[3];
        b.get(bytes);
        return Byte.toUnsignedInt(bytes[0]) | (Byte.toUnsignedInt(bytes[1]) << 8) | (Byte.toUnsignedInt(bytes[2]) << 16);
    }

    public static byte ip_u8(byte a, byte b, int n1, int n2) {
        return (byte) ((n1 * Byte.toUnsignedInt(a) + n2 * Byte.toUnsignedInt(b)) / (n1 + n2));
    }

    private static int ip_u16(int a, int b, int n1, int n2) {
        return (n1 * a + n2 * b) / (n1 + n2);
    }

    public static short ip_565(short c0, short c1, int n1, int n2) {
        int r = ip_u16(c0 & 0xf800, c1 & 0xf800, n1, n2) & 0xf800;
        int g = ip_u16(c0 & 0x7e0, c1 & 0x7e0, n1, n2) & 0x7e0;
        int b = ip_u16(c0 & 0x1f, c1 & 0x1f, n1, n2) & 0x1f;
        return (short) (r | g | b);
    }
}
