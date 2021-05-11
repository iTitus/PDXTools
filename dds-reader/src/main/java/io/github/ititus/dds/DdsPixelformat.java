package io.github.ititus.dds;

import static io.github.ititus.dds.DdsConstants.*;

public record DdsPixelformat(
        int dwSize,
        int dwFlags,
        int dwFourCC,
        int dwRGBBitCount,
        int dwRBitMask,
        int dwGBitMask,
        int dwBBitMask,
        int dwABitMask
) {

    public static DdsPixelformat load(DataReader r) {
        return new DdsPixelformat(
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword(),
                r.readDword()
        );
    }

    public boolean shouldLoadHeader10() {
        return dwFlags == DDPF_FOURCC && dwFourCC == DDS_DX10;
    }

    public boolean isValid() {
        if (dwSize != 32) {
            return false;
        } else
            return (dwFlags & DDPF_FOURCC) != DDPF_FOURCC
                    || dwFourCC == D3DFMT_DXT1
                    || dwFourCC == D3DFMT_DXT2
                    || dwFourCC == D3DFMT_DXT3
                    || dwFourCC == D3DFMT_DXT4
                    || dwFourCC == D3DFMT_DXT5
                    || dwFourCC == DDS_DX10;
    }

    @Override
    public String toString() {
        return "DdsPixelformat[" +
                "dwSize=" + dwSize +
                ", dwFlags=0x" + Integer.toHexString(dwFlags) +
                ", dwFourCC=" + (dwFourCC == 0 ? dwFourCC : getStringFrom4CC(dwFourCC)) +
                ", dwRGBBitCount=" + dwRGBBitCount +
                ", dwRBitMask=0x" + Integer.toHexString(dwRBitMask) +
                ", dwGBitMask=0x" + Integer.toHexString(dwGBitMask) +
                ", dwBBitMask=0x" + Integer.toHexString(dwBBitMask) +
                ", dwABitMask=0x" + Integer.toHexString(dwABitMask) +
                ']';
    }
}
