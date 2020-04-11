package io.github.ititus.pdx.pdxscript;

import io.github.ititus.pdx.util.ColorUtil;
import io.github.ititus.pdx.util.Deduplicator;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public final class PdxColorWrapper implements PdxConstants {

    private static final Deduplicator<PdxColorWrapper> DEDUPLICATOR = new Deduplicator<>();

    private final Color color;
    private final String representation;

    private PdxColorWrapper(Color color, String representation) {
        this.color = color;
        this.representation = representation/*.intern()*/;
    }

    public static PdxColorWrapper fromRGBHex(String hex) {
        if (hex == null || hex.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return DEDUPLICATOR.deduplicate(new PdxColorWrapper(ColorUtil.fromRGBHex(hex), hex));
    }

    public static PdxColorWrapper fromRGB(Number... colorComponents) {
        if (colorComponents == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder sb = new StringBuilder(RGB).append(SPACE_CHAR).append(LIST_OBJECT_OPEN_CHAR).append(SPACE_CHAR);
        Arrays.stream(colorComponents).forEachOrdered(c -> sb.append(c).append(SPACE_CHAR));
        return DEDUPLICATOR.deduplicate(new PdxColorWrapper(ColorUtil.fromRGBArray(colorComponents),
                sb.append(LIST_OBJECT_CLOSE_CHAR).toString()));
    }

    public static PdxColorWrapper fromHSV(Number... colorComponents) {
        if (colorComponents == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder sb = new StringBuilder(HSV).append(SPACE_CHAR).append(LIST_OBJECT_OPEN_CHAR).append(SPACE_CHAR);
        Arrays.stream(colorComponents).forEachOrdered(c -> sb.append(c).append(SPACE_CHAR));
        return DEDUPLICATOR.deduplicate(new PdxColorWrapper(ColorUtil.fromHSVArray(colorComponents),
                sb.append(LIST_OBJECT_CLOSE_CHAR).toString()));
    }

    public Color getColor() {
        return color;
    }

    public String getRepresentation() {
        return representation;
    }

    public String toPdxScript() {
        return representation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PdxColorWrapper)) {
            return false;
        }
        PdxColorWrapper that = (PdxColorWrapper) o;
        return Objects.equals(representation, that.representation) && Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(representation, color);
    }

    @Override
    public String toString() {
        return "PdxColorWrapper{" +
                "color=" + color +
                ", representation='" + representation + '\'' +
                '}';
    }
}
