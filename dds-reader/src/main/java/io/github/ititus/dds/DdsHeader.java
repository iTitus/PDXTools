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
                "dwSize=" + dwSize +
                ", dwFlags=" + dwFlags +
                ", dwHeight=" + dwHeight +
                ", dwWidth=" + dwWidth +
                ", dwPitchOrLinearSize=" + dwPitchOrLinearSize +
                ", dwDepth=" + dwDepth +
                ", dwMipMapCount=" + dwMipMapCount +
                ", dwReserved1=" + Arrays.toString(dwReserved1) +
                ", ddspf=" + ddspf +
                ", dwCaps=" + dwCaps +
                ", dwCaps2=" + dwCaps2 +
                ", dwCaps3=" + dwCaps3 +
                ", dwCaps4=" + dwCaps4 +
                ", dwReserved2=" + dwReserved2 +
                ']';
    }
}
