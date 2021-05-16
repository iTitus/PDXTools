package io.github.ititus.dds;

import java.io.IOException;
import java.util.List;

public class DdsResource {

    private final List<DdsSurface> surfaces;

    private DdsResource(List<DdsSurface> surfaces) {
        this.surfaces = surfaces;
    }

    public static DdsResource load(DataReader r, DdsHeader header, DdsHeaderDxt10 header10) throws IOException {
        return new DdsResource(List.of());
    }

    public List<DdsSurface> getSurfaces() {
        return surfaces;
    }
}
