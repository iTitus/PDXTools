package io.github.ititus.dds;

import java.io.IOException;
import java.util.NoSuchElementException;

public enum D3d10ResourceDimension {

    D3D10_RESOURCE_DIMENSION_UNKNOWN(0),
    D3D10_RESOURCE_DIMENSION_BUFFER(1),
    D3D10_RESOURCE_DIMENSION_TEXTURE1D(2),
    D3D10_RESOURCE_DIMENSION_TEXTURE2D(3),
    D3D10_RESOURCE_DIMENSION_TEXTURE3D(4);

    public static final D3d10ResourceDimension DDS_DIMENSION_TEXTURE1D = D3D10_RESOURCE_DIMENSION_TEXTURE1D;
    public static final D3d10ResourceDimension DDS_DIMENSION_TEXTURE2D = D3D10_RESOURCE_DIMENSION_TEXTURE2D;
    public static final D3d10ResourceDimension DDS_DIMENSION_TEXTURE3D = D3D10_RESOURCE_DIMENSION_TEXTURE3D;

    private static final D3d10ResourceDimension[] VALUES = values();

    private final int value;

    D3d10ResourceDimension(int value) {
        this.value = value;
    }

    public static D3d10ResourceDimension load(DataReader r) throws IOException {
        try {
            return get(r.readUInt());
        } catch (NoSuchElementException e) {
            throw new IOException(e);
        }
    }

    public static D3d10ResourceDimension get(int value) {
        for (D3d10ResourceDimension d : VALUES) {
            if (d.value == value) {
                return d;
            }
        }

        throw new NoSuchElementException("unknown resource dimension");
    }

    public int value() {
        return value;
    }
}
