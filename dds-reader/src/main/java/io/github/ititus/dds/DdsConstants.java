package io.github.ititus.dds;

public final class DdsConstants {

    // DdsFile.dwMagic
    public static final int DDS_MAGIC = make4CC("DDS ");

    // DdsHeader.dwFlags
    public static final int DDSD_CAPS = 0x1;
    public static final int DDSD_HEIGHT = 0x2;
    public static final int DDSD_WIDTH = 0x4;
    public static final int DDSD_PITCH = 0x8;
    public static final int DDSD_PIXELFORMAT = 0x1000;
    public static final int DDSD_MIPMAPCOUNT = 0x20000;
    public static final int DDSD_LINEARSIZE = 0x80000;
    public static final int DDSD_DEPTH = 0x800000;
    public static final int DDS_HEADER_FLAGS_TEXTURE = DDSD_CAPS | DDSD_HEIGHT | DDSD_WIDTH | DDSD_PIXELFORMAT;
    public static final int DDS_HEADER_FLAGS_MIPMAP = DDSD_MIPMAPCOUNT;
    public static final int DDS_HEADER_FLAGS_VOLUME = DDSD_DEPTH;
    public static final int DDS_HEADER_FLAGS_PITCH = DDSD_PITCH;
    public static final int DDS_HEADER_FLAGS_LINEARSIZE = DDSD_LINEARSIZE;

    // DdsHeader.dwCaps
    public static final int DDSCAPS_COMPLEX = 0x8;
    public static final int DDSCAPS_MIPMAP = 0x400000;
    public static final int DDSCAPS_TEXTURE = 0x1000;
    public static final int DDS_SURFACE_FLAGS_MIPMAP = DDSCAPS_COMPLEX | DDSCAPS_MIPMAP;
    public static final int DDS_SURFACE_FLAGS_TEXTURE = DDSCAPS_TEXTURE;
    public static final int DDS_SURFACE_FLAGS_CUBEMAP = DDSCAPS_COMPLEX;

    // DdsHeader.dwCaps2
    public static final int DDSCAPS2_CUBEMAP = 0x200;
    public static final int DDSCAPS2_CUBEMAP_POSITIVEX = 0x400;
    public static final int DDSCAPS2_CUBEMAP_NEGATIVEX = 0x800;
    public static final int DDSCAPS2_CUBEMAP_POSITIVEY = 0x1000;
    public static final int DDSCAPS2_CUBEMAP_NEGATIVEY = 0x2000;
    public static final int DDSCAPS2_CUBEMAP_POSITIVEZ = 0x4000;
    public static final int DDSCAPS2_CUBEMAP_NEGATIVEZ = 0x8000;
    public static final int DDSCAPS2_VOLUME = 0x200000;
    public static final int DDS_CUBEMAP_POSITIVEX = DDSCAPS2_CUBEMAP | DDSCAPS2_CUBEMAP_POSITIVEX;
    public static final int DDS_CUBEMAP_NEGATIVEX = DDSCAPS2_CUBEMAP | DDSCAPS2_CUBEMAP_NEGATIVEX;
    public static final int DDS_CUBEMAP_POSITIVEY = DDSCAPS2_CUBEMAP | DDSCAPS2_CUBEMAP_POSITIVEY;
    public static final int DDS_CUBEMAP_NEGATIVEY = DDSCAPS2_CUBEMAP | DDSCAPS2_CUBEMAP_NEGATIVEY;
    public static final int DDS_CUBEMAP_POSITIVEZ = DDSCAPS2_CUBEMAP | DDSCAPS2_CUBEMAP_POSITIVEZ;
    public static final int DDS_CUBEMAP_ALLFACES = DDS_CUBEMAP_POSITIVEX | DDS_CUBEMAP_NEGATIVEX | DDS_CUBEMAP_POSITIVEY | DDS_CUBEMAP_NEGATIVEY | DDS_CUBEMAP_POSITIVEZ | DDSCAPS2_CUBEMAP_NEGATIVEZ;
    public static final int DDS_CUBEMAP_NEGATIVEZ = DDSCAPS2_CUBEMAP | DDSCAPS2_CUBEMAP_NEGATIVEZ;
    public static final int DDS_FLAGS_VOLUME = DDSCAPS2_VOLUME;

