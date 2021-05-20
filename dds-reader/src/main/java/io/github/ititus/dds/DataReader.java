package io.github.ititus.dds;

import java.io.DataInput;
import java.io.IOException;
import java.nio.ByteBuffer;

@FunctionalInterface
public interface DataReader {

    private static int bytesToDword(byte b1, byte b2, byte b3, byte b4) {
        return Byte.toUnsignedInt(b1) | (Byte.toUnsignedInt(b2) << 8) | (Byte.toUnsignedInt(b3) << 16) | (Byte.toUnsignedInt(b4) << 24);
    }

    static int readDword(DataInput i) throws IOException {
        return bytesToDword(i.readByte(), i.readByte(), i.readByte(), i.readByte());
    }

    byte readByte() throws IOException;

    default int readDword() throws IOException {
        return bytesToDword(readByte(), readByte(), readByte(), readByte());
    }

    default int readUInt() throws IOException {
        return readDword();
    }

    default void read(ByteBuffer target, int size) throws IOException {
        for (int i = 0; i < size; i++) {
            target.put(readByte());
        }
    }
}
