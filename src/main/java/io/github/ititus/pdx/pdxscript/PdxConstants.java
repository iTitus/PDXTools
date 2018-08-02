package io.github.ititus.pdx.pdxscript;

import java.util.Date;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.regex.Pattern;

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
    String SDF_PATTERN = "yyyy.MM.dd";
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
    String VARIABLE_PREFIX = "@";
    String QUOTE = String.valueOf(QUOTE_CHAR);
    String ESCAPE = String.valueOf(ESCAPE_CHAR);

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
    String UNSIGNED_INT = "value:u_int";
    String INT = "value:int";
    String LONG = "value:long";
    String DOUBLE = "value:double";
    String DATE = "value:date";
    String NULL = "null";

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

    Date START_DATE = new Date(2200 - 1900, 0, 1);

    Pattern EMPTY_PATTERN = Pattern.compile(EMPTY);
    Pattern DIGITS_PATTERN = Pattern.compile("\\d+");
    Pattern STRING_NEEDS_QUOTE_PATTERN = Pattern.compile("\\s|[=<>#{},"/*+"+-*"*/ + "/\"]");
    Pattern PERCENT = Pattern.compile("(\\S+)\\s*%");
    Pattern HEX_RGB_PATTERN = Pattern.compile("(0x|#)((([0-9A-Fa-f]{2})?)[0-9A-Fa-f]{6})");
    Pattern LANGUAGE_PATTERN = Pattern.compile("^(?<" + KEY_LANGUAGE + ">l_(\\w+)):$");
    Pattern TRANSLATION_PATTERN = Pattern.compile("^(?<" + KEY_INDENT + "> )(?<" + KEY_KEY + ">[\\w.]+):0 \"(?<" + KEY_VALUE + ">.*)\"$");

    Predicate<IPdxScript> TO_BOOLEAN = s -> {
        if (s instanceof PdxScriptValue) {
            Object o = ((PdxScriptValue) s).getValue();
            if (o instanceof Boolean) {
                return (boolean) o;
            }
        }
        return false;
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
        return 0;
    };
    Function<IPdxScript, Integer> NULL_OR_INTEGER = s -> {
        if (s instanceof PdxScriptValue) {
            Object o = ((PdxScriptValue) s).getValue();
            if (o instanceof Integer) {
                return (Integer) o;
            }
        }
        return 0;
    };
    ToDoubleFunction<IPdxScript> TO_DOUBLE = s -> {
        if (s instanceof PdxScriptValue) {
            Object o = ((PdxScriptValue) s).getValue();
            if (o instanceof Number) {
                return (double) o;
            }
        }
        return 0;
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
    Function<IPdxScript, String> NULL_OR_STRING = s -> {
        if (s instanceof PdxScriptValue) {
            Object o = ((PdxScriptValue) s).getValue();
            if (o instanceof String) {
                return (String) o;
            }
        }
        return null;
    };
}
