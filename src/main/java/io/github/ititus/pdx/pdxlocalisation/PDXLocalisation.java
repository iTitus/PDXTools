package io.github.ititus.pdx.pdxlocalisation;

import io.github.ititus.pdx.pdxscript.PdxConstants;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Maps;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class PDXLocalisation implements PdxConstants {

    private final ImmutableMap<String, ImmutableMap<String, String>> localisation;

    public PDXLocalisation(ImmutableMap<String, ImmutableMap<String, String>> localisation) {
        this.localisation = localisation;
    }

    public RichIterable<String> getLanguages() {
        return localisation.keysView();
    }

    public String get(String language, String key) {
        return get(language, key, DEFAULT_LANGUAGE, key, key);
    }

    public String get(String language, String key, String fallbackLanguage) {
        return get(language, key, fallbackLanguage, key, key);
    }

    public String get(String language, String key, String fallbackLanguage, String fallbackKey) {
        return get(language, key, fallbackLanguage, fallbackKey, key);
    }

    public String get(String language, String key, String fallbackLanguage, String fallbackKey, String fallback) {
        String internedLanguage = language; // language != null ? language.intern() : null;
        ImmutableMap<String, String> languageMap = null;
        if (internedLanguage != null) {
            languageMap = localisation.get(internedLanguage);
        }
        String internedFallbackLanguage = fallbackLanguage; // fallbackLanguage != null ? fallbackLanguage.intern() : null;
        if (languageMap == null && internedFallbackLanguage != null && !internedFallbackLanguage.equals(internedLanguage)) {
            languageMap = localisation.get(internedFallbackLanguage);
        }
        if (languageMap != null) {
            String internedKey = key; // key != null ? key.intern() : null;
            if (internedKey != null && languageMap.containsKey(internedKey)) {
                return languageMap.get(internedKey);
            }
            String internedFallbackKey = fallbackKey; // fallbackKey != null ? fallbackKey.intern() : null;
            if (internedFallbackKey != null && !internedFallbackKey.equals(internedKey) && languageMap.containsKey(internedFallbackKey)) {
                return languageMap.get(internedFallbackKey);
            }
        }
        return fallback; // fallback != null ? fallback.intern() : null;
    }

    public ImmutableMap<String, ImmutableMap<String, String>> getExtraLocalisation() {
        ImmutableMap<String, String> defaultLangMap = localisation.get(DEFAULT_LANGUAGE);
        MutableMap<String, MutableMap<String, String>> map = Maps.mutable.empty();
        localisation.forEachKeyValue((lang, langMap) -> {
            if (!DEFAULT_LANGUAGE.equals(lang)) {
                map.computeIfAbsent(lang, k -> Maps.mutable.empty()).putAll(langMap.toMap().entrySet().stream().filter(p -> !defaultLangMap.containsKey(p.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            }
        });
        MutableMap<String, ImmutableMap<String, String>> ret = Maps.mutable.empty();
        map.forEachKeyValue((lang, langMap) -> ret.put(lang, langMap.toImmutable()));
        return ret.toImmutable();
    }

    public ImmutableMap<String, ImmutableMap<String, String>> getMissingLocalisation() {
        MutableMap<String, String> defaultLanguageMap = localisation.get(DEFAULT_LANGUAGE).toMap();
        MutableMap<String, MutableMap<String, String>> map = Maps.mutable.empty();
        localisation.forEachKeyValue((lang, langMap) -> {
            if (!DEFAULT_LANGUAGE.equals(lang)) {
                map.computeIfAbsent(lang, k -> Maps.mutable.empty()).putAll(defaultLanguageMap.entrySet().stream().filter(p -> !langMap.containsKey(p.getKey()) || langMap.get(p.getKey()).equals(p.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            }
        });
        MutableMap<String, ImmutableMap<String, String>> ret = Maps.mutable.empty();
        map.forEachKeyValue((lang, langMap) -> ret.put(lang, langMap.toImmutable()));
        return ret.toImmutable();
    }

    public String toYML() {
        StringBuilder b = new StringBuilder();
        localisation.forEachKeyValue((lang, langMap) -> {
            b.append(lang).append(COLON_CHAR).append(LINE_FEED);
            langMap.forEachKeyValue((k, v) ->
                    b
                            .append(SPACE_CHAR)
                            .append(k)
                            .append(COLON_CHAR)
                            .append(ZERO_CHAR)
                            .append(SPACE_CHAR)
                            .append(QUOTE_CHAR)
                            .append(v)
                            .append(QUOTE_CHAR)
                            .append(LINE_FEED)
            );
        });
        return b.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PDXLocalisation)) {
            return false;
        }
        PDXLocalisation that = (PDXLocalisation) o;
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
