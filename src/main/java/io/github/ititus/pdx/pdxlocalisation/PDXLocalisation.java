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
        if (language != null) {
            Map<String, String> languageMap = localisation.get(language);
            if (languageMap == null && !language.equals(fallbackLanguage)) {
                languageMap = localisation.get(fallbackLanguage);
            }
            if (languageMap != null) {
                if (key != null && languageMap.containsKey(key)) {
                    return languageMap.get(key);
                } else if (((key != null && !key.equals(fallbackKey)) || (fallbackKey != null && !fallbackKey.equals(key))) && languageMap.containsKey(fallbackKey)) {
                    return languageMap.get(fallbackKey);
                }
            }
        }
        return fallback;
    }

    public String toYML() {
        return "";
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
