package io.github.ititus.pdx.pdxscript;

import io.github.ititus.pdx.util.collection.CountingSet;
import io.github.ititus.pdx.util.io.IOUtil;
import io.github.ititus.pdx.util.mutable.MutableBoolean;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;

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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class PdxScriptParser implements PdxConstants {

    private static final CountingSet<String> unknownLiterals = new CountingSet<>();

    private PdxScriptParser() {
    }

    private static ObjectIntPair<IPdxScript> parse(ImmutableList<String> tokens, int i) {
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

                    ObjectIntPair<IPdxScript> pair = parse(tokens, i);
                    i = pair.getTwo();
                    IPdxScript s = pair.getOne();

                    if (COMMA.equals(tokens.get(i))) {
                        // comma-separated list as value
                        PdxScriptList.Builder lb = PdxScriptList.builder();
                        lb.add(s);
                        while (COMMA.equals(tokens.get(i))) {
                            i++;
                            pair = parse(tokens, i);
                            i = pair.getTwo();
                            lb.add(pair.getOne());
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
                    ObjectIntPair<IPdxScript> pair = parse(tokens, i);
                    i = pair.getTwo();
                    IPdxScript s = pair.getOne();

                    if (COMMA.equals(tokens.get(i))) {
                        // comma-separated list as value
                        PdxScriptList.Builder lb = PdxScriptList.builder();
                        lb.add(s);
                        while (COMMA.equals(tokens.get(i))) {
                            i++;
                            pair = parse(tokens, i);
                            i = pair.getTwo();
                            lb.add(pair.getOne());
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

            if (token.charAt(0) == QUOTE_CHAR || token.charAt(l - 1) == QUOTE_CHAR) {
                if (l >= 2 && token.charAt(0) == QUOTE_CHAR && token.charAt(l - 1) == QUOTE_CHAR) {
                    token = stripQuotes(token);
                    try {
                        value = LocalDate.parse(token, DTF);
                    } catch (DateTimeParseException ignored) {
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
                ObjectIntPair<IPdxScript> colorPair = parse(tokens, ++i);
                value = PdxColorWrapper.fromHSV(((PdxScriptList) colorPair.getOne()).getAsNumberArray());
                i = colorPair.getTwo();
            } else if (RGB.equals(token)) {
                ObjectIntPair<IPdxScript> colorPair = parse(tokens, ++i);
                value = PdxColorWrapper.fromRGB(((PdxScriptList) colorPair.getOne()).getAsNumberArray());
                i = colorPair.getTwo();
            } else {
                try {
                    value = PdxColorWrapper.fromRGBHex(token);
                    i++;
                } catch (IllegalArgumentException ignored1) {
                    String oldToken = token;
                    boolean percent = false;
                    if (token.endsWith(PERCENT)) {
                        percent = true;
                        token = token.substring(0, l - 1);
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
                                if (token.startsWith(VARIABLE_PREFIX)) {
                                    // TODO: Parse @ variables
                                } else {
                                    unknownLiterals.add(token.toLowerCase(Locale.ROOT).intern());
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
                            ObjectIntPair<IPdxScript> pair = parse(tokens, i);
                            if (!(pair.getOne() instanceof PdxScriptValue)) {
                                throw new RuntimeException("Expected PdxScriptValue but got " + (pair.getOne() != null ? pair.getOne().getClass().getTypeName() : NULL));
                            }
                            PdxScriptValue v = (PdxScriptValue) pair.getOne();
                            Object o = v.getValue();
                            if (!(o instanceof Number)) {
                                throw new RuntimeException("Can only do math with numbers but got " + (o != null ? o.getClass().getTypeName() : NULL));
                            }
                            value = operation.apply((Number) value, (Number) o);
                            i = pair.getTwo();
                        }
                    }
                }
            }

            object = PdxScriptValue.of(relation, value);
        }

        return PrimitiveTuples.pair(object, i);
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

            StringBuilder b = new StringBuilder();
            Character last = null;
            MutableBoolean first = new MutableBoolean(true), openQuotes = new MutableBoolean(false), token = new MutableBoolean(false), comment = new MutableBoolean(false), separator = new MutableBoolean(false), relation = new MutableBoolean(false), mathOperator = new MutableBoolean(false), done = new MutableBoolean(true);

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
        return (QUOTE_CHAR + s.replace(QUOTE, ESCAPE + QUOTE) + QUOTE_CHAR).intern();
    }

    public static String quoteIfNecessary(String s) {
        return isQuoteNecessary(s) ? quote(s) : s;
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

    public static IPdxScript parse(Path scriptFile) {
        if (!Files.isRegularFile(scriptFile)) {
            throw new IllegalArgumentException();
        }

        try (Reader r = new BufferedReader(new InputStreamReader(Files.newInputStream(scriptFile), StandardCharsets.UTF_8))) {
            return parse(IOUtil.getCharacterIterator(r));
        } catch (IOException e) {
            throw new UncheckedIOException("Error while reading file: " + scriptFile, e);
        }
    }

    private static IPdxScript parse(PrimitiveIterator.OfInt iterator) {
        Iterator<String> tokenIterator = tokenize(iterator);

        // TODO: Implement some kind of ring buffer here
        MutableList<String> l = Lists.mutable.empty();
        while (tokenIterator.hasNext()) {
            l.add(tokenIterator.next());
        }
        ImmutableList<String> tokens = l.toImmutable();

        ObjectIntPair<IPdxScript> pair = parse(tokens, 0);
        if ((!(pair.getOne() instanceof PdxScriptObject) && !(pair.getOne() instanceof PdxScriptList)) || pair.getTwo() != tokens.size()) {
            throw new RuntimeException("Unexpected return value from parsing: " + (pair.getOne() != null ? pair.getOne().getClass().getTypeName() : NULL) + COMMA_CHAR + SPACE_CHAR + pair.getTwo() + SLASH_CHAR + tokens.size());
        }
        return pair.getOne();
    }

    public static ImmutableList<String> getUnknownLiterals() {
        return unknownLiterals.sortedList();
    }
}
