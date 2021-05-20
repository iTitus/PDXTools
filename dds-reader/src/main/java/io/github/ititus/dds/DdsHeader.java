package io.github.ititus.dds;

import javax.imageio.ImageTypeSpecifier;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.github.ititus.dds.DdsConstants.*;

public record DdsHeader(
        int dwSize,
        int dwFlags,
        int dwHeight,
        int dwWidth,
        int dwPitchOrLinearSize,
        int dwDepth,
        int dwMipMapCount,
        int dwReserved1_0,
        int dwReserved1_1,
        int dwReserved1_2,
        int dwReserved1_3,
        int dwReserved1_4,
        int dwReserved1_5,
        int dwReserved1_6,
        int dwReserved1_7,
        int dwReserved1_8,
        int dwReserved1_9,
        int dwReserved1_10,
        DdsPixelformat ddspf,
        int dwCaps,
        int dwCaps2,
        int dwCaps3,
        int dwCaps4,
        int dwReserved2
) {

    public static final int SIZE = 124;

    public static DdsHeader load(DataReader r) throws IOException {
        return new DdsHeader(
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                DdsPixelformat.load(r),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword()
        );
    }

    public boolean isValid() {
        return isValid(false);
    }

    public boolean isValid(boolean strict) {
        if (dwSize != SIZE) {
            return false;
        } else if (strict && (dwCaps & DDSCAPS_TEXTURE) != DDSCAPS_TEXTURE) {
            return false;
        } else if ((dwFlags & DDS_HEADER_FLAGS_TEXTURE) != DDS_HEADER_FLAGS_TEXTURE) {
            return false;
        } else if (isUncompressed() && isCompressed()) {
            return false;
        } else if (isCubemap() && isVolumeTexture()) {
            return false;
        } else if (hasDepth() && !isVolumeTexture()) {
            return false;
        } else if (strict && isBlockCompressed() && (dwHeight % 4 != 0 || dwWidth % 4 != 0)) {
            return false;
        }

        return ddspf.isValid(strict);
    }

    public boolean shouldLoadHeader10() {
        return ddspf.shouldLoadHeader10();
    }

    public boolean isUncompressed() {
        return (dwFlags & DDS_HEADER_FLAGS_PITCH) == DDS_HEADER_FLAGS_PITCH;
    }

    public boolean isCompressed() {
        return (dwFlags & DDS_HEADER_FLAGS_LINEARSIZE) == DDS_HEADER_FLAGS_LINEARSIZE;
    }

    public boolean isBlockCompressed() {
        return d3dFormat().isBlockCompressed();
    }

    public boolean hasDepth() {
        return (dwFlags & DDS_HEADER_FLAGS_VOLUME) == DDS_HEADER_FLAGS_VOLUME;
    }

    public boolean hasMipmaps() {
        return (dwFlags & DDSD_MIPMAPCOUNT) == DDSD_MIPMAPCOUNT
                && (dwCaps & DDSCAPS_MIPMAP) == DDSCAPS_MIPMAP;
    }

    public boolean isFlatTexture() {
        return !isCubemap() && !isVolumeTexture();
    }

    public boolean isCubemap() {
        return (dwCaps2 & DDSCAPS2_CUBEMAP) == DDSCAPS2_CUBEMAP;
    }

    public boolean isVolumeTexture() {
        return (dwCaps2 & DDSCAPS2_VOLUME) == DDSCAPS2_VOLUME;
    }

    public D3dFormat d3dFormat() {
        return ddspf.d3dFormat();
    }

    public ImageTypeSpecifier imageType() {
        return ddspf.imageType();
    }

    public int calculateCubemapFaces() {
        int faces = 0;
        if ((dwCaps2 & DDSCAPS2_CUBEMAP_POSITIVEX) == DDSCAPS2_CUBEMAP_POSITIVEX) {
            faces++;
        }
        if ((dwCaps2 & DDSCAPS2_CUBEMAP_NEGATIVEX) == DDSCAPS2_CUBEMAP_NEGATIVEX) {
            faces++;
        }
        if ((dwCaps2 & DDSCAPS2_CUBEMAP_POSITIVEY) == DDSCAPS2_CUBEMAP_POSITIVEY) {
            faces++;
        }
        if ((dwCaps2 & DDSCAPS2_CUBEMAP_NEGATIVEY) == DDSCAPS2_CUBEMAP_NEGATIVEY) {
            faces++;
        }
        if ((dwCaps2 & DDSCAPS2_CUBEMAP_POSITIVEZ) == DDSCAPS2_CUBEMAP_POSITIVEZ) {
            faces++;
        }
        if ((dwCaps2 & DDSCAPS2_CUBEMAP_NEGATIVEZ) == DDSCAPS2_CUBEMAP_NEGATIVEZ) {
            faces++;
        }
        return faces;
    }

    @Override
    public String toString() {
        List<String> list = new ArrayList<>(24);
        if (dwSize != SIZE) {
            list.add("dwSize=" + dwSize);
        }
        if (dwFlags != 0) {
            list.add("dwFlags=0x" + Integer.toHexString(dwFlags));
        }
        list.add("dwHeight=" + dwHeight);
        list.add("dwWidth=" + dwWidth);
        if (dwPitchOrLinearSize != 0) {
            list.add((isUncompressed() ? "dwPitch=" : isCompressed() ? "dwLinearSize=" : "dwPitchOrLinearSize=") + dwPitchOrLinearSize);
        }
        if (dwDepth != 0) {
            list.add("dwDepth=" + dwDepth);
        }
        if (dwMipMapCount != 0) {
            list.add("dwMipMapCount=" + dwMipMapCount);
        }
        if (dwReserved1_0 != 0) {
            list.add("dwReserved[0]=" + dwReserved1_0);
        }
        if (dwReserved1_1 != 0) {
            list.add("dwReserved[1]=" + dwReserved1_1);
        }
        if (dwReserved1_2 != 0) {
            list.add("dwReserved[2]=" + dwReserved1_2);
        }
        if (dwReserved1_3 != 0) {
            list.add("dwReserved[3]=" + dwReserved1_3);
        }
        if (dwReserved1_4 != 0) {
            list.add("dwReserved[4]=" + dwReserved1_4);
        }
        if (dwReserved1_5 != 0) {
            list.add("dwReserved[5]=" + dwReserved1_5);
        }
        if (dwReserved1_6 != 0) {
            list.add("dwReserved[6]=" + dwReserved1_6);
        }
        if (dwReserved1_7 != 0) {
            list.add("dwReserved[7]=" + dwReserved1_7);
        }
        if (dwReserved1_8 != 0) {
            list.add("dwReserved[8]=" + dwReserved1_8);
        }
        if (dwReserved1_9 != 0) {
            list.add("dwReserved[9]=" + dwReserved1_9);
        }
        if (dwReserved1_10 != 0) {
            list.add("dwReserved[10]=" + dwReserved1_10);
        }
        if (dwCaps != 0) {
            list.add("dwCaps=0x" + Integer.toHexString(dwCaps));
        }
        if (dwCaps2 != 0) {
            list.add("dwCaps2=0x" + Integer.toHexString(dwCaps2));
        }
        if (dwCaps3 != 0) {
            list.add("dwCaps3=0x" + Integer.toHexString(dwCaps3));
        }
        if (dwCaps4 != 0) {
            list.add("dwCaps4=0x" + Integer.toHexString(dwCaps4));
        }
        if (dwReserved2 != 0) {
            list.add("dwReserved2=" + dwReserved2);
        }
        list.add("ddspf=" + ddspf);
        return "DdsHeader[" + String.join(", ", list) + ']';
    }
}
