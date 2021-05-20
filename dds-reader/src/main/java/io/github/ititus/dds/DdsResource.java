package io.github.ititus.dds;

import io.github.ititus.dds.internal.DdsHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DdsResource {

    private final List<DdsSurface> surfaces;

    private DdsResource(List<DdsSurface> surfaces) {
        this.surfaces = surfaces;
    }

    public static DdsResource load(DataReader r, DdsHeader header, DdsHeaderDxt10 header10) throws IOException {
        if (header10 != null) {
            throw new UnsupportedOperationException("unsupported format");
        }

        List<DdsSurface> surfaces = new ArrayList<>();

        D3dFormat d3dFormat = header.d3dFormat();

        int mipMapCount = header.hasMipmaps() ? Math.max(1, header.dwMipMapCount()) : 1;
        int depth = header.isVolumeTexture() ? Math.max(1, header.dwDepth()) : 1;
        int faces = header.isCubemap() ? Math.max(1, header.calculateCubemapFaces()) : 1;

        for (int face = 0; face < faces; face++) {
            int height = header.dwHeight();
            int width = header.dwWidth();
            for (int mipmap = 0; mipmap < mipMapCount; mipmap++) {
                int size = DdsHelper.calculateSurfaceSize(height, width, d3dFormat) * depth;
                surfaces.add(DdsSurface.load(r, size));

                if (height == 1 && width == 1) {
                    break;
                }

                height = Math.max(1, height / 2);
                width = Math.max(1, width / 2);
            }
        }

        return new DdsResource(List.copyOf(surfaces));
    }

    public List<DdsSurface> getSurfaces() {
        return surfaces;
    }
}
