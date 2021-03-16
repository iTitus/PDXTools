package io.github.ititus.pdx.util;

import java.awt.*;

public final class ColorUtil {

    private ColorUtil() {
    }

    public static Color fromRGBArray(Number... colorComponents) {
        if (colorComponents.length != 3 && colorComponents.length != 4) {
            throw new IllegalArgumentException();
        }
        Number rN = colorComponents[0];
        Number gN = colorComponents[1];
        Number bN = colorComponents[2];
        Number aN = colorComponents.length == 4 ? colorComponents[3] : null;

        float r = get(rN, 0, 255, 255, 0, 1);
        float g = get(gN, 0, 255, 255, 0, 1);
        float b = get(bN, 0, 255, 255, 0, 1);
        float a = getAlpha(aN, 1, 0, 255, 255, 0, 1);

        return new Color(r, g, b, a);
    }

    public static Color fromHSVArray(Number... colorComponents) {
        if (colorComponents.length != 3 && colorComponents.length != 4) {
            throw new IllegalArgumentException();
        }
        Number hN = colorComponents[0];
        Number sN = colorComponents[1];
        Number vN = colorComponents[2];
        Number aN = colorComponents.length == 4 ? colorComponents[3] : null;

        float h = get(hN, 0, 360, 360, 0, 1);
        float s = get(sN, 0, 100, 100, 0, 1);
        float v = get(vN, 0, 100, 100, 0, 1);
        float a = getAlpha(aN, 1, 0, 255, 255, 0, 1);

        return fromHSVA(h, s, v, a);
    }

    public static Color fromHSVA(float hue, float saturation, float value, float alpha) {
        int r, g, b, a = (int) (alpha * 255.0f + 0.5f);
        if (saturation == 0) {
            r = g = b = (int) (value * 255.0f + 0.5f);
        } else {
            float h = (hue - (float) Math.floor(hue)) * 6.0f;
            float f = h - (float) Math.floor(h);
            float p = value * (1.0f - saturation);
            float q = value * (1.0f - saturation * f);
            float t = value * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h) {
                case 0 -> {
                    r = (int) (value * 255.0f + 0.5f);
                    g = (int) (t * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                }
                case 1 -> {
                    r = (int) (q * 255.0f + 0.5f);
                    g = (int) (value * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                }
                case 2 -> {
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (value * 255.0f + 0.5f);
                    b = (int) (t * 255.0f + 0.5f);
                }
                case 3 -> {
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (q * 255.0f + 0.5f);
                    b = (int) (value * 255.0f + 0.5f);
                }
                case 4 -> {
                    r = (int) (t * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (value * 255.0f + 0.5f);
                }
                case 5 -> {
                    r = (int) (value * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (q * 255.0f + 0.5f);
                }
                default -> throw new RuntimeException(
                        "Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value + ", " + alpha);
            }
        }
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255 || a < 0 || a > 255) {
            throw new RuntimeException(
                    "Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + "," +
                            " " + value + ", " + alpha);
        }
        return new Color(r & 0xFF, g & 0xFF, b & 0xFF, a & 0xFF);
    }

    public static Color fromRGBHex(String hex) {
        if (hex != null && (hex.startsWith("#") || hex.startsWith("0x"))) {
            int l = hex.length();
            if (l >= 7 && l <= 10) {
                if (hex.startsWith("#")) {
                    hex = hex.substring(1);
                    l--;
                } else if (hex.startsWith("0x")) {
                    hex = hex.substring(2);
                    l -= 2;
                }

                if (l == 6 || l == 8) {
                    return new Color(Integer.parseUnsignedInt(hex, 16), l == 8);
                }
            }
        }

        throw new IllegalArgumentException();
    }

    private static float get(Number n, int lowerIntBound, int upperIntBound, float divider, float lowerFloatBound,
                             float upperFloatBound) {
        if (n instanceof Integer) {
            int i = n.intValue();
            if (i < lowerIntBound || i > upperIntBound) {
                throw new IllegalArgumentException();
            }
            return i / divider;
        } else if (n instanceof Double) {
            float f = n.floatValue();
            if (f < lowerFloatBound || upperFloatBound > 1) {
                throw new IllegalArgumentException();
            }
            return f;
        }

        throw new IllegalArgumentException();
    }

    private static float getAlpha(Number aN, float def, int lowerIntBound, int upperIntBound, float divider,
                                  float lowerFloatBound, float upperFloatBound) {
        if (aN instanceof Integer) {
            int i = aN.intValue();
            if (i < lowerIntBound || i > upperIntBound) {
                throw new IllegalArgumentException();
            }
            return i / divider;
        } else if (aN instanceof Double) {
            float a = aN.floatValue();
            if (a < lowerFloatBound || a > upperFloatBound) {
                throw new IllegalArgumentException();
            }
            return a;
        } else if (aN != null) {
            throw new IllegalArgumentException();
        }

        return def;
    }
}
