package io.github.ititus.pdx.pdxscript;

import io.github.ititus.data.IteratorBuffer;
import io.github.ititus.data.mutable.MutableBoolean;
import io.github.ititus.pdx.util.IOUtil;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.factory.SortedBags;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Stream;

import static io.github.ititus.pdx.pdxscript.PdxConstants.*;

public final class PdxScriptParser {

    private static final boolean COUNT_UNKNOWN_LITERALS = false;
    private static final MutableSortedBag<String> unknownLiterals = SortedBags.mutable.empty();

    private PdxScriptParser() {
    }

    private static IPdxScript parse(IteratorBuffer<String> tokens, MutableMap<String, IPdxScript> variables) {
        String token = tokens.get();

        PdxRelation relation = PdxRelation.get(token);
        if (relation != null) {
            token = tokens.getNext();
        } else {
            relation = PdxRelation.EQUALS;
        }

        IPdxScript object;
        if (LIST_OBJECT_OPEN.equals(token)) { // list or object
            token = tokens.getNext();
            if (LIST_OBJECT_CLOSE.equals(token)) { // empty
                tokens.next();
                object = PdxScriptObject.builder().build(relation);
            } else if (PdxRelation.get(tokens.get(1)) != null) { // object
                PdxScriptObject.Builder b = PdxScriptObject.builder();
                while (!LIST_OBJECT_CLOSE.equals(token)) {
                    String key = stripQuotes(token);
                    token = tokens.getNext();

                    if (!LIST_OBJECT_OPEN.equals(token) && PdxRelation.get(token) == null) {
                        throw new RuntimeException("Missing relation sign or { in object");
                    }

                    IPdxScript parsedValue = parse(tokens, variables);
                    token = tokens.get();

                    if (COMMA.equals(token)) {
                        // comma-separated list as value
                        PdxScriptList.Builder lb = PdxScriptList.builder();
                        lb.add(parsedValue);
                        while (COMMA.equals(token)) {
                            tokens.next();
                            parsedValue = parse(tokens, variables);
                            token = tokens.get();
                            lb.add(parsedValue);
                        }
                        parsedValue = lb.build(PdxScriptList.Mode.COMMA, parsedValue.getRelation());
                    }

                    if (key.charAt(0) == VARIABLE_PREFIX) {
                        variables.put(key, parsedValue);
                    } else {
                        b.add(key, parsedValue);
                    }
                }
                tokens.next();

                object = b.build(relation);
            } else { // list
                PdxScriptList.Builder b = PdxScriptList.builder();
                while (!LIST_OBJECT_CLOSE.equals(token)) {
                    if (PdxRelation.get(token) != null) {
                        throw new RuntimeException("No relation sign in list allowed");
                    }

                    IPdxScript s = parse(tokens, variables);
                    token = tokens.get();

                    if (COMMA.equals(token)) {
                        // comma-separated list as value
                        PdxScriptList.Builder lb = PdxScriptList.builder();
                        lb.add(s);
                        while (COMMA.equals(token)) {
                            tokens.next();
                            s = parse(tokens, variables);
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
        } else { // value
            if (token.charAt(0) == VARIABLE_PREFIX) {
                IPdxScript resolved = variables.get(token);
                if (resolved == null) {
                    throw new RuntimeException("cannot resolve variable " + token);
                }

                tokens.next();
                return resolved;
            }

            Object value;
            if (token.charAt(0) == QUOTE_CHAR) {
                token = stripQuotes(token);
                int l = token.length();
                if (l > 0 && isDigit(token.charAt(0))) {
                    try {
                        value = LocalDate.parse(token, DTF);
                        if (value.equals(NULL_DATE)) {
                            value = NULL_DATE;
                        }
                    } catch (DateTimeParseException ignored) {
                        value = token; // fallback to string
                    }
                } else {
                    value = token;
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
                IPdxScript s = parse(tokens, variables);
                value = PdxColor.fromHSV(s.expectList().getAsNumberArray());
            } else if (RGB.equals(token)) {
                tokens.next();
                IPdxScript s = parse(tokens, variables);
                value = PdxColor.fromRGB(s.expectList().getAsNumberArray());
            } else {
                try {
                    value = PdxColor.fromRGBHex(token);
                    tokens.next();
                } catch (IllegalArgumentException ignored1) {
                    String oldToken = token;
                    boolean percent = false;
                    int last = token.length() - 1;
                    if (token.charAt(last) == PERCENT) {
                        percent = true;
                        token = token.substring(0, last);
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
                                if (COUNT_UNKNOWN_LITERALS) {
                                    unknownLiterals.add(token.toLowerCase(Locale.ROOT));
                                }

                                // TODO: Fix tokenizer splitting raw tokens with math symbols in it
                                /*String tokenString = token;
                                tring operator = tokens.get(1);
                                PdxMathOperation operation = PdxMathOperation.get(operator);
                                if (operation != null) {
                                    tokenString += operator;
                                }
                                value = tokenString; // fallback to string
                                */
                                value = token; // fallback to string
                            }
                        }
                    }

                    if (percent && !token.equals(oldToken)) {
                        if (value instanceof Double) {
                            double d = (double) value / 100D;
                            if ((int) d == d) {
                                value = (int) d;
                            } else if ((long) d == d) {
                                value = (long) d;
                            } else {
                                value = d;
                            }
                        } else if (value instanceof Integer) {
                            double d = (int) value / 100D;
                            if ((int) d == d) {
                                value = (int) d;
                            } else {
                                value = d;
                            }
                        } else { // Long
                            double d = (long) value / 100D;
                            if ((long) d == d) {
                                value = (long) d;
                            } else {
                                value = d;
                            }
                        }
                    }

                    token = tokens.getNext();

                    // TODO: Fix this (currently evaluated from right to left and ignores brackets)
                    if (value instanceof Number) {
                        PdxMathOperation op = PdxMathOperation.get(token);
                        if (op != null) {
                            tokens.next();
                            IPdxScript s = parse(tokens, variables);
                            if (!(s instanceof PdxScriptValue)) {
                                throw new RuntimeException("Expected PdxScriptValue but got " + (s != null ? s.getClass().getTypeName() : NULL));
                            }
                            PdxScriptValue v = (PdxScriptValue) s;
                            Object o = v.getValue();
                            if (!(o instanceof Number)) {
                                throw new RuntimeException("Can only do math with numbers but got " + (o != null ? o.getClass().getTypeName() : NULL));
                            }
                            value = op.apply((Number) value, (Number) o);
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
        int l = s.length() - 1;
        if (l <= 0) {
            return s;
        }

        boolean startsWithQuote = s.charAt(0) == QUOTE_CHAR;
        boolean endsWithQuote = s.charAt(l) == QUOTE_CHAR;

        if (startsWithQuote ^ endsWithQuote) {
            throw new IllegalArgumentException("given string has mismatched quotes: " + s);
        } else if (startsWithQuote) {
            return s.substring(1, l).replace(ESCAPED_QUOTE, QUOTE);
        }

        return s;
    }

    private static Iterator<String> tokenize(PrimitiveIterator.OfInt src) {
        return new Iterator<>() {

            final StringBuilder b = new StringBuilder();
            final MutableBoolean first = MutableBoolean.ofTrue();
            final MutableBoolean openQuotes = MutableBoolean.ofFalse();
            final MutableBoolean token = MutableBoolean.ofFalse();
            final MutableBoolean comment = MutableBoolean.ofFalse();
            final MutableBoolean separator = MutableBoolean.ofFalse();
            final MutableBoolean relation = MutableBoolean.ofFalse();
            final MutableBoolean mathOperator = MutableBoolean.ofFalse();
            final MutableBoolean done = MutableBoolean.ofTrue();
            Character last = null;
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

    public static boolean isDigit(int codepoint) {
        return '0' <= codepoint && codepoint <= '9';
    }

    public static boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    private static boolean isNewLine(char c) {
        return c == LINE_FEED || c == CARRIAGE_RETURN || c == LINE_SEPARATOR || c == PARAGRAPH_SEPARATOR;
    }

    private static boolean isSeparator(char c) {
        return c == LIST_OBJECT_OPEN_CHAR || c == LIST_OBJECT_CLOSE_CHAR || c == COMMA_CHAR;
    }

    private static boolean isRelation(char c) {
        return c == EQUALS_CHAR || c == NOT_CHAR || c == LESS_THAN_CHAR || c == GREATER_THAN_CHAR;
    }

    private static boolean isMathOperator(char c) {
        return /*c == ADD_CHAR || c == SUBTRACT_CHAR || c == MULTIPLY_CHAR ||*/ c == DIVIDE_CHAR;
    }

    public static boolean isQuoteNecessary(String s) {
        int l = s.length();
        for (int i = 0; i < l; i++) {
            char c = s.charAt(i);
            if (Character.isWhitespace(c) || c == '=' || c == '<' || c == '>' || c == '#' || c == '{' || c == '}' || c == ',' || c == '/' || c == '"') {
                return true;
            }
        }

        return false;
    }

    public static String quote(String s) {
        return QUOTE_CHAR + s.replace(QUOTE, ESCAPED_QUOTE) + QUOTE_CHAR;
    }

    public static String quoteUnsafe(String s) {
        return QUOTE_CHAR + s + QUOTE_CHAR;
    }

    public static String quoteIfNecessary(String s) {
        return isQuoteNecessary(s) ? quote(s) : s;
    }

    public static IPdxScript parse(Path... scriptFiles) {
        return parse(null, null, scriptFiles);
    }

    public static IPdxScript parse(ImmutableMap<String, IPdxScript> variables, Path... scriptFiles) {
        return parse(null, variables, scriptFiles);
    }

    public static IPdxScript parseWithDefaultPatches(Path... scriptFiles) {
        return parse(PdxPatchDatabase.DEFAULT, null, scriptFiles);
    }

    public static IPdxScript parseWithDefaultPatches(ImmutableMap<String, IPdxScript> variables, Path... scriptFiles) {
        return parse(PdxPatchDatabase.DEFAULT, variables, scriptFiles);
    }

    public static IPdxScript parse(PdxPatchDatabase patchDatabase, ImmutableMap<String, IPdxScript> variables, Path... scriptFiles) {
        if (scriptFiles.length == 0) {
            throw new IllegalArgumentException("got empty path array");
        }

        PdxScriptObject.Builder b = PdxScriptObject.builder();
        for (Path p : scriptFiles) {
            b.addAll(parse(IOUtil.getCharacterIterator(patchDatabase, p), variables != null ? variables.toMap() : null).expectObject());
        }

        return b.build();
    }

    public static IPdxScript parseWithCommonVariables(MutableMap<String, IPdxScript> variables, Path... scriptFiles) {
        return parseWithCommonVariables(null, variables, scriptFiles);
    }

    public static IPdxScript parseWithDefaultPatchesAndCommonVariables(MutableMap<String, IPdxScript> variables, Path... scriptFiles) {
        return parseWithCommonVariables(PdxPatchDatabase.DEFAULT, variables, scriptFiles);
    }

    public static IPdxScript parseWithCommonVariables(PdxPatchDatabase patchDatabase, MutableMap<String, IPdxScript> variables, Path... scriptFiles) {
        if (scriptFiles.length == 0) {
            throw new IllegalArgumentException("got empty path array");
        }

        if (variables == null) {
            variables = Maps.mutable.empty();
        }

        PdxScriptObject.Builder b = PdxScriptObject.builder();
        for (Path p : scriptFiles) {
            b.addAll(parse(IOUtil.getCharacterIterator(patchDatabase, p), variables).expectObject());
        }

        return b.build();
    }

    private static IPdxScript parse(PrimitiveIterator.OfInt charIterator, MutableMap<String, IPdxScript> variables) {
        Iterator<String> tokenIterator;
        try {
            tokenIterator = tokenize(charIterator);
        } catch (Exception e) {
            throw new RuntimeException("unable to tokenize script", e);
        }

        IteratorBuffer<String> tokens = new IteratorBuffer<>(tokenIterator, 2, 0);

        MutableMap<String, IPdxScript> mutableVariables = variables != null ? variables : Maps.mutable.empty();
        IPdxScript s;
        try {
            s = parse(tokens, mutableVariables);
        } catch (Exception e) {
            throw new RuntimeException("unable to parse script", e);
        }

        if (tokens.hasNext()) {
            throw new RuntimeException("Unconsumed tokens left at pos " + tokens.getPos());
        } else if ((!(s instanceof PdxScriptObject) && !(s instanceof PdxScriptList))) {
            throw new RuntimeException("Unexpected return value from parsing: " + (s != null ? s.getClass().getTypeName() : NULL) + COMMA_CHAR + SPACE_CHAR + tokens.getPos());
        }

        return s;
    }

    public static List<String> getUnknownLiterals() {
        Stream.Builder<ObjectIntPair<String>> b = Stream.builder();
        unknownLiterals.forEachWithOccurrences((s, i) -> b.accept(PrimitiveTuples.pair(s, i)));
        return b.build()
                .sorted(Comparator.<ObjectIntPair<String>>comparingInt(ObjectIntPair::getTwo).reversed())
                .map(ObjectIntPair::getOne)
                .toList();
    }
}
