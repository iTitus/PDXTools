package io.github.ititus.pdx.util;

import java.awt.*;
import java.util.Objects;

public final class ColorUtil {

    private ColorUtil() {
    }

    public static Color fromRGBArray(Number... colorComponents) {
        if (colorComponents.length != 3 && colorComponents.length != 4) {
            throw new IllegalArgumentException("expected 3 or 4 color components");
        }

        float r = get(colorComponents[0], 255);
        float g = get(colorComponents[1], 255);
        float b = get(colorComponents[2], 255);
        float a = getAlpha(colorComponents.length == 4 ? colorComponents[3] : null);

        return new Color(r, g, b, a);
    }

    public static Color fromHSVArray(Number... colorComponents) {
        if (colorComponents.length != 3 && colorComponents.length != 4) {
            throw new IllegalArgumentException("expected 3 or 4 color components");
        }

        float h = get(colorComponents[0], 360);
        float s = get(colorComponents[1], 100);
        float v = get(colorComponents[2], 100);
        float a = getAlpha(colorComponents.length == 4 ? colorComponents[3] : null);

        return fromHSVA(h, s, v, a);
    }

    public static Color fromHSVA(float hue, float saturation, float value, float alpha) {
        int r, g, b;
        int a = rgbInt(alpha);
        if (saturation == 0) {
            r = g = b = rgbInt(value);
        } else {
            int i = (int) Math.floor(hue * 6);
            float f = hue * 6 - i;
            float p = value * (1 - saturation);
            float q = value * (1 - f * saturation);
            float t = value * (1 - (1 - f) * saturation);
            switch (i % 6) {
                case 0 -> {
                    r = rgbInt(value);
                    g = rgbInt(t);
                    b = rgbInt(p);
                }
                case 1 -> {
                    r = rgbInt(q);
                    g = rgbInt(value);
                    b = rgbInt(p);
                }
                case 2 -> {
                    r = rgbInt(p);
                    g = rgbInt(value);
                    b = rgbInt(t);
                }
                case 3 -> {
                    r = rgbInt(p);
                    g = rgbInt(q);
                    b = rgbInt(value);
                }
                case 4 -> {
                    r = rgbInt(t);
                    g = rgbInt(p);
                    b = rgbInt(value);
                }
                case 5 -> {
                    r = rgbInt(value);
                    g = rgbInt(p);
                    b = rgbInt(q);
                }
                default -> throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value + ", " + alpha);
            }
        }
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255 || a < 0 || a > 255) {
            throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value + ", " + alpha);
        }
        return new Color(r, g, b, a);
    }

    public static Color fromRGBHex(String hex) {
        int l = Objects.requireNonNull(hex).length();
        if (l < 7 || l > 10) {
            throw new IllegalArgumentException("unexpected color string length");
        }

        int start;
        if (hex.charAt(0) == '#') {
            start = 1;
            l--;
        } else if (hex.charAt(0) == '0' && hex.charAt(1) == 'x') {
            start = 2;
            l -= 2;
        } else {
            throw new IllegalArgumentException("unexpected color string prefix");
        }

        if (l == 6 || l == 8) {
            return new Color(Integer.parseUnsignedInt(hex, start, start + l, 16), l == 8);
        }

        throw new IllegalArgumentException("unexpected color string length");
    }

    private static float get(Number n, int upperIntBound) {
        if (n instanceof Integer) {
            int i = n.intValue();
            if (i < 0 || i > upperIntBound) {
                throw new IllegalArgumentException();
            }
            return i / (float) upperIntBound;
        } else if (n instanceof Double) {
            float f = n.floatValue();
            if (f < 0 || f > 2) {
                throw new IllegalArgumentException();
            } else if (f > 1) {
                f = 1; // some hsv colors have a 'value' > 1
            }
            return f;
        }

        throw new IllegalArgumentException();
    }

    private static float getAlpha(Number aN) {
        if (aN instanceof Integer) {
            int i = aN.intValue();
            if (i < 0 || i > 255) {
                throw new IllegalArgumentException();
            }
            return i / 255f;
        } else if (aN instanceof Double) {
            float a = aN.floatValue();
            if (a < 0 || a > 1) {
                throw new IllegalArgumentException();
            }
            return a;
        } else if (aN != null) {
            throw new IllegalArgumentException();
        }

        return 1;
    }

    private static int rgbInt(float f) {
        return (int) (f * 255.0f + 0.5f);
    }
}
