package io.github.ititus.pdx.pdxscript;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class PdxConstants {

    public static final char UTF_8_BOM = '\uFEFF';
    public static final char LINE_FEED = '\n';
    public static final char CARRIAGE_RETURN = '\r';
    public static final char LINE_SEPARATOR = '\u2028';
    public static final char PARAGRAPH_SEPARATOR = '\u2029';
    public static final char SPACE_CHAR = ' ';
    public static final char COLON_CHAR = ':';
    public static final char ZERO_CHAR = '0';
    public static final char SLASH_CHAR = '/';
    public static final char DOT_CHAR = '.';
    public static final String EMPTY = "";
    public static final String INDENT = "    ";
    public static final String NUMBER_MARKER = "<num>";
    public static final long UNSIGNED_INT_MAX_LONG = 0xFFFFFFFFL;
    public static final char LIST_OBJECT_OPEN_CHAR = '{';
    public static final char LIST_OBJECT_CLOSE_CHAR = '}';
    public static final char COMMA_CHAR = ',';
    public static final String LIST_OBJECT_OPEN = String.valueOf(LIST_OBJECT_OPEN_CHAR);
    public static final String LIST_OBJECT_CLOSE = String.valueOf(LIST_OBJECT_CLOSE_CHAR);
    public static final String COMMA = String.valueOf(COMMA_CHAR);
    public static final char COMMENT_CHAR = '#';
    public static final char QUOTE_CHAR = '"';
    public static final char ESCAPE_CHAR = '\\';
    public static final char VARIABLE_PREFIX = '@';
    public static final String QUOTE = String.valueOf(QUOTE_CHAR);
    public static final String ESCAPE = String.valueOf(ESCAPE_CHAR);
    public static final char PERCENT = '%';
    public static final char EQUALS_CHAR = '=';
    public static final char LESS_THAN_CHAR = '<';
    public static final char GREATER_THAN_CHAR = '>';
    public static final String EQUALS = String.valueOf(EQUALS_CHAR);
    public static final String LESS_THAN = String.valueOf(LESS_THAN_CHAR);
    public static final String GREATER_THAN = String.valueOf(GREATER_THAN_CHAR);
    public static final String LESS_THAN_OR_EQUALS = LESS_THAN_CHAR + EQUALS;
    public static final String GREATER_THAN_OR_EQUALS = GREATER_THAN_CHAR + EQUALS;
    /**
     * Currently only one math operation (division: 4/30) in common/defines/00_defines.txt:852
     */
    public static final char ADD_CHAR = '+';
    public static final char SUBTRACT_CHAR = '-';
    public static final char MULTIPLY_CHAR = '*';
    public static final char DIVIDE_CHAR = '/';
    public static final String ADD = String.valueOf(ADD_CHAR);
    public static final String SUBTRACT = String.valueOf(SUBTRACT_CHAR);
    public static final String MULTIPLY = String.valueOf(MULTIPLY_CHAR);
    public static final String DIVIDE = String.valueOf(DIVIDE_CHAR);
    public static final String YES = "yes";
    public static final String NO = "no";
    public static final String NONE = "none";
    public static final String HSV = "hsv";
    public static final String RGB = "rgb";
    public static final String OBJECT = "object";
    public static final String LIST = "list";
    public static final String IMPLICIT_LIST = "list:implicit";
    public static final String STRING = "value:string";
    public static final String BOOLEAN = "value:boolean";
    public static final String U_INT = "value:u_int";
    public static final String NEG_INT = "value:-int";
    public static final String INT = "value:int";
    public static final String LONG = "value:long";
    public static final String DOUBLE = "value:double";
    public static final String DATE = "value:date";
    public static final String COLOR = "value:color";
    public static final String NULL = "null";
    public static final String DEFAULT = "l_default";
    public static final String ENGLISH = "l_english";
    public static final String BRAZ_POR = "l_braz_por";
    public static final String GERMAN = "l_german";
    public static final String FRENCH = "l_french";
    public static final String SPANISH = "l_spanish";
    public static final String POLISH = "l_polish";
    public static final String RUSSIAN = "l_russian";
    public static final String DEFAULT_LANGUAGE = ENGLISH;
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_INDENT = "indent";
    public static final String KEY_KEY = "key";
    public static final String KEY_VALUE = "value";
    public static final LocalDate NULL_DATE = LocalDate.of(1, 1, 1);
    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("u.MM.dd", Locale.ROOT);

    private PdxConstants() {
    }
}
