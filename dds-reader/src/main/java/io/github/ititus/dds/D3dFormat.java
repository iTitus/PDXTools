package io.github.ititus.dds;

import java.io.IOException;
import java.util.NoSuchElementException;

public enum D3dFormat {

    UNKNOWN(0),
    R8G8B8(20),
    A8R8G8B8(21),
    X8R8G8B8(22),
    R5G6B5(23),
    X1R5G5B5(24),
    A1R5G5B5(25),
    A4R4G4B4(26),
    R3G3B2(27),
    A8(28),
    A8R3G3B2(29),
    X4R4G4B4(30),
    A2B10G10R10(31),
    A8B8G8R8(32),
    X8B8G8R8(33),
    G16R16(34),
    A2R10G10B10(35),
    A16B16G16R16(36),
    A8P8(40),
    P8(41),
    L8(50),
    A8L8(51),
    A4L4(52),
    V8U8(60),
    L6V5U5(61),
    X8L8V8U8(62),
    Q8W8V8U8(63),
    V16U16(64),
    A2W10V10U10(67),
    UYVY(DdsConstants.D3DFMT_UYVY),
    R8G8_B8G8(DdsConstants.D3DFMT_R8G8_B8G8),
    YUY2(DdsConstants.D3DFMT_YUY2),
    G8R8_G8B8(DdsConstants.D3DFMT_G8R8_G8B8),
    DXT1(DdsConstants.D3DFMT_DXT1),
    DXT2(DdsConstants.D3DFMT_DXT2),
    DXT3(DdsConstants.D3DFMT_DXT3),
    DXT4(DdsConstants.D3DFMT_DXT4),
    DXT5(DdsConstants.D3DFMT_DXT5),
    D16_LOCKABLE(70),
    D32(71),
    D15S1(73),
    D24S8(75),
    D24X8(77),
    D24X4S4(79),
    D16(80),
    D32F_LOCKABLE(82),
    D24FS8(83),
    D32_LOCKABLE(84),
    S8_LOCKABLE(85),
    L16(81),
    VERTEXDATA(100),
    INDEX16(101),
    INDEX32(102),
    Q16W16V16U16(110),
    MULTI2_ARGB8(DdsConstants.D3DFMT_MULTI2_ARGB8),
    R16F(111),
    G16R16F(112),
    A16B16G16R16F(113),
    R32F(114),
    G32R32F(115),
    A32B32G32R32F(116),
    CxV8U8(117),
    A1(118),
    A2B10G10R10_XR_BIAS(119),
    BINARYBUFFER(199),
    FORCE_DWORD(0x7fffffff);

    private static final D3dFormat[] VALUES = values();

    private final int value;

    D3dFormat(int value) {
        this.value = value;
    }

    public static D3dFormat load(DataReader r) throws IOException {
        try {
            return get(r.readDword());
        } catch (NoSuchElementException e) {
            throw new IOException(e);
        }
    }

    public static D3dFormat get(int value) {
        for (D3dFormat f : VALUES) {
            if (f != FORCE_DWORD && f.value == value) {
                return f;
            }
        }

        throw new NoSuchElementException("unknown d3d format");
    }

    public int value() {
        return ordinal();
    }

    public int getBitsPerPixel() {
        return switch (this) {
            case A1 -> 1;
            case DXT1 -> 4;
            case R3G3B2, A8, P8, L8, A4L4, S8_LOCKABLE, DXT2, DXT3, DXT4, DXT5 -> 8;
            case R5G6B5, X1R5G5B5, A1R5G5B5, A4R4G4B4, A8R3G3B2, X4R4G4B4, A8P8, A8L8, V8U8, L6V5U5, UYVY, YUY2, R8G8_B8G8, G8R8_G8B8, D16, D16_LOCKABLE, D15S1, L16, INDEX16, R16F, CxV8U8 -> 16;
            case R8G8B8 -> 24;
            case A8R8G8B8, X8R8G8B8, A2B10G10R10, A8B8G8R8, X8B8G8R8, G16R16, A2R10G10B10, X8L8V8U8, V16U16, A2W10V10U10, D32, D24S8, D24X8, D24X4S4, D24FS8, INDEX32, G16R16F, R32F, A2B10G10R10_XR_BIAS, MULTI2_ARGB8 -> 32;
            case A16B16G16R16, A16B16G16R16F, Q16W16V16U16, G32R32F -> 64;
            case A32B32G32R32F -> 128;
            default -> throw new IllegalStateException("unknown bpp for format " + this);
        };
    }

    public boolean isBlockCompressed() {
        return switch (this) {
            case DXT1, DXT2, DXT3, DXT4, DXT5 -> true;
            default -> false;
        };
    }
}
