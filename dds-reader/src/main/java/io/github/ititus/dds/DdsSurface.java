package io.github.ititus.dds;

import java.io.IOException;
import java.nio.ByteBuffer;

public class DdsSurface {

    private final ByteBuffer buffer;

    private DdsSurface(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public static DdsSurface load(DataReader r, int size) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(size);
        r.read(buf, size);
        buf.flip();
        return new DdsSurface(buf.asReadOnlyBuffer());
    }

    public ByteBuffer getBuffer() {
        return buffer.duplicate();
    }
}
