package io.github.ititus.pdx.util;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtil {

    public static final Pattern HEX_RGB_PATTERN = Pattern.compile("(0x|#)((([0-9A-Fa-f]{2})?)[0-9A-Fa-f]{6})");

    public static Color fromRGBArray(Number... colorComponents) {
        if (colorComponents.length != 3 && colorComponents.length != 4) {
            throw new IllegalArgumentException();
        }
        float r, g, b, a = 1;
        Number rN = colorComponents[0];
        Number gN = colorComponents[1];
        Number bN = colorComponents[2];
        Number aN = colorComponents.length == 4 ? colorComponents[3] : null;

        if (rN instanceof Integer) {
            int i = rN.intValue();
            if (i < 0 || i > 255) {
                throw new IllegalArgumentException();
            }
            r = i / 255F;
        } else if (rN instanceof Double) {
            r = rN.floatValue();
            if (r < 0 || r > 1) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }

        if (gN instanceof Integer) {
            int i = gN.intValue();
            if (i < 0 || i > 255) {
                throw new IllegalArgumentException();
            }
            g = i / 255F;
        } else if (gN instanceof Double) {
            g = gN.floatValue();
            if (g < 0 || g > 1) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }

        if (bN instanceof Integer) {
            int i = bN.intValue();
            if (i < 0 || i > 255) {
                throw new IllegalArgumentException();
            }
            b = i / 255F;
        } else if (bN instanceof Double) {
            b = bN.floatValue();
            if (b < 0 || b > 1) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }

        if (aN instanceof Integer) {
            int i = aN.intValue();
            if (i < 0 || i > 255) {
                throw new IllegalArgumentException();
            }
            a = i / 255F;
        } else if (aN instanceof Double) {
            a = aN.floatValue();
            if (a < 0 || a > 1) {
                throw new IllegalArgumentException();
            }
        } else if (aN != null) {
            throw new IllegalArgumentException();
        }

        return new Color(r, g, b, a);
    }

    public static Color fromHSVArray(Number... colorComponents) {
        if (colorComponents.length != 3 && colorComponents.length != 4) {
            throw new IllegalArgumentException();
        }
        float h, s, v, a = 1;
        Number hN = colorComponents[0];
        Number sN = colorComponents[1];
        Number vN = colorComponents[2];
        Number aN = colorComponents.length == 4 ? colorComponents[3] : null;

        if (hN instanceof Integer) {
            int i = hN.intValue();
            if (i < 0 || i > 360) {
                throw new IllegalArgumentException();
            }
            h = i / 360F;
        } else if (hN instanceof Double) {
            h = hN.floatValue();
            if (h < 0 || h > 1) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }

        if (sN instanceof Integer) {
            int i = sN.intValue();
            if (i < 0 || i > 100) {
                throw new IllegalArgumentException();
            }
            s = i / 100F;
        } else if (sN instanceof Double) {
            s = sN.floatValue();
            if (s < 0 || s > 1) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }

        if (vN instanceof Integer) {
            int i = vN.intValue();
            if (i < 0 || i > 100) {
                throw new IllegalArgumentException();
            }
            v = i / 100F;
        } else if (vN instanceof Double) {
            v = vN.floatValue();
            if (v < 0 || v > 1) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }

        if (aN instanceof Integer) {
            int i = aN.intValue();
            if (i < 0 || i > 255) {
                throw new IllegalArgumentException();
            }
            a = i / 255F;
        } else if (aN instanceof Double) {
            a = aN.floatValue();
            if (a < 0 || a > 1) {
                throw new IllegalArgumentException();
            }
        } else if (aN != null) {
            throw new IllegalArgumentException();
        }
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
                case 0:
                    r = (int) (value * 255.0f + 0.5f);
                    g = (int) (t * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 1:
                    r = (int) (q * 255.0f + 0.5f);
                    g = (int) (value * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 2:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (value * 255.0f + 0.5f);
                    b = (int) (t * 255.0f + 0.5f);
                    break;
                case 3:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (q * 255.0f + 0.5f);
                    b = (int) (value * 255.0f + 0.5f);
                    break;
                case 4:
                    r = (int) (t * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (value * 255.0f + 0.5f);
                    break;
                case 5:
                    r = (int) (value * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (q * 255.0f + 0.5f);
                    break;
                default:
                    throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value + ", " + alpha);
            }
        }
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255 || a < 0 || a > 255) {
            throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value + ", " + alpha);
        }
        return new Color(r & 0xFF, g & 0xFF, b & 0xFF, a & 0xFF);
    }

    public static Color fromRGBHex(String hex) {
        if (hex != null && hex.length() >= "#FFFFFF".length() && hex.length() <= "0xFFFFFFFF".length()) {
            Matcher m = HEX_RGB_PATTERN.matcher(hex);
            if (m.matches()) {
                String components = m.group(2);
                String alpha = m.group(3);
                Color c;
                if (alpha != null && !alpha.isEmpty()) {
                    c = new Color(Integer.parseUnsignedInt(components, 16), true);
                } else {
                    c = new Color(Integer.parseInt(components, 16));
                }
                return c;
            }
        }
        throw new IllegalArgumentException();
    }
}
