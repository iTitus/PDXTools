package io.github.ititus.pdx.pdxlocalisation;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.multimap.ImmutableMultimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Multimaps;

import java.util.Objects;
import java.util.Optional;

import static io.github.ititus.pdx.pdxscript.PdxConstants.DEFAULT;
import static io.github.ititus.pdx.pdxscript.PdxConstants.DEFAULT_LANGUAGE;

public final class PdxLocalisation {

    private final ImmutableSet<String> languages;
    private final ImmutableMap<String, ImmutableMap<String, String>> localisation;

    public PdxLocalisation(ImmutableSet<String> languages, ImmutableMap<String, ImmutableMap<String, String>> localisation) {
        this.languages = languages;
        this.localisation = localisation;
    }

    public ImmutableSet<String> getLanguages() {
        return languages;
    }

    public RichIterable<String> getTranslationKeys() {
        return localisation.keysView();
    }

    public String translate(String key) {
        return get(DEFAULT_LANGUAGE, key).orElse(key);
    }

    public String translate(String language, String key) {
        return get(language, key).orElse(key);
    }

    private Optional<String> get(String language, String key) {
        ImmutableMap<String, String> translationMap = localisation.get(key);
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

        return Optional.ofNullable(translation);
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
