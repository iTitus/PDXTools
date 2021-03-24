package io.github.ititus.pdx.pdxlocalisation;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Maps;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.github.ititus.pdx.pdxscript.PdxConstants.*;

public final class PdxLocalisation {

    private final ImmutableMap<String, ImmutableMap<String, String>> localisation;

    public PdxLocalisation(ImmutableMap<String, ImmutableMap<String, String>> localisation) {
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
        ImmutableMap<String, String> languageMap = null;
        if (language != null) {
            languageMap = localisation.get(language);
        }
        if (languageMap == null && fallbackLanguage != null && !fallbackLanguage.equals(language)) {
            languageMap = localisation.get(fallbackLanguage);
        }
        if (languageMap != null) {
            if (key != null && languageMap.containsKey(key)) {
                return languageMap.get(key);
            }
            if (fallbackKey != null && !fallbackKey.equals(key) && languageMap.containsKey(fallbackKey)) {
                return languageMap.get(fallbackKey);
            }
        }
        return fallback;
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
