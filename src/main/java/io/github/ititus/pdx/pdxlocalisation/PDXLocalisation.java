package io.github.ititus.pdx.pdxlocalisation;

import java.util.*;

public final class PDXLocalisation {

    public static final String ENGLISH = "l_english";
    public static final String BRAZ_POR = "l_braz_por";
    public static final String GERMAN = "l_german";
    public static final String FRENCH = "l_french";
    public static final String SPANISH = "l_spanish";
    public static final String POLISH = "l_polish";
    public static final String RUSSIAN = "l_russian";
    public static final String DEFAULT = ENGLISH;

    private final Map<String, Map<String, String>> localisation;

    public PDXLocalisation(Map<String, Map<String, String>> localisation) {
        this.localisation = new HashMap<>();
        localisation.forEach((language, languageMap) -> this.localisation.put(language, new HashMap<>(languageMap)));
    }

    public Set<String> getLanguages() {
        return Collections.unmodifiableSet(localisation.keySet());
    }

    public String get(String language, String key) {
        return get(language, key, DEFAULT, key, key);
    }

    public String get(String language, String key, String fallbackLanguage) {
        return get(language, key, fallbackLanguage, key, key);
    }

    public String get(String language, String key, String fallbackLanguage, String fallbackKey) {
        return get(language, key, fallbackLanguage, fallbackKey, key);
    }

    public String get(String language, String key, String fallbackLanguage, String fallbackKey, String fallback) {
        String internedLanguage = language != null ? language.intern() : null;
        Map<String, String> languageMap = null;
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

    public String toYML() {
        StringBuilder b = new StringBuilder();
        localisation.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEachOrdered(langEntry -> {
            b.append(langEntry.getKey()).append(":\n");
            langEntry.getValue().entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEachOrdered(translationEntry -> b.append(' ').append(translationEntry.getKey()).append(":0 \"").append(translationEntry.getValue()).append("\"\n"));
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