    // DdsPixelformat.dwFlags
    public static final int DDPF_ALPHAPIXELS = 0x1;
    public static final int DDPF_ALPHA = 0x2;
    public static final int DDPF_FOURCC = 0x4;
    public static final int DDPF_RGB = 0x40;
    public static final int DDPF_YUV = 0x200;
    public static final int DDPF_LUMINANCE = 0x20000;
    public static final int DDS_RGBA = DDPF_RGB | DDPF_ALPHAPIXELS;

    // DdsPixelformat.dwFourCC
    public static final int D3DFMT_DXT1 = make4CC("DXT1");
    public static final int D3DFMT_DXT2 = make4CC("DXT2");
    public static final int D3DFMT_DXT3 = make4CC("DXT3");
    public static final int D3DFMT_DXT4 = make4CC("DXT4");
    public static final int D3DFMT_DXT5 = make4CC("DXT5");
    public static final int DDS_DX10 = make4CC("DX10");
    public static final int DXGI_FORMAT_BC4_UNORM = make4CC("BC4U");
    public static final int DXGI_FORMAT_BC4_SNORM = make4CC("BC4S");
    public static final int DXGI_FORMAT_BC5_UNORM = make4CC("ATI2");
    public static final int DXGI_FORMAT_BC5_SNORM = make4CC("BC5S");
    public static final int D3DFMT_R8G8_B8G8 = make4CC("RGBG");
    public static final int D3DFMT_G8R8_G8B8 = make4CC("GRGB");
    public static final int D3DFMT_UYVY = make4CC("UYVY");
    public static final int D3DFMT_YUY2 = make4CC("YUY2");
    public static final int D3DFMT_MULTI2_ARGB8 = make4CC("MET1");

    // DdsHeaderDxt10.miscFlags
    public static final int D3D10_RESOURCE_MISC_GENERATE_MIPS = 0x1;
    public static final int D3D10_RESOURCE_MISC_SHARED = 0x2;
    public static final int D3D10_RESOURCE_MISC_TEXTURECUBE = 0x4;
    public static final int D3D10_RESOURCE_MISC_SHARED_KEYEDMUTEX = 0x10;
    public static final int D3D10_RESOURCE_MISC_GDI_COMPATIBLE = 0x20;
    public static final int DDS_RESOURCE_MISC_TEXTURECUBE = D3D10_RESOURCE_MISC_TEXTURECUBE;

    // DdsHeaderDxt10.miscFlags2
    public static final int DDS_ALPHA_MODE_UNKNOWN = 0x0;
    public static final int DDS_ALPHA_MODE_STRAIGHT = 0x1;
    public static final int DDS_ALPHA_MODE_PREMULTIPLIED = 0x2;
    public static final int DDS_ALPHA_MODE_OPAQUE = 0x3;
    public static final int DDS_ALPHA_MODE_CUSTOM = 0x4;
    public static final int DDS_MISC_FLAGS2_ALPHA_MODE_MASK = 0x7;

    private DdsConstants() {
    }

    public static int make4CC(String s) {
        if (s.length() != 4) {
            throw new IllegalArgumentException("expected string with length 4");
        }

        return s.charAt(0) | s.charAt(1) << 8 | s.charAt(2) << 16 | s.charAt(3) << 24;
    }

    public static String getStringFrom4CC(int fourCC) {
        return String.valueOf((char) (fourCC & 0xFF))
                + (char) ((fourCC >> 8) & 0xFF)
                + (char) ((fourCC >> 16) & 0xFF)
                + (char) ((fourCC >> 24) & 0xFF);
    }
}
