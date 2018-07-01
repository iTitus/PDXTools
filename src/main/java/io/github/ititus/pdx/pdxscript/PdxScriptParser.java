package io.github.ititus.pdx.pdxscript;

import io.github.ititus.pdx.util.CollectionUtil;
import io.github.ititus.pdx.util.IOUtil;
import io.github.ititus.pdx.util.MutableBoolean;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class PdxScriptParser {

    public static final String SDF_PATTERN = "yyyy.MM.dd";
    public static final String YES = "yes";
    public static final String NO = "no";
    public static final String NONE = "none";
    public static final String HSV = "hsv";
    public static final String RGB = "rgb";
    public static final String EQUALS = "=";
    public static final String LESS_THAN = "<";
    public static final String GREATER_THAN = ">";
    public static final String LESS_THAN_OR_EQUALS = "<=";
    public static final String GREATER_THAN_OR_EQUALS = ">=";
    public static final String ADD = "+";
    public static final String SUBTRACT = "-";
    public static final String MULTIPLY = "*";
    public static final String DIVIDE = "/";
    private static final String LIST_OBJECT_OPEN = "{";
    private static final String LIST_OBJECT_CLOSE = "}";
    private static final String INDENT = "    ";
    private static final char UTF_8_BOM = '\uFEFF';
    private static final char QUOTE = '"';
    private static final char ESCAPE = '\\';
    private static final char COMMENT_CHAR = '#';
    private static final Pattern STRING_NEEDS_QUOTE_PATTERN = Pattern.compile("\\s|[=<>#{}"/*+"+-*"*/ + "/\"]");

    private static final Set<String> unknownLiterals = new HashSet<>();

    private PdxScriptParser() {
    }

    private static ScriptIntPair parse(List<String> tokens, int i) {
        String token = tokens.get(i);
        if (i == 0) {
            if (!LIST_OBJECT_OPEN.equals(token) || !LIST_OBJECT_CLOSE.equals(tokens.get(tokens.size() - 1))) {
                throw new RuntimeException("First or last token is not a curly bracket");
            }
        }

        PdxRelation relation = PdxRelation.get(token);
        if (relation != null) {
            token = tokens.get(++i);
        } else {
            relation = PdxRelation.EQUALS;
        }

        IPdxScript object;
        if (LIST_OBJECT_OPEN.equals(token)) {
            if (LIST_OBJECT_CLOSE.equals(tokens.get(i + 1)) || PdxRelation.get(tokens.get(i + 2)) != null) {
                //object or empty
                i++;
                PdxScriptObject.Builder b = PdxScriptObject.builder();
                while (!LIST_OBJECT_CLOSE.equals(tokens.get(i))) {
                    String key = stripQuotes(tokens.get(i));
                    i++;

                    if (PdxRelation.get(tokens.get(i)) == null) {
                        throw new RuntimeException("Missing relation sign in object");
                    }

                    ScriptIntPair pair = parse(tokens, i);
                    i = pair.i;

                    b.add(key, pair.o);
                }
                i++;

                object = b.build(relation);
            } else {
                //list
                i++;
                PdxScriptList.Builder b = PdxScriptList.builder();
                while (!LIST_OBJECT_CLOSE.equals(tokens.get(i))) {
                    if (PdxRelation.get(tokens.get(i)) != null) {
                        throw new RuntimeException("No relation sign in list allowed");
                    }
                    ScriptIntPair pair = parse(tokens, i);
                    i = pair.i;

                    b.add(pair.o);
                }
                i++;

                object = b.build(relation);
            }
        } else {
            int l = token.length();
            if (l == 0) {
                throw new RuntimeException("Zero length token");
            }

            Object value;
            SimpleDateFormat sdf = new SimpleDateFormat(SDF_PATTERN, Locale.ENGLISH);

            if (token.charAt(0) == QUOTE || token.charAt(l - 1) == QUOTE) {
                if (l >= 2 && token.charAt(0) == QUOTE && token.charAt(l - 1) == QUOTE) {
                    token = token.substring(1, token.length() - 1);
                    try {
                        value = sdf.parse(token);
                    } catch (ParseException e) {
                        value = token; // fallback to string
                    }
                } else {
                    throw new RuntimeException("Quote not closed at token " + token);
                }
                i++;
            } else if (NONE.equals(token)) {
                value = null;
                i++;
            } else if (NO.equals(token)) {
                value = Boolean.FALSE;
                i++;
            } else if (YES.equals(token)) {
                value = Boolean.TRUE;
                i++;
            } else if (HSV.equals(token)) {
                ScriptIntPair colorPair = parse(tokens, ++i);
                value = new PdxColorWrapper(PdxColorWrapper.Type.HSV, ((PdxScriptList) colorPair.o).getAsNumberArray());
                i = colorPair.i;
            } else if (RGB.equals(token)) {
                ScriptIntPair colorPair = parse(tokens, ++i);
                value = new PdxColorWrapper(PdxColorWrapper.Type.RGB, ((PdxScriptList) colorPair.o).getAsNumberArray());
                i = colorPair.i;
            } else {
                try {
                    value = Integer.valueOf(token);
                } catch (NumberFormatException e1) {
                    try {
                        value = Long.valueOf(token);
                    } catch (NumberFormatException e2) {
                        try {
                            value = Double.valueOf(token);
                        } catch (NumberFormatException e3) {
                            if (i > 0 && PdxRelation.get(tokens.get(i - 1)) != null) {
                                unknownLiterals.add(token);
                            }
                            String tokenString = token;
                            // TODO: Fix tokenizer splitting raw tokens with '-' in it
                            /*String operator = tokens.get(i + 1);
                            PdxMathOperation operation = PdxMathOperation.get(operator);
                            if (operation != null) {
                                tokenString += operator;
                            }*/
                            value = tokenString; // fallback to string
                        }
                    }
                }
                i++;
                // TODO: Fix this (currently evaluated from right to left and ignores brackets)
                if (value instanceof Number) {
                    String operator = tokens.get(i);
                    PdxMathOperation operation = PdxMathOperation.get(operator);
                    if (operation != null) {
                        i++;
                        ScriptIntPair pair = parse(tokens, i);
                        if (!(pair.o instanceof PdxScriptValue)) {
                            throw new RuntimeException("Expected PdxScriptValue but got " + (pair.o != null ? pair.o.getClass().getTypeName() : "null"));
                        }
                        PdxScriptValue v = (PdxScriptValue) pair.o;
                        Object o = v.getValue();
                        if (!(o instanceof Number)) {
                            throw new RuntimeException("Can only do math with numbers but got " + (o != null ? o.getClass().getTypeName() : "null"));
                        }
                        value = operation.apply((Number) value, (Number) o);
                        i = pair.i;
                    }
                }
            }

            object = new PdxScriptValue(relation, value);
        }

        return new ScriptIntPair(object, i);
    }

    private static String stripQuotes(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        int beginIndex = 0;
        if (s.charAt(beginIndex) == QUOTE) {
            beginIndex++;
        }

        int endIndex = s.length();
        if (s.charAt(endIndex - 1) == QUOTE) {
            endIndex--;
        }

        return s.substring(beginIndex, endIndex).replaceAll("\\\"", "\"");
    }

    private static List<String> tokenize(IntStream src) {
        List<String> tokens = CollectionUtil.listOf(LIST_OBJECT_OPEN);
        StringBuilder b = new StringBuilder();
        MutableBoolean openQuotes = new MutableBoolean(false), token = new MutableBoolean(false), comment = new MutableBoolean(false), separator = new MutableBoolean(false), relation = new MutableBoolean(false), mathOperator = new MutableBoolean(false);
        src.forEachOrdered(i -> {
                    char c = (char) i;

                    if (c == UTF_8_BOM) {
                        return;
                    }

                    if (comment.get()) {
                        if (isNewLine(c)) {
                            comment.set(false);
                        }
                        return;
                    }

                    if (openQuotes.get()) {
                        if (isNewLine(c)) {
                            throw new RuntimeException("No multi-line strings");
                        }
                        b.append(c);
                        if (c == QUOTE && b.charAt(b.length() - 1) != ESCAPE) {
                            openQuotes.set(false);
                            tokens.add(b.toString());
                            b.setLength(0);
                        }
                        return;
                    }

                    if (token.get()) {
                        if (c == COMMENT_CHAR || c == QUOTE || isSeparator(c) || isRelation(c) || isMathOperator(c) || Character.isWhitespace(c)) {
                            token.set(false);
                            tokens.add(b.toString());
                            b.setLength(0);
                        } else {
                            b.append(c);
                            return;
                        }
                    }

                    if (separator.get()) {
                        // Separators (curly brackets) can only be one char long
                        separator.set(false);
                        tokens.add(b.toString());
                        b.setLength(0);
                    }

                    if (relation.get()) {
                        if (!isRelation(c)) {
                            relation.set(false);
                            tokens.add(b.toString());
                            b.setLength(0);
                        } else {
                            b.append(c);
                            return;
                        }
                    }

                    if (mathOperator.get()) {
                        if (!isMathOperator(c)) {
                            mathOperator.set(false);
                            tokens.add(b.toString());
                            b.setLength(0);
                        } else {
                            throw new RuntimeException("Math operators can only be one char long");
                        }
                    }

                    if (b.length() > 0) {
                        throw new RuntimeException();
                    }

                    if (c == COMMENT_CHAR) {
                        comment.set(true);
                    } else if (c == QUOTE) {
                        openQuotes.set(true);
                        b.append(c);
                    } else if (isSeparator(c)) {
                        separator.set(true);
                        b.append(c);
                    } else if (isRelation(c)) {
                        relation.set(true);
                        b.append(c);
                    } else if (isMathOperator(c)) {
                        mathOperator.set(true);
                        b.append(c);
                    } else if (!Character.isWhitespace(c)) {
                        token.set(true);
                        b.append(c);
                    }
                }
        );
        if (openQuotes.get()) {
            throw new RuntimeException("Quotes not closed at EOF");
        }
        if (token.get() || separator.get() || relation.get() || mathOperator.get()) {
            tokens.add(b.toString());
            b.setLength(0);
        }
        tokens.add(LIST_OBJECT_CLOSE);
        return tokens;
    }

    private static boolean isNewLine(char c) {
        return c == 10 || c == 13 || c == 8232 || c == 8233;
    }

    private static boolean isSeparator(char c) {
        return c == '{' || c == '}';
    }

    private static boolean isRelation(char c) {
        return c == '=' || c == '<' || c == '>';
    }

    private static boolean isMathOperator(char c) {
        return /*c == '+' || c == '-' || c == '*' ||*/ c == '/';
    }

    public static String quote(String s) {
        return '"' + s.replaceAll("\"", "\\\"") + '"';
    }

    public static String quoteIfNecessary(String s) {
        if (STRING_NEEDS_QUOTE_PATTERN.matcher(s).find()) {
            return quote(s);
        }
        return s;
    }

    public static String indent(int indent) {
        if (indent < 0) {
            throw new IllegalArgumentException();
        } else if (indent > 1) {
            return IntStream.range(0, indent).mapToObj(i -> INDENT).collect(Collectors.joining());
        } else if (indent == 1) {
            return INDENT;
        }
        return "";
    }

    public static IPdxScript parse(File scriptFile) {
        try (Reader r = new InputStreamReader(new FileInputStream(scriptFile), StandardCharsets.UTF_8)) {
            return parse(IOUtil.getCharacterStream(r));
        } catch (Exception e) {
            throw new RuntimeException("Error while reading file: " + scriptFile, e);
        }
    }

    public static IPdxScript parse(IntStream stream) {
        List<String> tokens = new ArrayList<>(tokenize(stream));
        ScriptIntPair pair = parse(tokens, 0);
        if ((!(pair.o instanceof PdxScriptObject) && !(pair.o instanceof PdxScriptList)) || pair.i != tokens.size()) {
            throw new RuntimeException("Unexpected return value from parsing: " + (pair.o != null ? pair.o.getClass().getTypeName() : "null") + ", " + pair.i + "/" + tokens.size());
        }
        return pair.o;
    }

    public static void printUnknownLiterals() {
        System.out.println("-------------------------");
        System.out.println("Unknown literals:");
        unknownLiterals.forEach(System.out::println);
        System.out.println("-------------------------");
    }

    private static class ScriptIntPair {

        private final IPdxScript o;
        private final int i;

        private ScriptIntPair(IPdxScript o, int i) {
            this.o = o;
            this.i = i;
        }
    }
}
