package io.github.ititus.dds;

import java.util.Arrays;

import static io.github.ititus.dds.DdsConstants.*;

public record DdsHeader(
        int dwSize,
        int dwFlags,
        int dwHeight,
        int dwWidth,
        int dwPitchOrLinearSize,
        int dwDepth,
        int dwMipMapCount,
        int[/* length 11 */] dwReserved1,
        DdsPixelformat ddspf,
        int dwCaps,
        int dwCaps2,
        int dwCaps3,
        int dwCaps4,
        int dwReserved2
) {

    private static final int[] RESERVED_DEFAULT = new int[11];

    public static DdsHeader load(DataReader r) {
        return new DdsHeader(
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDwordArray(11),
                DdsPixelformat.load(r),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword()
        );
    }

    public boolean isValid() {
        if (dwSize != 124) {
            return false;
        } else /*else if ((dwCaps & DDSCAPS_TEXTURE) != DDSCAPS_TEXTURE) {
            return false;
        }*/ if ((dwFlags & DDS_HEADER_FLAGS_TEXTURE) != DDS_HEADER_FLAGS_TEXTURE) {
            return false;
        } else
            return ddspf.isValid();
    }

    public boolean shouldLoadHeader10() {
        return ddspf.shouldLoadHeader10();
    }

    public boolean hasMipmaps() {
        return (dwCaps & DDS_SURFACE_FLAGS_MIPMAP) == DDS_SURFACE_FLAGS_MIPMAP
                && (dwFlags & DDSD_MIPMAPCOUNT) == DDSD_MIPMAPCOUNT;
    }

    public boolean isCubemap() {
        return (dwCaps2 & DDSCAPS2_CUBEMAP) == DDSCAPS2_CUBEMAP;
    }

    public boolean isVolumeTexture() {
        return (dwCaps2 & DDSCAPS2_VOLUME) == DDSCAPS2_VOLUME;
    }

    @Override
    public String toString() {
        return "DdsHeader[" +
                (dwSize == 124 ? "" : "dwSize=" + dwSize + ", ") +
                (dwFlags == 0 ? "" : "dwFlags=0x" + Integer.toHexString(dwFlags) + ", ") +
                "dwHeight=" + dwHeight +
                ", dwWidth=" + dwWidth +
                ", dwPitchOrLinearSize=" + dwPitchOrLinearSize +
                (dwDepth == 0 ? "" : ", dwDepth=" + dwDepth) +
                (dwMipMapCount == 0 ? "" : ", dwMipMapCount=" + dwMipMapCount) +
                (Arrays.equals(dwReserved1, RESERVED_DEFAULT) ? "" : ", dwReserved1=" + Arrays.toString(dwReserved1)) +
                (dwCaps == 0 ? "" : ", dwCaps=0x" + Integer.toHexString(dwCaps)) +
                (dwCaps2 == 0 ? "" : ", dwCaps2=0x" + Integer.toHexString(dwCaps2)) +
                (dwCaps3 == 0 ? "" : ", dwCaps3=" + dwCaps3) +
                (dwCaps4 == 0 ? "" : ", dwCaps4=" + dwCaps4) +
                (dwReserved2 == 0 ? "" : ", dwReserved2=" + dwReserved2) +
                ", ddspf=" + ddspf +
                ']';
    }
}
