package io.github.ititus.pdx.pdxscript;

import io.github.ititus.pdx.util.collection.CountingSet;
import io.github.ititus.pdx.util.collection.IteratorBuffer;
import io.github.ititus.pdx.util.io.IOUtil;
import io.github.ititus.pdx.util.mutable.MutableBoolean;
import org.eclipse.collections.api.list.ImmutableList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;

public final class PdxScriptParser implements PdxConstants {

    private static final CountingSet<String> unknownLiterals = new CountingSet<>();

    private PdxScriptParser() {
    }

    private static IPdxScript parse(IteratorBuffer<String> tokens) {
        String token = tokens.get();

        PdxRelation relation = PdxRelation.get(token);
        if (relation != null) {
            token = tokens.getNext();
        } else {
            relation = PdxRelation.EQUALS;
        }

        IPdxScript object;
        if (LIST_OBJECT_OPEN.equals(token)) {
            if (LIST_OBJECT_CLOSE.equals(tokens.get(1)) || PdxRelation.get(tokens.get(2)) != null) {
                //object or empty
                token = tokens.getNext();
                PdxScriptObject.Builder b = PdxScriptObject.builder();
                while (!LIST_OBJECT_CLOSE.equals(token)) {
                    String key = stripQuotes(token);
                    token = tokens.getNext();

                    if (PdxRelation.get(token) == null) {
                        throw new RuntimeException("Missing relation sign in object");
                    }

                    IPdxScript s = parse(tokens);
                    token = tokens.get();

                    if (COMMA.equals(token)) {
                        // comma-separated list as value
                        PdxScriptList.Builder lb = PdxScriptList.builder();
                        lb.add(s);
                        while (COMMA.equals(token)) {
                            tokens.next();
                            s = parse(tokens);
                            token = tokens.get();
                            lb.add(s);
                        }
                        s = lb.build(PdxScriptList.Mode.COMMA, s.getRelation());
                    }

                    b.add(key, s);
                }
                tokens.next();

                object = b.build(relation);
            } else {
                //list
                token = tokens.getNext();
                PdxScriptList.Builder b = PdxScriptList.builder();
                while (!LIST_OBJECT_CLOSE.equals(token)) {
                    if (PdxRelation.get(token) != null) {
                        throw new RuntimeException("No relation sign in list allowed");
                    }
                    IPdxScript s = parse(tokens);
                    token = tokens.get();

                    if (COMMA.equals(token)) {
                        // comma-separated list as value
                        PdxScriptList.Builder lb = PdxScriptList.builder();
                        lb.add(s);
                        while (COMMA.equals(token)) {
                            tokens.next();
                            s = parse(tokens);
                            token = tokens.get();
                            lb.add(s);
                        }
                        s = lb.build(PdxScriptList.Mode.COMMA, s.getRelation());
                    }

                    b.add(s);
                }
                tokens.next();

                object = b.build(relation);
            }
        } else {
            int l = token.length();
            if (l == 0) {
                throw new RuntimeException("Zero length token");
            }

            Object value;

            if (token.charAt(0) == QUOTE_CHAR || token.charAt(l - 1) == QUOTE_CHAR) {
                if (l >= 2 && token.charAt(0) == QUOTE_CHAR && token.charAt(l - 1) == QUOTE_CHAR) {
                    token = stripQuotes(token);
                    try {
                        value = LocalDate.parse(token, DTF);
                        if (value.equals(NULL_DATE)) {
                            value = NULL_DATE;
                        }
                    } catch (DateTimeParseException ignored) {
                        value = token; // fallback to string
                    }
                } else {
                    throw new RuntimeException("Quote not closed at token " + token);
                }
                tokens.next();
            } else if (NONE.equals(token)) {
                value = null;
                tokens.next();
            } else if (NO.equals(token)) {
                value = Boolean.FALSE;
                tokens.next();
            } else if (YES.equals(token)) {
                value = Boolean.TRUE;
                tokens.next();
            } else if (HSV.equals(token)) {
                tokens.next();
                IPdxScript s = parse(tokens);
                value = PdxColorWrapper.fromHSV(((PdxScriptList) s).getAsNumberArray());
            } else if (RGB.equals(token)) {
                tokens.next();
                IPdxScript s = parse(tokens);
                value = PdxColorWrapper.fromRGB(((PdxScriptList) s).getAsNumberArray());
            } else {
                try {
                    value = PdxColorWrapper.fromRGBHex(token);
                    tokens.next();
                } catch (IllegalArgumentException ignored1) {
                    String oldToken = token;
                    boolean percent = false;
                    if (token.charAt(l - 1) == PERCENT) {
                        percent = true;
                        token = token.substring(0, --l);
                    }
                    try {
                        value = Integer.valueOf(token);
                    } catch (NumberFormatException ignored2) {
                        try {
                            value = Long.valueOf(token);
                        } catch (NumberFormatException ignored3) {
                            try {
                                value = Double.valueOf(token);
                            } catch (NumberFormatException ignored4) {
                                token = oldToken;
                                if (token.charAt(0) == VARIABLE_PREFIX) {
                                    // TODO: Parse @ variables
                                } else {
                                    unknownLiterals.add(token.toLowerCase(Locale.ROOT)/*.intern()*/);
                                }
                                String tokenString = token;
                                // TODO: Fix tokenizer splitting raw tokens with math symbols in it
                            /*String operator = tokens.get(1);
                            PdxMathOperation operation = PdxMathOperation.get(operator);
                            if (operation != null) {
                                tokenString += operator;
                            }*/
                                value = tokenString; // fallback to string
                            }
                        }
                    }

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

                    token = tokens.getNext();

                    // TODO: Fix this (currently evaluated from right to left and ignores brackets)
                    if (value instanceof Number) {
                        String operator = token;
                        PdxMathOperation operation = PdxMathOperation.get(operator);
                        if (operation != null) {
                            tokens.next();
                            IPdxScript s = parse(tokens);
                            if (!(s instanceof PdxScriptValue)) {
                                throw new RuntimeException("Expected PdxScriptValue but got " + (s != null ?
                                        s.getClass().getTypeName() : NULL));
                            }
                            PdxScriptValue v = (PdxScriptValue) s;
                            Object o = v.getValue();
                            if (!(o instanceof Number)) {
                                throw new RuntimeException("Can only do math with numbers but got " + (o != null ?
                                        o.getClass().getTypeName() : NULL));
                            }
                            value = operation.apply((Number) value, (Number) o);
                            tokens.next();
                        }
                    }
                }
            }

            object = PdxScriptValue.of(relation, value);
        }

        return object;
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

    private static Iterator<String> tokenize(PrimitiveIterator.OfInt src) {
        return new Iterator<>() {

            final StringBuilder b = new StringBuilder();
            Character last = null;
            final MutableBoolean first = new MutableBoolean(true);
            final MutableBoolean openQuotes = new MutableBoolean(false);
            final MutableBoolean token =
                    new MutableBoolean(false);
            final MutableBoolean comment = new MutableBoolean(false);
            final MutableBoolean separator =
                    new MutableBoolean(false);
            final MutableBoolean relation = new MutableBoolean(false);
            final MutableBoolean mathOperator =
                    new MutableBoolean(false);
            final MutableBoolean done = new MutableBoolean(true);

            String next = null;
            boolean hasNextCalled = false;

            private void findNextToken() {
                if (first.get()) {
                    first.set(false);
                    next = LIST_OBJECT_OPEN;
                    return;
                }

                while (last != null || src.hasNext()) {
                    char c = last != null ? last : (char) src.nextInt();
                    last = null;

                    if (c == UTF_8_BOM) {
                        continue;
                    }

                    if (comment.get()) {
                        if (isNewLine(c)) {
                            comment.set(false);
                        }
                        continue;
                    }

                    if (openQuotes.get()) {
                        b.append(c);
                        if (c == QUOTE_CHAR && b.charAt(b.length() - 2) != ESCAPE_CHAR) {
                            openQuotes.set(false);
                            next = b.toString();
                            b.setLength(0);
                            return;
                        }

                        continue;
                    }

                    if (token.get()) {
                        if (c == COMMENT_CHAR || c == QUOTE_CHAR || isSeparator(c) || isRelation(c) || isMathOperator(c) || Character.isWhitespace(c)) {
                            token.set(false);
                            next = b.toString();
                            b.setLength(0);
                            last = c;
                            return;
                        } else {
                            b.append(c);
                            continue;
                        }
                    }

                    if (separator.get()) {
                        // Separators (curly brackets) can only be one char long
                        separator.set(false);
                        next = b.toString();
                        b.setLength(0);
                        last = c;
                        return;
                    }

                    if (relation.get()) {
                        if (!isRelation(c)) {
                            relation.set(false);
                            next = b.toString();
                            b.setLength(0);
                            last = c;
                            return;
                        } else {
                            b.append(c);
                            continue;
                        }
                    }

                    if (mathOperator.get()) {
                        if (!isMathOperator(c)) {
                            mathOperator.set(false);
                            next = b.toString();
                            b.setLength(0);
                            last = c;
                            return;
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

                if (openQuotes.get()) {
                    throw new RuntimeException("Quotes not closed at EOF");
                }

                if (token.get() || separator.get() || relation.get() || mathOperator.get()) {
                    token.set(false);
                    separator.set(false);
                    relation.set(false);
                    mathOperator.set(false);
                    next = b.toString();
                    b.setLength(0);
                    return;
                }

                if (done.get()) {
                    done.set(false);
                    next = LIST_OBJECT_CLOSE;
                    return;
                }

                next = null;
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                String n = next;
                hasNextCalled = false;
                next = null;
                return n;
            }

            @Override
            public boolean hasNext() {
                if (!hasNextCalled) {
                    findNextToken();
                    hasNextCalled = true;
                }
                return next != null;
            }
        };
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

    public static boolean isQuoteNecessary(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c) || c == '=' || c == '<' || c == '>' || c == '#' || c == '{' || c == '}' || c == ',' || c == '/' || c == '"') {
                return true;
            }
        }

        return false;
    }

    public static String quote(String s) {
        return (QUOTE_CHAR + s.replace(QUOTE, ESCAPE + QUOTE) + QUOTE_CHAR)/*.intern()*/;
    }

    public static String quoteIfNecessary(String s) {
        return isQuoteNecessary(s) ? quote(s) : s;
    }

    public static IPdxScript parse(Path scriptFile) {
        if (!Files.isRegularFile(scriptFile)) {
            throw new IllegalArgumentException();
        }

        try (Reader r = new BufferedReader(new InputStreamReader(Files.newInputStream(scriptFile),
                StandardCharsets.UTF_8))) {
            return parse(IOUtil.getCharacterIterator(r));
        } catch (IOException e) {
            throw new UncheckedIOException("Error while reading file: " + scriptFile, e);
        }
    }

    private static IPdxScript parse(PrimitiveIterator.OfInt charIterator) {
        Iterator<String> tokenIterator = tokenize(charIterator);

        IteratorBuffer<String> tokens = new IteratorBuffer<>(tokenIterator, 2, 0);
        IPdxScript s = parse(tokens);

        if (tokens.hasNext()) {
            throw new RuntimeException("Unconsumed tokens left at pos " + tokens.getPos());
        } else if ((!(s instanceof PdxScriptObject) && !(s instanceof PdxScriptList))) {
            throw new RuntimeException("Unexpected return value from parsing: " + (s != null ?
                    s.getClass().getTypeName() : NULL) + COMMA_CHAR + SPACE_CHAR + tokens.getPos());
        }

        return s;
    }

    public static ImmutableList<String> getUnknownLiterals() {
        return unknownLiterals.sortedList();
    }
}
