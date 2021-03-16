package io.github.ititus.pdx.pdxscript;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.ititus.pdx.pdxscript.PdxConstants.*;

public final class PdxHelper {

    private static final String[] INDENTS;

    static {
        INDENTS = new String[16];
        INDENTS[0] = EMPTY;
        StringBuilder last = new StringBuilder((INDENTS.length - 1) * INDENT.length());
        for (int i = 1; i < INDENTS.length; i++) {
            INDENTS[i] = last.append(INDENT).toString();
        }
    }

    private PdxHelper() {
    }

    public static String getTypeString(IPdxScript s) {
        if (s instanceof PdxScriptObject) {
            return OBJECT;
        } else if (s instanceof PdxScriptList) {
            if (((PdxScriptList) s).getMode() == PdxScriptList.Mode.IMPLICIT) {
                return IMPLICIT_LIST;
            }

            return LIST;
        } else if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof LocalDate) {
                return DATE;
            } else if (v instanceof Double) {
                return DOUBLE;
            } else if (v instanceof Long) {
                long l = (long) v;
                if (l >= Integer.MIN_VALUE && l < 0) {
                    return NEG_INT;
                } else if (l >= 0 && l <= Integer.MAX_VALUE) {
                    return INT;
                } else if (l > Integer.MAX_VALUE && l <= UNSIGNED_INT_MAX_LONG) {
                    return U_INT;
                }

                return LONG;
            } else if (v instanceof Integer) {
                int i = (int) v;
                if (i < 0) {
                    return NEG_INT;
                }

                return INT;
            } else if (v instanceof Boolean) {
                return BOOLEAN;
            } else if (v instanceof String) {
                return STRING;
            }
        }

        return NULL;
    }

    public static String indent(int indent) {
        if (indent < 0) {
            throw new IllegalArgumentException();
        } else if (indent < INDENTS.length) {
            return INDENTS[indent];
        }

        return IntStream.range(0, indent)
                .mapToObj(i -> INDENT)
                .collect(Collectors.joining());
    }

    public static void listObjectOpen(int indent, boolean root, String key, StringBuilder b, PdxRelation relation,
                                      boolean empty) {
        if (!root) {
            b.append(indent(indent));
            if (key != null) {
                b.append(PdxScriptParser.quoteIfNecessary(key));
                b.append(relation.getSign());
            }
            b.append(LIST_OBJECT_OPEN);
            if (!empty) {
                b.append(LINE_FEED);
            }
        }
    }

    public static void listObjectClose(int indent, boolean root, StringBuilder b, boolean empty) {
        if (!root) {
            if (!empty) {
                b.append(indent(indent));
            }
            b.append(LIST_OBJECT_CLOSE);
        } else if (!empty && b.charAt(b.length() - 1) == LINE_FEED) {
            b.deleteCharAt(b.length() - 1);
        }
    }

    public static PdxScriptObject extractObject(String key, IPdxScript s) {
        if (s instanceof PdxScriptObject) {
            return (PdxScriptObject) s;
        }

        throw new NoSuchElementException("expected object for key " + key + " but got " + s);
    }

    public static PdxScriptList extractImplicitList(String key, IPdxScript s) {
        if (s == null) {
            return PdxScriptList.EMPTY_IMPLICIT;
        } else if (s instanceof PdxScriptList) {
            PdxScriptList l = (PdxScriptList) s;
            if (l.getMode() == PdxScriptList.Mode.IMPLICIT) {
                return l;
            }
        }

        // TODO: is this smart? maybe throw instead...
        return PdxScriptList.builder().add(s).buildRaw(PdxScriptList.Mode.IMPLICIT, PdxRelation.EQUALS);
    }

    public static PdxScriptList extractList(String key, IPdxScript s) {
        if (s instanceof PdxScriptList) {
            PdxScriptList l = (PdxScriptList) s;
            if (l.getMode() != PdxScriptList.Mode.IMPLICIT) {
                return l;
            }
        }

        throw new NoSuchElementException("expected explicit list for key " + key + " but got " + s);
    }

    public static boolean extractBoolean(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof Boolean) {
                return (boolean) v;
            }
        }

        throw new NoSuchElementException("expected boolean for key " + key + " but got " + s);
    }

    public static int extractInt(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof Integer) {
                return (int) v;
            } else if (v instanceof String) {
                try {
                    return Integer.parseInt((String) v);
                } catch (NumberFormatException ignored) {
                }
            }
        }

        throw new NoSuchElementException("expected int for key " + key + " but got " + s);
    }

    public static int extractUnsignedInt(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof Integer) {
                return (int) v;
            } else if (v instanceof Long) {
                long l = (long) v;
                if (l >= 0 && l <= UNSIGNED_INT_MAX_LONG) {
                    return (int) l;
                }
            } else if (v instanceof String) {
                try {
                    return Integer.parseUnsignedInt((String) v);
                } catch (NumberFormatException ignored) {
                }
            }
        }

        throw new NoSuchElementException("expected unsigned int for key " + key + " but got " + s);
    }

    public static long extractLong(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof Long || v instanceof Integer) {
                return ((Number) v).longValue();
            } else if (v instanceof String) {
                try {
                    return Long.parseLong((String) v);
                } catch (NumberFormatException ignored) {
                }
            }
        }

        throw new NoSuchElementException("expected int for key " + key + " but got " + s);
    }

    public static double extractDouble(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof Double || v instanceof Long || v instanceof Integer) {
                return ((Number) v).doubleValue();
            } else if (v instanceof String) {
                try {
                    return Double.parseDouble((String) v);
                } catch (NumberFormatException ignored) {
                }
            }
        }

        throw new NoSuchElementException("expected double for key " + key + " but got " + s);
    }

    public static String extractString(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof String) {
                return (String) v;
            }
        }

        throw new NoSuchElementException("expected string for key " + key + " but got " + s);
    }

    public static String extractNullOrString(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v == null) {
                return null;
            } else if (v instanceof String) {
                return (String) v;
            }
        }

        throw new NoSuchElementException("expected string or null for key " + key + " but got " + s);
    }

    public static LocalDate extractDate(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof LocalDate) {
                return (LocalDate) v;
            }
        }

        throw new NoSuchElementException("expected date for key " + key + " but got " + s);
    }

    public static PdxColor extractColor(String key, IPdxScript s) {
        if (s instanceof PdxScriptValue) {
            Object v = ((PdxScriptValue) s).getValue();
            if (v instanceof PdxColor) {
                return (PdxColor) v;
            }
        }

        throw new NoSuchElementException("expected color for key " + key + " but got " + s);
    }

    public static <T extends IPdxScript, R> Function<T, R> nullOr(Function<? super T, ? extends R> fct) {
        return s -> s instanceof PdxScriptValue && ((PdxScriptValue) s).getValue() == null ? null : fct.apply(s);
    }
}
