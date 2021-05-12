package io.github.ititus.dds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public record DdsHeaderDxt10(
        DxgiFormat dxgiFormat,
        D3d10ResourceDimension resourceDimension,
        int miscFlag,
        int arraySize,
        int miscFlags2
) {

    public static DdsHeaderDxt10 load(DataReader r) throws IOException {
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
        List<String> list = new ArrayList<>(5);
        list.add("dxgiFormat=" + dxgiFormat);
        list.add("resourceDimension=" + resourceDimension);
        if (miscFlag != 0) {
            list.add("miscFlag=0x" + Integer.toHexString(miscFlag));
        }
        if (arraySize != 0) {
            list.add("arraySize=" + arraySize);
        }
        if (miscFlags2 != 0) {
            list.add("miscFlags2=0x" + Integer.toHexString(miscFlags2));
        }
        return "DdsHeaderDxt10[" + String.join(", ", list) + ']';
    }
}
