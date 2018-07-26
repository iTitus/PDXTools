package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.ViewableList;
import io.github.ititus.pdx.util.collection.ViewableUnmodifiableArrayList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RandomNameDatabase {

    private final ViewableList<String> speciesModificationPrefix, speciesModificationPostfix, starNames, blackHoleNames, nebulaNames, asteroidPrefix;
    private final ViewableList<ViewableList<String>> asteroidPostfix;

    public RandomNameDatabase(PdxScriptObject o) {
        this.speciesModificationPrefix = o.getList("species_modification_prefix").getAsStringList();
        this.speciesModificationPostfix = o.getList("species_modification_postfix").getAsStringList();
        this.starNames = o.getList("star_names").getAsStringList();
        this.blackHoleNames = o.getList("black_hole_names").getAsStringList();
        this.nebulaNames = o.getList("nebula_names").getAsStringList();
        this.asteroidPrefix = o.getList("asteroid_prefix").getAsStringList();
        this.asteroidPostfix = o.getImplicitList("asteroid_postfix").getAsList(s -> {
            if (s instanceof PdxScriptList) {
                return ((PdxScriptList) s).getAsStringList();
            }
            return null;
        });
    }

    public RandomNameDatabase(Collection<String> speciesModificationPrefix, Collection<String> speciesModificationPostfix, Collection<String> starNames, Collection<String> blackHoleNames, Collection<String> nebulaNames, Collection<String> asteroidPrefix, Collection<Collection<String>> asteroidPostfix) {
        this.speciesModificationPrefix = new ViewableUnmodifiableArrayList<>(speciesModificationPrefix);
        this.speciesModificationPostfix = new ViewableUnmodifiableArrayList<>(speciesModificationPostfix);
        this.starNames = new ViewableUnmodifiableArrayList<>(starNames);
        this.blackHoleNames = new ViewableUnmodifiableArrayList<>(blackHoleNames);
        this.nebulaNames = new ViewableUnmodifiableArrayList<>(nebulaNames);
        this.asteroidPrefix = new ViewableUnmodifiableArrayList<>(asteroidPrefix);
        ViewableUnmodifiableArrayList.Builder<ViewableList<String>> b = ViewableUnmodifiableArrayList.builder();
        asteroidPostfix.forEach(c -> b.add(new ViewableUnmodifiableArrayList<>(c)));
        this.asteroidPostfix = b.build();
    }

    public List<String> getSpeciesModificationPrefix() {
        return speciesModificationPrefix.getView();
    }

    public List<String> getSpeciesModificationPostfix() {
        return speciesModificationPostfix.getView();
    }

    public List<String> getStarNames() {
        return starNames.getView();
    }

    public List<String> getBlackHoleNames() {
        return blackHoleNames.getView();
    }

    public List<String> getNebulaNames() {
        return nebulaNames.getView();
    }

    public List<String> getAsteroidPrefix() {
        return asteroidPrefix.getView();
    }

    public List<List<String>> getAsteroidPostfix() {
        return Collections.unmodifiableList(asteroidPostfix.stream().map(ViewableList::getView).collect(Collectors.toList()));
    }
}
