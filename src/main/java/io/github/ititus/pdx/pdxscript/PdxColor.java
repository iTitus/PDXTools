package io.github.ititus.pdx.pdxscript;

import io.github.ititus.pdx.util.ColorUtil;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

import static io.github.ititus.pdx.pdxscript.PdxConstants.*;

public final class PdxColor {

    private final Color color;
    private final String representation;

    private PdxColor(Color color, String representation) {
        this.color = color;
        this.representation = representation;
    }

    public static PdxColor fromRGBHex(String hex) {
        if (hex == null || hex.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return new PdxColor(ColorUtil.fromRGBHex(hex), hex);
    }

    public static PdxColor fromRGB(Number... colorComponents) {
        if (colorComponents == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder sb = new StringBuilder(RGB).append(SPACE_CHAR).append(LIST_OBJECT_OPEN_CHAR).append(SPACE_CHAR);
        Arrays.stream(colorComponents).forEachOrdered(c -> sb.append(c).append(SPACE_CHAR));
        return new PdxColor(ColorUtil.fromRGBArray(colorComponents), sb.append(LIST_OBJECT_CLOSE_CHAR).toString());
    }

    public static PdxColor fromHSV(Number... colorComponents) {
        if (colorComponents == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder sb = new StringBuilder(HSV).append(SPACE_CHAR).append(LIST_OBJECT_OPEN_CHAR).append(SPACE_CHAR);
        Arrays.stream(colorComponents).forEachOrdered(c -> sb.append(c).append(SPACE_CHAR));
        return new PdxColor(ColorUtil.fromHSVArray(colorComponents), sb.append(LIST_OBJECT_CLOSE_CHAR).toString());
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
        if (!(o instanceof PdxColor)) {
            return false;
        }
        PdxColor that = (PdxColor) o;
        return Objects.equals(representation, that.representation) && Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(representation, color);
    }

    @Override
    public String toString() {
        return "PdxColor{" +
                "color=" + color +
                ", representation='" + representation + '\'' +
                '}';
    }
}
