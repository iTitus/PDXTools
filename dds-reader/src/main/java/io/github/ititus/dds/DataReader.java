package io.github.ititus.dds;

import java.io.DataInput;
import java.io.IOException;

@FunctionalInterface
public interface DataReader {

    static int readDword(DataInput i) throws IOException {
        byte b1 = i.readByte();
        byte b2 = i.readByte();
        byte b3 = i.readByte();
        byte b4 = i.readByte();
        return Byte.toUnsignedInt(b1) | (Byte.toUnsignedInt(b2) << 8) | (Byte.toUnsignedInt(b3) << 16) | (Byte.toUnsignedInt(b4) << 24);
    }

    byte readByte();

    default int readDword() {
        byte b1 = readByte();
        byte b2 = readByte();
        byte b3 = readByte();
        byte b4 = readByte();
        return Byte.toUnsignedInt(b1) | (Byte.toUnsignedInt(b2) << 8) | (Byte.toUnsignedInt(b3) << 16) | (Byte.toUnsignedInt(b4) << 24);
    }

    default int[] readDwordArray(int length) {
        int[] arr = new int[length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = readDword();
        }

        return arr;
    }

    default int readUInt() {
        return readDword();
    }
}
