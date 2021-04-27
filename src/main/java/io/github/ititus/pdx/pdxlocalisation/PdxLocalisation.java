package io.github.ititus.pdx.pdxlocalisation;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.multimap.ImmutableMultimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Multimaps;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static io.github.ititus.pdx.pdxscript.PdxConstants.DEFAULT;
import static io.github.ititus.pdx.pdxscript.PdxConstants.DEFAULT_LANGUAGE;

public final class PdxLocalisation {

    private static final ImmutableSet<String> COLOR_CODES = Sets.immutable.of("!", "B", "E", "G", "H", "L", "M", "P", "R", "S", "T", "W", "Y");

    private final ImmutableSet<String> languages;
    private final ImmutableMap<String, ImmutableMap<String, String>> localisation;

    public PdxLocalisation(ImmutableSet<String> languages, ImmutableMap<String, ImmutableMap<String, String>> localisation) {
        this.languages = languages;
        this.localisation = localisation;
    }

    private static String formatArgument(Object v, String options) {
        if ("".equals(options) || COLOR_CODES.contains(options)) {
            return v.toString();
        } else if ("0=+".equals(options)) {
            int i = v instanceof Integer ? (int) v : Integer.parseInt(v.toString());
            return (i >= 0 ? "+" : "") + i;
        }

        throw new UnsupportedOperationException("option " + options + " for $-expressions not supported");
    }

    public ImmutableSet<String> getLanguages() {
        return languages;
    }

    public RichIterable<String> getTranslationKeys() {
        return localisation.keysView();
    }

    public String translate(String key) {
        return get(DEFAULT_LANGUAGE, key, null).orElse(key);
    }

    public String translate(String key, ImmutableMap<String, ?> arguments) {
        return get(DEFAULT_LANGUAGE, key, arguments).orElse(key);
    }

    public String translate(String language, String key) {
        return get(language, key, null).orElse(key);
    }

    public String translate(String language, String key, ImmutableMap<String, ?> arguments) {
        return get(language, key, arguments).orElse(key);
    }

    private Optional<String> get(String language, String key, ImmutableMap<String, ?> arguments) {
        String lowerKey = key.toLowerCase(Locale.ROOT);
        ImmutableMap<String, String> translationMap = localisation.get(lowerKey);
        if (translationMap == null) {
            return Optional.empty();
        }

        String translation = translationMap.get(language);
        if (translation == null && !Objects.equals(language, DEFAULT_LANGUAGE)) {
            translation = translationMap.get(DEFAULT_LANGUAGE);
        }

        if (translation == null && !Objects.equals(language, DEFAULT)) {
            translation = translationMap.get(DEFAULT);
        }

        return Optional.ofNullable(translation).map(s -> format(language, lowerKey, s, arguments));
    }

    private String format(String language, String key, String translation, ImmutableMap<String, ?> arguments) {
        int dollarOpen;
        int dollarClose = -1;
        while ((dollarOpen = translation.indexOf('$', dollarClose + 1)) >= 0) {
            dollarClose = translation.indexOf('$', dollarOpen + 1);
            if (dollarClose < 0) {
                throw new RuntimeException("missing closing $");
            }

            String dollarExpression = translation.substring(dollarOpen + 1, dollarClose);
            int totalExpressionLength = dollarExpression.length();

            int optionSeparator = dollarExpression.lastIndexOf('|', totalExpressionLength - 1);
            String dollarOptions;
            if (optionSeparator >= 0) {
                dollarOptions = dollarExpression.substring(optionSeparator + 1);
                dollarExpression = dollarExpression.substring(0, optionSeparator);
            } else {
                dollarOptions = "";
            }

            if (dollarExpression.lastIndexOf('|', totalExpressionLength - 1) >= 0) {
                throw new UnsupportedOperationException("options for $-expressions not supported");
            }

            Object argumentValue = arguments != null ? arguments.get(dollarExpression) : null;
            String value = argumentValue != null ? formatArgument(argumentValue, dollarOptions) : translate(language, dollarExpression);
            translation = translation.substring(0, dollarOpen) + value + translation.substring(dollarClose + 1);
            dollarClose -= 2 + totalExpressionLength - value.length();
        }

        translation = translation.replace("\\n", "\n");
        translation = translation.replace("\\t", "\t");

        return translation;
    }

    public ImmutableMultimap<String, String> getExtraLocalisation() {
        MutableMultimap<String, String> map = Multimaps.mutable.set.empty();
        localisation.forEachKeyValue((key, translationMap) -> {
            if (!translationMap.containsKey(DEFAULT_LANGUAGE) && !translationMap.containsKey(DEFAULT)) {
                map.putAll(key, translationMap.keysView());
            }
        });

        return map.toImmutable();
    }

    public ImmutableMultimap<String, String> getMissingLocalisation() {
        MutableMultimap<String, String> map = Multimaps.mutable.set.empty();
        localisation.forEachKeyValue((key, translationMap) -> {
            if (translationMap.containsKey(DEFAULT_LANGUAGE)) {
                MutableSet<String> copy = languages.toSet();
                copy.removeAllIterable(localisation.keysView());
                map.putAll(key, copy);
            }
        });

        return map.toImmutable();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof PdxLocalisation)) {
            return false;
        }

        PdxLocalisation that = (PdxLocalisation) o;
        return Objects.equals(localisation, that.localisation);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(localisation);
    }

    @Override
    public String toString() {
        return "PDXLocalisation{" +
                "localisation=" + localisation +
                '}';
    }
}
