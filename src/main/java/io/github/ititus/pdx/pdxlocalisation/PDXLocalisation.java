package io.github.ititus.pdx.pdxlocalisation;

import com.koloboke.collect.map.ObjObjMap;
import com.koloboke.collect.map.hash.HashObjObjMaps;
import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.util.collection.CollectionUtil;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class PDXLocalisation implements PdxConstants {

    private final ObjObjMap<String, ObjObjMap<String, String>> localisation;

    public PDXLocalisation(ObjObjMap<String, ObjObjMap<String, String>> localisation) {
        this.localisation = CollectionUtil.toImmutableDeep(localisation);
    }

    public Set<String> getLanguages() {
        return localisation.keySet();
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
        String internedLanguage = language != null ? language.intern() : null;
        ObjObjMap<String, String> languageMap = null;
        if (internedLanguage != null) {
            languageMap = localisation.get(internedLanguage);
        }
        String internedFallbackLanguage = fallbackLanguage != null ? fallbackLanguage.intern() : null;
        if (languageMap == null && internedFallbackLanguage != null && !internedFallbackLanguage.equals(internedLanguage)) {
            languageMap = localisation.get(internedFallbackLanguage);
        }
        if (languageMap != null) {
            String internedKey = key != null ? key.intern() : null;
            if (internedKey != null && languageMap.containsKey(internedKey)) {
                return languageMap.get(internedKey);
            }
            String internedFallbackKey = fallbackKey != null ? fallbackKey.intern() : null;
            if (internedFallbackKey != null && !internedFallbackKey.equals(internedKey) && languageMap.containsKey(internedFallbackKey)) {
                return languageMap.get(internedFallbackKey);
            }
        }
        return fallback != null ? fallback.intern() : null;
    }

    public ObjObjMap<String, ObjObjMap<String, String>> getExtraLocalisation() {
        ObjObjMap<String, String> defaultLanguageMap = localisation.get(DEFAULT_LANGUAGE);
        ObjObjMap<String, ObjObjMap<String, String>> map = HashObjObjMaps.newUpdatableMap();
        localisation.forEach((language, languageMap) -> {
            if (!DEFAULT_LANGUAGE.equals(language)) {
                map.computeIfAbsent(language, k -> HashObjObjMaps.newUpdatableMap()).putAll(languageMap.entrySet().stream().filter(p -> !defaultLanguageMap.containsKey(p.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            }
        });
        return map;
    }

    public ObjObjMap<String, ObjObjMap<String, String>> getMissingLocalisation() {
        ObjObjMap<String, String> defaultLanguageMap = localisation.get(DEFAULT_LANGUAGE);
        ObjObjMap<String, ObjObjMap<String, String>> map = HashObjObjMaps.newUpdatableMap();
        localisation.forEach((language, languageMap) -> {
            if (!DEFAULT_LANGUAGE.equals(language)) {
                map.computeIfAbsent(language, k -> HashObjObjMaps.newUpdatableMap()).putAll(defaultLanguageMap.entrySet().stream().filter(p -> !languageMap.containsKey(p.getKey()) || languageMap.get(p.getKey()).equals(p.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            }
        });
        return map;
    }

    public String toYML() {
        StringBuilder b = new StringBuilder();
        localisation.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEachOrdered(langEntry -> {
            b.append(langEntry.getKey()).append(COLON_CHAR).append(LINE_FEED);
            langEntry
                    .getValue()
                    .entrySet()
                    .stream()
                    .sorted(Comparator.comparing(Map.Entry::getKey))
                    .forEachOrdered(translationEntry ->
                            b
                                    .append(SPACE_CHAR)
                                    .append(translationEntry.getKey())
                                    .append(COLON_CHAR)
                                    .append(ZERO_CHAR)
                                    .append(SPACE_CHAR)
                                    .append(QUOTE_CHAR)
                                    .append(translationEntry.getValue())
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
        return Objects.hash(localisation);
    }

    @Override
    public String toString() {
        return "PDXLocalisation{" +
                "localisation=" + localisation +
                '}';
    }
}
