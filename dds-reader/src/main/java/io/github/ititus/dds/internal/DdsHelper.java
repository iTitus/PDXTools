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
        return Byte.toUnsignedInt(bytes[2]) | (Byte.toUnsignedInt(bytes[1]) << 8) | (Byte.toUnsignedInt(bytes[0]) << 16);
    }

    public static int read24LE(ByteBuffer b) {
        byte[] bytes = new byte[3];
        b.get(bytes);
        return Byte.toUnsignedInt(bytes[0]) | (Byte.toUnsignedInt(bytes[1]) << 8) | (Byte.toUnsignedInt(bytes[2]) << 16);
    }

    public static int ip_half(int a, int b) {
        return (a + b) / 2;
    }

    public static int ip_third(int a, int b) {
        return (2 * a + b) / 3;
    }

    public static byte ip_fifth(byte a, byte b, int n) {
        return (byte) (((5 - n) * Byte.toUnsignedInt(a) + n * Byte.toUnsignedInt(b)) / 5);
    }

    public static byte ip_seventh(byte a, byte b, int n) {
        return (byte) (((7 - n) * Byte.toUnsignedInt(a) + n * Byte.toUnsignedInt(b)) / 7);
    }

    public static short ip_half_565(short c0, short c1) {
        return (short) ((ip_half(c0 & 0xf800, c1 & 0xf800) & 0xf800) | (ip_half(c0 & 0x7e0, c1 & 0x7e0) & 0x7e0) | (ip_half(c0 & 0x1f, c1 & 0x1f) & 0x1f));
    }

    public static short ip_third_565(short c0, short c1) {
        return (short) ((ip_third(c0 & 0xf800, c1 & 0xf800) & 0xf800) | (ip_third(c0 & 0x7e0, c1 & 0x7e0) & 0x7e0) | (ip_third(c0 & 0x1f, c1 & 0x1f) & 0x1f));
    }
}
