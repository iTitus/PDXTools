package io.github.ititus.pdx.pdxscript;

import org.eclipse.collections.api.block.function.Function;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

public interface PdxConstants {

    char UTF_8_BOM = '\uFEFF';
    char LINE_FEED = '\n';
    char CARRIAGE_RETURN = '\r';
    char LINE_SEPARATOR = '\u2028';
    char PARAGRAPH_SEPARATOR = '\u2029';
    char SPACE_CHAR = ' ';
    char COLON_CHAR = ':';
    char ZERO_CHAR = '0';
    char SLASH_CHAR = '/';
    char DOT_CHAR = '.';
    String EMPTY = "";
    String INDENT = "    ";
    long UNSIGNED_INT_MAX_LONG = 0xFFFFFFFFL;

    char LIST_OBJECT_OPEN_CHAR = '{';
    char LIST_OBJECT_CLOSE_CHAR = '}';
    char COMMA_CHAR = ',';
    String LIST_OBJECT_OPEN = String.valueOf(LIST_OBJECT_OPEN_CHAR);
    String LIST_OBJECT_CLOSE = String.valueOf(LIST_OBJECT_CLOSE_CHAR);
    String COMMA = String.valueOf(COMMA_CHAR);

    char COMMENT_CHAR = '#';
    char QUOTE_CHAR = '"';
    char ESCAPE_CHAR = '\\';
    char VARIABLE_PREFIX = '@';
    String QUOTE = String.valueOf(QUOTE_CHAR);
    String ESCAPE = String.valueOf(ESCAPE_CHAR);
    char PERCENT = '%';

    char EQUALS_CHAR = '=';
    char LESS_THAN_CHAR = '<';
    char GREATER_THAN_CHAR = '>';
    String EQUALS = String.valueOf(EQUALS_CHAR);
    String LESS_THAN = String.valueOf(LESS_THAN_CHAR);
    String GREATER_THAN = String.valueOf(GREATER_THAN_CHAR);
    String LESS_THAN_OR_EQUALS = LESS_THAN_CHAR + EQUALS;
    String GREATER_THAN_OR_EQUALS = GREATER_THAN_CHAR + EQUALS;

    // Currently only one math operation (division: 4/30) in common/defines/00_defines.txt:852
    char ADD_CHAR = '+';
    char SUBTRACT_CHAR = '-';
    char MULTIPLY_CHAR = '*';
    char DIVIDE_CHAR = '/';
    String ADD = String.valueOf(ADD_CHAR);
    String SUBTRACT = String.valueOf(SUBTRACT_CHAR);
    String MULTIPLY = String.valueOf(MULTIPLY_CHAR);
    String DIVIDE = String.valueOf(DIVIDE_CHAR);

    String YES = "yes";
    String NO = "no";
    String NONE = "none";
    String HSV = "hsv";
    String RGB = "rgb";

    String OBJECT = "object";
    String LIST = "list";
    String IMPLICIT_LIST = "list:implicit";
    String STRING = "value:string";
    String BOOLEAN = "value:boolean";
    String U_INT = "value:u_int";
    String NEG_INT = "value:-int";
    String INT = "value:int";
    String LONG = "value:long";
    String DOUBLE = "value:double";
    String DATE = "value:date";
    String COLOR = "value:color";
    String NULL = "null";

    String DEFAULT = "l_default";
    String ENGLISH = "l_english";
    String BRAZ_POR = "l_braz_por";
    String GERMAN = "l_german";
    String FRENCH = "l_french";
    String SPANISH = "l_spanish";
    String POLISH = "l_polish";
    String RUSSIAN = "l_russian";
    String DEFAULT_LANGUAGE = ENGLISH;

    String KEY_LANGUAGE = "language";
    String KEY_INDENT = "indent";
    String KEY_KEY = "key";
    String KEY_VALUE = "value";

    LocalDate NULL_DATE = LocalDate.of(1, 1, 1);
    DateTimeFormatter DTF = DateTimeFormatter.ofPattern("uuuu.MM.dd", Locale.ROOT);

    Predicate<IPdxScript> TO_BOOLEAN = s -> {
        if (s instanceof PdxScriptValue) {
            Object o = ((PdxScriptValue) s).getValue();
            if (o instanceof Boolean) {
                return (boolean) o;
            }
        }
        throw new IllegalArgumentException();
    };
    Function<IPdxScript, Boolean> NULL_OR_BOOLEAN = s -> {
        if (s instanceof PdxScriptValue) {
            Object o = ((PdxScriptValue) s).getValue();
            if (o instanceof Boolean) {
                return (Boolean) o;
            }
        }
        return null;
    };
    ToIntFunction<IPdxScript> TO_INT = s -> {
        if (s instanceof PdxScriptValue) {
            Object o = ((PdxScriptValue) s).getValue();
            if (o instanceof Number) {
                return (int) o;
            }
        }
        throw new IllegalArgumentException();
    };
    Function<IPdxScript, Integer> TO_INTEGER = s -> {
        if (s instanceof PdxScriptValue) {
            Object o = ((PdxScriptValue) s).getValue();
            if (o instanceof Integer) {
                return (Integer) o;
            }
        }
        throw new IllegalArgumentException();
    };
    ToDoubleFunction<IPdxScript> TO_DOUBLE = s -> {
        if (s instanceof PdxScriptValue) {
            Object o = ((PdxScriptValue) s).getValue();
            if (o instanceof Number) {
                return (double) o;
            }
        }
        throw new IllegalArgumentException();
    };
    Function<IPdxScript, Double> NULL_OR_DOUBLE = s -> {
        if (s instanceof PdxScriptValue) {
            Object o = ((PdxScriptValue) s).getValue();
            if (o instanceof Double) {
                return (Double) o;
            }
        }
        return null;
    };
    Function<IPdxScript, String> TO_STRING = s -> {
        if (s instanceof PdxScriptValue) {
            Object o = ((PdxScriptValue) s).getValue();
            if (o instanceof String) {
                return (String) o;
            }
        }
        throw new IllegalArgumentException();
    };
    Function<IPdxScript, String> NULL_OR_STRING = s -> {
        if (s instanceof PdxScriptValue) {
            Object o = ((PdxScriptValue) s).getValue();
            if (o instanceof String) {
                return (String) o;
            }
        }
        return null;
    };

    static String getTypeString(IPdxScript s) {
        if (s instanceof PdxScriptObject) {
            return OBJECT;
        }
        if (s instanceof PdxScriptList) {
            return ((PdxScriptList) s).getMode() == PdxScriptList.Mode.IMPLICIT ? IMPLICIT_LIST : LIST;
        }
        if (s instanceof PdxScriptValue) {
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
                } else {
                    return LONG;
                }
            } else if (v instanceof Integer) {
                int i = (int) v;
                if (i < 0) {
                    return NEG_INT;
                } else {
                    return INT;
                }
            } else if (v instanceof Boolean) {
                return BOOLEAN;
            } else if (v instanceof String) {
                return STRING;
            }
        }
        return NULL;
    }
}
