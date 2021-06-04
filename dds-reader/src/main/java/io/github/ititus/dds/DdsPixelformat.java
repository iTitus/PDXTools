package io.github.ititus.dds;

import javax.imageio.ImageTypeSpecifier;
import java.awt.color.ColorSpace;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DirectColorModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

    public static final int SIZE = 32;
    private static final int RGB_COLORSPACE = ColorSpace.CS_sRGB;

    public static DdsPixelformat load(DataReader r) throws IOException {
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

    public boolean isValid(boolean strict) {
        return dwSize == SIZE;
    }

    public D3dFormat d3dFormat() {
        if ((dwFlags & DDS_RGBA) == DDS_RGBA) {
            return switch (dwRGBBitCount) {
                case 16 -> {
                    if (dwRBitMask == 0x7c00 && dwGBitMask == 0x3e0 && dwBBitMask == 0x1f && dwABitMask == 0x8000) {
                        yield D3dFormat.A1R5G5B5;
                    } else if (dwRBitMask == 0xf00 && dwGBitMask == 0xf0 && dwBBitMask == 0xf && dwABitMask == 0xf000) {
                        yield D3dFormat.A4R4G4B4;
                    } else if (dwRBitMask == 0xe0 && dwGBitMask == 0x1c && dwBBitMask == 0x3 && dwABitMask == 0xff00) {
                        yield D3dFormat.A8R3G3B2;
                    }

                    yield D3dFormat.UNKNOWN;
                }
                case 32 -> {
                    if (dwRBitMask == 0xff && dwGBitMask == 0xff00 && dwBBitMask == 0xff0000 && dwABitMask == 0xff000000) {
                        yield D3dFormat.A8B8G8R8;
                    } else if (dwRBitMask == 0xffff && dwGBitMask == 0xffff0000) {
                        yield D3dFormat.G16R16;
                    } else if (dwRBitMask == 0x3ff && dwGBitMask == 0xffc00 && dwBBitMask == 0x3ff00000) {
                        yield D3dFormat.A2B10G10R10;
                    } else if (dwRBitMask == 0xff0000 && dwGBitMask == 0xff00 && dwBBitMask == 0xff && dwABitMask == 0xff000000) {
                        yield D3dFormat.A8R8G8B8;
                    } else if (dwRBitMask == 0x3ff00000 && dwGBitMask == 0xffc00 && dwBBitMask == 0x3ff && dwABitMask == 0xc0000000) {
                        yield D3dFormat.A2R10G10B10;
                    }

                    yield D3dFormat.UNKNOWN;
                }
                default -> D3dFormat.UNKNOWN;
            };
        } else if ((dwFlags & DDPF_RGB) == DDPF_RGB) {
            return switch (dwRGBBitCount) {
                case 16 -> {
                    if (dwRBitMask == 0xf800 && dwGBitMask == 0x7e0 && dwBBitMask == 0x1f) {
                        yield D3dFormat.R5G6B5;
                    } else if (dwRBitMask == 0x7c00 && dwGBitMask == 0x3e0 && dwBBitMask == 0x1f) {
                        yield D3dFormat.X1R5G5B5;
                    } else if (dwRBitMask == 0xf00 && dwGBitMask == 0xf0 && dwBBitMask == 0xf) {
                        yield D3dFormat.X4R4G4B4;
                    }

                    yield D3dFormat.UNKNOWN;
                }
                case 24 -> {
                    if (dwRBitMask == 0xff0000 && dwGBitMask == 0xff00 && dwBBitMask == 0xff) {
                        yield D3dFormat.R8G8B8;
                    }

                    yield D3dFormat.UNKNOWN;
                }
                case 32 -> {
                    if (dwRBitMask == 0xffff && dwGBitMask == 0xffff0000) {
                        yield D3dFormat.G16R16;
                    } else if (dwRBitMask == 0xff0000 && dwGBitMask == 0xff00 && dwBBitMask == 0xff) {
                        yield D3dFormat.X8R8G8B8;
                    } else if (dwRBitMask == 0xff && dwGBitMask == 0xff00 && dwBBitMask == 0xff0000) {
                        yield D3dFormat.X8B8G8R8;
                    }

                    yield D3dFormat.UNKNOWN;
                }
                default -> D3dFormat.UNKNOWN;
            };
        } else if ((dwFlags & DDPF_ALPHA) == DDPF_ALPHA) {
            return switch (dwRGBBitCount) {
                case 8 -> {
                    if (dwABitMask == 0xff) {
                        yield D3dFormat.A8;
                    }

                    yield D3dFormat.UNKNOWN;
                }
                default -> D3dFormat.UNKNOWN;
            };
        } else if ((dwFlags & DDPF_LUMINANCE) == DDPF_LUMINANCE) {
            return switch (dwRGBBitCount) {
                case 8 -> {
                    if (dwRBitMask == 0xf && dwABitMask == 0xf0) {
                        yield D3dFormat.A4L4;
                    } else if (dwRBitMask == 0xff) {
                        yield D3dFormat.L8;
                    }

                    yield D3dFormat.UNKNOWN;
                }
                case 16 -> {
                    if (dwRBitMask == 0xff && dwABitMask == 0xff00) {
                        yield D3dFormat.A8L8;
                    } else if (dwRBitMask == 0xffff) {
                        yield D3dFormat.L16;
                    }

                    yield D3dFormat.UNKNOWN;
                }
                default -> D3dFormat.UNKNOWN;
            };
        } else if ((dwFlags & DdsConstants.DDPF_FOURCC) == DdsConstants.DDPF_FOURCC) {
            try {
                return D3dFormat.get(dwFourCC);
            } catch (NoSuchElementException ignored) {
                return D3dFormat.UNKNOWN;
            }
        }

        return D3dFormat.UNKNOWN;
    }

    public ImageTypeSpecifier imageType() {
        if ((dwFlags & DDS_RGBA) == DDS_RGBA) {
            ColorModel cm = new DirectColorModel(
                    ColorSpace.getInstance(RGB_COLORSPACE),
                    dwRGBBitCount,
                    dwRBitMask,
                    dwGBitMask,
                    dwBBitMask,
                    dwABitMask,
                    false,
                    findBestTransferType()
            );
            return new ImageTypeSpecifier(
                    cm,
                    cm.createCompatibleSampleModel(1, 1)
            );
        } else if ((dwFlags & DDPF_RGB) == DDPF_RGB) {
            ColorModel cm = new DirectColorModel(
                    ColorSpace.getInstance(RGB_COLORSPACE),
                    dwRGBBitCount,
                    dwRBitMask,
                    dwGBitMask,
                    dwBBitMask,
                    0,
                    false,
                    findBestTransferType()
            );
            return new ImageTypeSpecifier(
                    cm,
                    cm.createCompatibleSampleModel(1, 1)
            );
        } else if ((dwFlags & DdsConstants.DDPF_FOURCC) == DdsConstants.DDPF_FOURCC) {
            if (dwFourCC == D3DFMT_DXT1) {
                ColorModel cm = new DirectColorModel(
                        ColorSpace.getInstance(RGB_COLORSPACE),
                        17,
                        0xf800,
                        0x7e0,
                        0x1f,
                        0x10000,
                        false,
                        DataBuffer.TYPE_INT
                );
                return new ImageTypeSpecifier(
                        cm,
                        cm.createCompatibleSampleModel(1, 1)
                );
            } else if (dwFourCC == D3DFMT_DXT2 || dwFourCC == D3DFMT_DXT3) {
                ColorModel cm = new DirectColorModel(
                        ColorSpace.getInstance(RGB_COLORSPACE),
                        20,
                        0xf800,
                        0x7e0,
                        0x1f,
                        0xf0000,
                        dwFourCC == D3DFMT_DXT2,
                        DataBuffer.TYPE_INT
                );
                return new ImageTypeSpecifier(
                        cm,
                        cm.createCompatibleSampleModel(1, 1)
                );
            } else if (dwFourCC == D3DFMT_DXT4 || dwFourCC == D3DFMT_DXT5) {
                ColorModel cm = new DirectColorModel(
                        ColorSpace.getInstance(RGB_COLORSPACE),
                        24,
                        0xf800,
                        0x7e0,
                        0x1f,
                        0xff0000,
                        dwFourCC == D3DFMT_DXT4,
                        DataBuffer.TYPE_INT
                );
                return new ImageTypeSpecifier(
                        cm,
                        cm.createCompatibleSampleModel(1, 1)
                );
            }
        }

        throw new UnsupportedOperationException();
    }

    private int findBestTransferType() {
        if (dwRGBBitCount <= 8) {
            return DataBuffer.TYPE_BYTE;
        } else if (dwRGBBitCount <= 16) {
            return DataBuffer.TYPE_USHORT;
        } else if (dwRGBBitCount <= 32) {
            return DataBuffer.TYPE_INT;
        }

        return DataBuffer.TYPE_UNDEFINED;
    }

    @Override
    public String toString() {
        List<String> list = new ArrayList<>(8);
        if (dwSize != SIZE) {
            list.add("dwSize=" + dwSize);
        }
        if (dwFlags != 0) {
            list.add("dwFlags=0x" + Integer.toHexString(dwFlags));
        }
        if (dwFourCC != 0) {
            list.add("dwFourCC=" + getStringFrom4CC(dwFourCC));
        }
        if (dwRGBBitCount != 0) {
            list.add("dwRGBBitCount=" + dwRGBBitCount);
        }
        if (dwRBitMask != 0) {
            list.add("dwRBitMask=0x" + Integer.toHexString(dwRBitMask));
        }
        if (dwGBitMask != 0) {
            list.add("dwGBitMask=0x" + Integer.toHexString(dwGBitMask));
        }
        if (dwBBitMask != 0) {
            list.add("dwBBitMask=0x" + Integer.toHexString(dwBBitMask));
        }
        if (dwABitMask != 0) {
            list.add("dwABitMask=0x" + Integer.toHexString(dwABitMask));
        }
        return "DdsPixelformat[" + String.join(", ", list) + ']';
    }
}
