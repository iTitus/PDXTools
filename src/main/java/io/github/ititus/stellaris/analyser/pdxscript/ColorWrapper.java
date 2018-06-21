package io.github.ititus.stellaris.analyser.pdxscript;

import io.github.ititus.stellaris.analyser.util.ArrayUtil;

import java.awt.*;
import java.util.Arrays;

public class ColorWrapper {

    private final float[] colorComponents;
    private final Color color;
    private final Type type;

    public ColorWrapper(Type type, float... colorComponents) {
        this.colorComponents = Arrays.copyOf(colorComponents, 3);
        this.color = type == Type.HSV ? Color.getHSBColor(colorComponents[0], colorComponents[1], colorComponents[2]) : new Color(colorComponents[0], colorComponents[1], colorComponents[2]);
        this.type = type;
    }

    public ColorWrapper(Type type, double... colorComponents) {
        this(type, ArrayUtil.toFloatArray(colorComponents));
    }

    public float[] getColorComponents() {
        return Arrays.copyOf(colorComponents, 3);
    }

    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public String toPdxScript() {
        String s = (type == Type.HSV ? PdxScriptParser.HSV : PdxScriptParser.RGB) + " { ";
        for (float c : colorComponents) {
            s += c + " ";
        }
        return s + "}";
    }

    enum Type {
        HSV, RGB
    }
}
