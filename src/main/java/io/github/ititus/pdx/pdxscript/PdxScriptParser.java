package io.github.ititus.pdx.pdxscript;

import io.github.ititus.pdx.util.ColorUtil;
import io.github.ititus.pdx.util.collection.CollectionUtil;
import io.github.ititus.pdx.util.collection.CountingSet;
import io.github.ititus.pdx.util.io.IOUtil;
import io.github.ititus.pdx.util.mutable.MutableBoolean;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class PdxScriptParser implements PdxConstants {

    private static final CountingSet<String> unknownLiterals = new CountingSet<>();

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
                    IPdxScript s = pair.o;

                    if (COMMA.equals(tokens.get(i))) {
                        // comma-separated list as value
                        PdxScriptList.Builder lb = PdxScriptList.builder();
                        lb.add(s);
                        while (COMMA.equals(tokens.get(i))) {
                            i++;
                            pair = parse(tokens, i);
                            i = pair.i;
                            lb.add(pair.o);
                        }
                        s = lb.build(PdxScriptList.Mode.COMMA, s.getRelation());
                    }

                    b.add(key, s);
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
                    IPdxScript s = pair.o;

                    if (COMMA.equals(tokens.get(i))) {
                        // comma-separated list as value
                        PdxScriptList.Builder lb = PdxScriptList.builder();
                        lb.add(s);
                        while (COMMA.equals(tokens.get(i))) {
                            i++;
                            pair = parse(tokens, i);
                            i = pair.i;
                            lb.add(pair.o);
                        }
                        s = lb.build(PdxScriptList.Mode.COMMA, s.getRelation());
                    }

                    b.add(s);
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

            if (token.charAt(0) == QUOTE_CHAR || token.charAt(l - 1) == QUOTE_CHAR) {
                if (l >= 2 && token.charAt(0) == QUOTE_CHAR && token.charAt(l - 1) == QUOTE_CHAR) {
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
                value = PdxColorWrapper.fromHSV(((PdxScriptList) colorPair.o).getAsNumberArray());
                i = colorPair.i;
            } else if (RGB.equals(token)) {
                ScriptIntPair colorPair = parse(tokens, ++i);
                value = PdxColorWrapper.fromRGB(((PdxScriptList) colorPair.o).getAsNumberArray());
                i = colorPair.i;
            } else if (ColorUtil.HEX_RGB_PATTERN.matcher(token).matches()) {
                value = PdxColorWrapper.fromRGBHex(token);
                i++;
            } else {
                String oldToken = token;
                boolean percent = false;
                Matcher m = PERCENT.matcher(token);
                if (m.matches()) {
                    percent = true;
                    token = m.group(1);
                }
                try {
                    value = Integer.valueOf(token);
                } catch (NumberFormatException e1) {
                    try {
                        value = Long.valueOf(token);
                    } catch (NumberFormatException e2) {
                        try {
                            value = Double.valueOf(token);
                        } catch (NumberFormatException e3) {
                            token = oldToken;
                            if (token.startsWith(VARIABLE_PREFIX)) {
                                // TODO: Parse @ variables
                            } else {
                                unknownLiterals.add(token.toLowerCase(Locale.ENGLISH).intern());
                            }
                            String tokenString = token;
                            // TODO: Fix tokenizer splitting raw tokens with math symbols in it
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

                if (percent && !token.equals(oldToken)) {
                    if (value instanceof Double) {
                        double d = (Double) value / 100D;
                        if ((int) d == d) {
                            value = (int) d;
                        } else if ((long) d == d) {
                            value = (long) d;
                        } else {
                            value = d;
                        }
                    } else if (value instanceof Integer) {
                        double d = (Integer) value / 100D;
                        if ((int) d == d) {
                            value = (int) d;
                        } else {
                            value = d;
                        }
                    } else if (value instanceof Long) {
                        double d = (Long) value / 100D;
                        if ((long) d == d) {
                            value = (long) d;
                        } else {
                            value = d;
                        }
                    } else {
                        throw new RuntimeException("Something went wrong while parsing a percent number");
                    }
                }

                // TODO: Fix this (currently evaluated from right to left and ignores brackets)
                if (value instanceof Number) {
                    String operator = tokens.get(i);
                    PdxMathOperation operation = PdxMathOperation.get(operator);
                    if (operation != null) {
                        i++;
                        ScriptIntPair pair = parse(tokens, i);
                        if (!(pair.o instanceof PdxScriptValue)) {
                            throw new RuntimeException("Expected PdxScriptValue but got " + (pair.o != null ? pair.o.getClass().getTypeName() : NULL));
                        }
                        PdxScriptValue v = (PdxScriptValue) pair.o;
                        Object o = v.getValue();
                        if (!(o instanceof Number)) {
                            throw new RuntimeException("Can only do math with numbers but got " + (o != null ? o.getClass().getTypeName() : NULL));
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
        if (s.charAt(beginIndex) == QUOTE_CHAR) {
            beginIndex++;
        }

        int endIndex = s.length();
        if (s.charAt(endIndex - 1) == QUOTE_CHAR) {
            endIndex--;
        }

        return s.substring(beginIndex, endIndex).replace(ESCAPE + QUOTE, QUOTE);
    }

    private static List<String> tokenize(IntStream src) {
        List<String> tokens = CollectionUtil.mutableListOf(LIST_OBJECT_OPEN);
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
                        if (c == QUOTE_CHAR && b.charAt(b.length() - 1) != ESCAPE_CHAR) {
                            openQuotes.set(false);
                            tokens.add(b.toString());
                            b.setLength(0);
                        }
                        return;
                    }

                    if (token.get()) {
                        if (c == COMMENT_CHAR || c == QUOTE_CHAR || isSeparator(c) || isRelation(c) || isMathOperator(c) || Character.isWhitespace(c)) {
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
                    } else if (c == QUOTE_CHAR) {
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
        return c == LINE_FEED || c == CARRIAGE_RETURN || c == LINE_SEPARATOR || c == PARAGRAPH_SEPARATOR;
    }

    private static boolean isSeparator(char c) {
        return c == LIST_OBJECT_OPEN_CHAR || c == LIST_OBJECT_CLOSE_CHAR || c == COMMA_CHAR;
    }

    private static boolean isRelation(char c) {
        return c == EQUALS_CHAR || c == LESS_THAN_CHAR || c == GREATER_THAN_CHAR;
    }

    private static boolean isMathOperator(char c) {
        return /*c == ADD_CHAR || c == SUBTRACT_CHAR || c == MULTIPLY_CHAR ||*/ c == DIVIDE_CHAR;
    }

    public static String quote(String s) {
        return (QUOTE_CHAR + s.replace(QUOTE, ESCAPE + QUOTE) + QUOTE_CHAR).intern();
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
            return IntStream.range(0, indent).mapToObj(i -> INDENT).collect(Collectors.joining()).intern();
        } else if (indent == 1) {
            return INDENT;
        }
        return EMPTY;
    }

    public static IPdxScript parse(File scriptFile) {
        try (Reader r = new InputStreamReader(new FileInputStream(scriptFile), StandardCharsets.UTF_8)) {
            return parse(IOUtil.getCharacterStream(r));
        } catch (Exception e) {
            throw new RuntimeException("Error while reading file: " + scriptFile, e);
        }
    }

    public static IPdxScript parse(IntStream stream) {
        List<String> tokens = tokenize(stream);
        ScriptIntPair pair = parse(tokens, 0);
        if ((!(pair.o instanceof PdxScriptObject) && !(pair.o instanceof PdxScriptList)) || pair.i != tokens.size()) {
            throw new RuntimeException("Unexpected return value from parsing: " + (pair.o != null ? pair.o.getClass().getTypeName() : NULL) + COMMA_CHAR + SPACE_CHAR + pair.i + SLASH_CHAR + tokens.size());
        }
        return pair.o;
    }

    public static List<String> getUnknownLiterals() {
        return Collections.unmodifiableList(unknownLiterals.sortedStream().collect(Collectors.toList()));
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
