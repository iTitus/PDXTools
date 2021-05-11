package io.github.ititus.dds;

public record DdsHeaderDxt10(
        DxgiFormat dxgiFormat,
        D3d10ResourceDimension resourceDimension,
        int miscFlag,
        int arraySize,
        int miscFlags2
) {

    public static DdsHeaderDxt10 load(DataReader r) {
        return new DdsHeaderDxt10(
                DxgiFormat.load(r),
                D3d10ResourceDimension.load(r),
                r.readUInt(),
                r.readUInt(),
                r.readUInt()
        );
    }

    public int resourceCount() {
        return arraySize > 0 ? arraySize : 1;
    }

    @Override
    public String toString() {
        return "DdsHeaderDxt10[" +
                "dxgiFormat=" + dxgiFormat +
                ", resourceDimension=" + resourceDimension +
                (miscFlag == 0 ? "" : ", miscFlag=0x" + Integer.toHexString(miscFlag)) +
                (arraySize == 0 ? "" : ", arraySize=" + arraySize) +
                (miscFlags2 == 0 ? "" : ", miscFlags2=0x" + Integer.toHexString(miscFlags2)) +
                ']';
    }
}
