package io.github.ititus.pdx.pdxscript;

import io.github.ititus.pdx.util.ColorUtil;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public final class PdxColorWrapper {

    private final Number[] colorComponents;
    private final Color color;
    private final Type type;

    public PdxColorWrapper(Type type, Number... colorComponents) {
        if (colorComponents == null) {
            throw new IllegalArgumentException();
        }
        this.colorComponents = Arrays.copyOf(colorComponents, colorComponents.length);
        if (type == Type.HSV) {
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
                    // throw new IllegalArgumentException();
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

            this.color = ColorUtil.fromHSVA(h, s, v, a);
        } else if (type == Type.RGB) {
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
            this.color = new Color(r, g, b, a);
        } else {
            throw new IllegalArgumentException();
        }
        this.type = type;
    }

    public Number[] getColorComponents() {
        return Arrays.copyOf(colorComponents, 3);
    }

    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public String toPdxScript() {
        StringBuilder b = new StringBuilder((type == Type.HSV ? PdxScriptParser.HSV : PdxScriptParser.RGB)).append(" { ");
        for (Number n : colorComponents) {
            b.append(n).append(" ");
        }
        return b.append("}").toString();
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
        return Arrays.equals(colorComponents, that.colorComponents) && Objects.equals(color, that.color) && type == that.type;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(color, type);
        result = 31 * result + Arrays.hashCode(colorComponents);
        return result;
    }

    @Override
    public String toString() {
        return "PdxColorWrapper{" +
                "colorComponents=" + Arrays.toString(colorComponents) +
                ", color=" + color +
                ", type=" + type +
                '}';
    }

    enum Type {
        RGB, HSV
    }
}
