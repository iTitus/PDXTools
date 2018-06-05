package io.github.ititus.stellaris.analyser.pdxscript;

import io.github.ititus.stellaris.analyser.util.CollectionUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PdxScriptParser {

    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
    public static final String YES = "yes";
    public static final String NO = "no";
    public static final String NONE = "none";

    private static final String LIST_OBJECT_OPEN = "{";
    private static final String LIST_OBJECT_CLOSE = "}";
    private static final String EQUALS = "=";
    private static final String INDENT = "\t";
    private static final char QUOTE = '"';
    private static final char ESCAPE = '\\';
    private static final Pattern QUOTE_PATTERN = Pattern.compile("\\s|=|");

    private static ScriptIntPair parse(List<String> tokens, int i) {
        String token = tokens.get(i);
        if (i == 0) {
            assert LIST_OBJECT_OPEN.equals(token) && LIST_OBJECT_CLOSE.equals(tokens.get(tokens.size() - 1));
        }

        IPdxScript object;
        if (LIST_OBJECT_OPEN.equals(token)) {
            if (EQUALS.equals(tokens.get(i + 2)) || LIST_OBJECT_CLOSE.equals(tokens.get(i + 1))) {
                //object or empty
                i++;
                PdxScriptObject.Builder b = PdxScriptObject.builder();
                while (!LIST_OBJECT_CLOSE.equals(tokens.get(i))) {
                    String key = tokens.get(i);
                    i++;

                    String equals = tokens.get(i);
                    assert EQUALS.equals(equals);
                    i++;

                    ScriptIntPair pair = parse(tokens, i);
                    i = pair.i;

                    b.add(key, pair.o);
                }
                i++;

                object = b.build();
            } else {
                //list
                i++;
                PdxScriptList.Builder b = PdxScriptList.builder();
                while (!LIST_OBJECT_CLOSE.equals(tokens.get(i))) {
                    ScriptIntPair pair = parse(tokens, i);
                    i = pair.i;

                    b.add(pair.o);
                }
                i++;

                object = b.build();
            }
        } else {
            int l = token.length();
            assert l > 1 || !isTokenSeparator(token.charAt(0));

            Object value;

            if (token.charAt(0) == QUOTE && token.charAt(l - 1) == QUOTE) {
                token = token.substring(1, token.length() - 1);
                try {
                    value = SDF.parse(token);
                } catch (ParseException e) {
                    value = token;
                }
            } else if (NONE.equals(token)) {
                value = null;
            } else if (NO.equals(token)) {
                value = Boolean.FALSE;
            } else if (YES.equals(token)) {
                value = Boolean.TRUE;
            } else {
                try {
                    value = SDF.parse(token);
                } catch (ParseException e1) {
                    try {
                        value = Integer.valueOf(token);
                    } catch (NumberFormatException e2) {
                        try {
                            value = Long.valueOf(token);
                        } catch (NumberFormatException e3) {
                            try {
                                value = Double.valueOf(token);
                            } catch (NumberFormatException e4) {
                                //fallback to string
                                value = token;
                            }
                        }
                    }
                }
            }

            object = new PdxScriptValue(value);
            i++;
        }

        return new ScriptIntPair(object, i);
    }

    private static List<String> tokenize(String src) {
        List<String> tokens = CollectionUtil.listOf(LIST_OBJECT_OPEN);
        boolean openQuotes = false, token = false;
        int tokenStart = 0;
        for (int i = 0; i < src.length(); i++) {
            if (openQuotes && src.charAt(i) == QUOTE && src.charAt(i - 1) != ESCAPE) {
                tokens.add(QUOTE + src.substring(tokenStart, i) + QUOTE);
                openQuotes = false;
            } else if (!openQuotes && src.charAt(i) == QUOTE) {
                if (token) {
                    tokens.add(src.substring(tokenStart, i));
                    token = false;
                }
                openQuotes = true;
                tokenStart = i + 1;
            } else if (!openQuotes && isTokenSeparator(src.charAt(i))) {
                if (token) {
                    tokens.add(src.substring(tokenStart, i));
                    token = false;
                }
                tokens.add(String.valueOf(src.charAt(i)));
            } else if (!openQuotes && token && Character.isWhitespace(src.charAt(i))) {
                tokens.add(src.substring(tokenStart, i));
                token = false;
            } else if (!openQuotes && !token && !Character.isWhitespace(src.charAt(i))) {
                token = true;
                tokenStart = i;
            }
        }
        assert !openQuotes;
        if (token) {
            tokens.add(src.substring(tokenStart, src.length()));
            token = false;
        }
        assert !token;
        tokens.add(LIST_OBJECT_CLOSE);
        return tokens;
    }

    private static boolean isTokenSeparator(char c) {
        return c == '{' || c == '}' || c == '=';
    }

    public static String quote(String s) {
        return '"' + s.replaceAll("\"", "\\\"") + '"';
    }

    public static String quoteIfNecessary(String s) {
        if (QUOTE_PATTERN.matcher(s).find()) {
            return quote(s);
        }
        return s;
    }

    public static String indent(int indent) {
        if (indent > 1) {
            return IntStream.range(0, indent).mapToObj(i -> INDENT).collect(Collectors.joining());
        } else if (indent == 1) {
            return INDENT;
        }
        return "";
    }

    public static PdxScriptObject parse(File scriptFile) {
        String src = "";
        try (Stream<String> stream = Files.lines(scriptFile.toPath(), StandardCharsets.UTF_8)) {
            src = stream.collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parse(src);
    }

    public static PdxScriptObject parse(String src) {
        List<String> tokens = new ArrayList<>(tokenize(src));
        ScriptIntPair pair = parse(tokens, 0);
        assert pair.o instanceof PdxScriptObject && pair.i == tokens.size() - 1;
        return (PdxScriptObject) pair.o;
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
