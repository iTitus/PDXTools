package io.github.ititus.dds;

/**
 * Identifies the type of resource being used.
 */
public enum D3d10ResourceDimension {

    /**
     * Resource is of unknown type.
     */
    D3D10_RESOURCE_DIMENSION_UNKNOWN,

    /**
     * Resource is a buffer.
     */
    D3D10_RESOURCE_DIMENSION_BUFFER,

    /**
     * Resource is a 1D texture.
     */
    D3D10_RESOURCE_DIMENSION_TEXTURE1D,

    /**
     * Resource is a 2D texture.
     */
    D3D10_RESOURCE_DIMENSION_TEXTURE2D,

    /**
     * Resource is a 3D texture.
     */
    D3D10_RESOURCE_DIMENSION_TEXTURE3D;

    public static final D3d10ResourceDimension DDS_DIMENSION_TEXTURE1D = D3D10_RESOURCE_DIMENSION_TEXTURE1D;
    public static final D3d10ResourceDimension DDS_DIMENSION_TEXTURE2D = D3D10_RESOURCE_DIMENSION_TEXTURE2D;
    public static final D3d10ResourceDimension DDS_DIMENSION_TEXTURE3D = D3D10_RESOURCE_DIMENSION_TEXTURE3D;

    private static final D3d10ResourceDimension[] VALUES = values();

    public static D3d10ResourceDimension load(DataReader r) {
        return VALUES[r.readUInt()];
    }

    public int value() {
        return ordinal();
    }
}
