package io.github.ititus.pdx.pdxscript;

import io.github.ititus.function.BiIntObjFunction;

import java.util.function.BiFunction;
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
        if (s != null) {
            return s.getTypeString();
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

    public static <T extends IPdxScript, R> Function<T, R> nullOr(Function<? super T, ? extends R> fct) {
        return s -> s instanceof PdxScriptValue && ((PdxScriptValue) s).getValue() == null ? null : fct.apply(s);
    }

    public static <T extends String, U extends IPdxScript, R> BiFunction<T, U, R> nullOr(BiFunction<? super T, ? super U, ? extends R> fct) {
        return (k, v) -> v instanceof PdxScriptValue && ((PdxScriptValue) v).getValue() == null ? null : fct.apply(k, v);
    }

    public static <T extends IPdxScript, R> BiIntObjFunction<T, R> nullOr(BiIntObjFunction<? super T, ? extends R> fct) {
        return (k, v) -> v instanceof PdxScriptValue && ((PdxScriptValue) v).getValue() == null ? null : fct.apply(k, v);
    }
}
