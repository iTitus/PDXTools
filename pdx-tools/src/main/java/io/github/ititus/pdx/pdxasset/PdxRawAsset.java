package io.github.ititus.pdx.pdxasset;

import org.eclipse.collections.api.factory.Stacks;
import org.eclipse.collections.api.stack.MutableStack;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public final class PdxRawAsset {

    private static final int HEADER_SIZE = 4;
    private static final byte[] BINARY_HEADER = toBytes("@@b@");

    private final PdxRawAssetObject data;

    public PdxRawAsset(Path path) {
        this.data = load(path);
    }

    private static int[] readIntArray(DataInput i) throws IOException {
        int length = readInt(i);
        int[] data = new int[length];
        for (int j = 0; j < length; j++) {
            data[j] = readInt(i);
        }
        return data;
    }

    private static float[] readFloatArray(DataInput i) throws IOException {
        int length = readInt(i);
        float[] data = new float[length];
        for (int j = 0; j < length; j++) {
            data[j] = readFloat(i);
        }
        return data;
    }

    private static String[] readStringArray(DataInput i) throws IOException {
        int length = readInt(i);
        String[] data = new String[length];
        for (int j = 0; j < length; j++) {
            int len = readInt(i);
            data[j] = readString(i, len);
        }
        return data;
    }

    private static Object readValueArray(DataInput i) throws IOException {
        byte type = i.readByte();
        return switch (type) {
            case 'i' -> readIntArray(i);
            case 'f' -> readFloatArray(i);
            case 's' -> readStringArray(i);
            default -> throw new IllegalArgumentException("invalid value type " + (char) type);
        };
    }

    private static String toString(byte[] bytes) {
        return new String(bytes, StandardCharsets.US_ASCII);
    }

    private static String toString(byte[] bytes, int length) {
        return new String(bytes, 0, length, StandardCharsets.US_ASCII);
    }

    private static String toString(ByteArrayOutputStream os) {
        return os.toString(StandardCharsets.US_ASCII);
    }

    private static byte[] toBytes(String s) {
        return s.getBytes(StandardCharsets.US_ASCII);
    }

    private static int readInt(DataInput i) throws IOException {
        byte b1 = i.readByte();
        byte b2 = i.readByte();
        byte b3 = i.readByte();
        byte b4 = i.readByte();
        return Byte.toUnsignedInt(b1) | (Byte.toUnsignedInt(b2) << 8) | (Byte.toUnsignedInt(b3) << 16) | (Byte.toUnsignedInt(b4) << 24);
    }

    private static float readFloat(DataInput i) throws IOException {
        return Float.intBitsToFloat(readInt(i));
    }

    private static String readString(DataInput i, int length) throws IOException {
        byte[] data = new byte[length];
        i.readFully(data);
        if (length > 0 && data[length - 1] == 0) {
            length--;
        }

        return toString(data, length);
    }

    private static String readNullTerminatedStringWithStart(DataInput i, byte start) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(16);
        os.write(start);

        byte b;
        while ((b = i.readByte()) != 0) {
            os.write(b);
        }
        return toString(os);
    }

    public PdxRawAssetObject getData() {
        return data;
    }

    private PdxRawAssetObject load(Path path) {
        try (DataInputStream is = new DataInputStream(new BufferedInputStream(Files.newInputStream(path)))) {
            return _load(is);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private PdxRawAssetObject _load(DataInput i) throws IOException {
        byte[] headerBytes = new byte[HEADER_SIZE];
        i.readFully(headerBytes);
        if (!Arrays.equals(BINARY_HEADER, headerBytes)) {
            throw new IllegalArgumentException("invalid header " + toString(headerBytes));
        }

        MutableStack<PdxRawAssetObject.Builder> objects = Stacks.mutable.of(PdxRawAssetObject.builder());
        byte type;
        while (true) {
            try {
                type = i.readByte();
            } catch (EOFException ignored) {
                break;
            }

            switch (type) {
                case '!' -> {
                    int length = i.readByte();
                    String name = readString(i, length);
                    Object value = readValueArray(i);
                    objects.peek().addProperty(name, value);
                }
                case '[' -> {
                    int depth = 1;
                    byte b;
                    while ((b = i.readByte()) == '[') {
                        depth++;
                    }
                    String name = readNullTerminatedStringWithStart(i, b);

                    int depthDiff = objects.size() - depth;
                    if (depthDiff >= 0) {
                        for (int j = 0; j < depthDiff; j++) {
                            objects.pop();
                        }

                        PdxRawAssetObject.Builder newBuilder = PdxRawAssetObject.builder();
                        objects.peek().addChild(name, newBuilder);
                        objects.push(newBuilder);
                    } else if (depthDiff == -1) {
                        PdxRawAssetObject.Builder newBuilder = PdxRawAssetObject.builder();
                        objects.peek().addChild(name, newBuilder);
                        objects.push(newBuilder);
                    } else {
                        throw new IllegalArgumentException("skipping depth levels not supported");
                    }
                }
                default -> throw new IllegalArgumentException("invalid content type " + (char) type);
            }
        }

        while (objects.size() > 1) {
            objects.pop();
        }

        return objects.pop().build();
    }
}
