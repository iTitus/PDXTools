package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.ViewableArrayList;
import io.github.ititus.pdx.util.collection.ViewableList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RandomNameDatabase {

    private final ViewableList<String> speciesModificationPrefix, speciesModificationPostfix, starNames, blackHoleNames, nebulaNames, asteroidPrefix;
    private final ViewableList<List<String>> asteroidPostfix;

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
        this.speciesModificationPrefix = new ViewableArrayList<>(speciesModificationPrefix);
        this.speciesModificationPostfix = new ViewableArrayList<>(speciesModificationPostfix);
        this.starNames = new ViewableArrayList<>(starNames);
        this.blackHoleNames = new ViewableArrayList<>(blackHoleNames);
        this.nebulaNames = new ViewableArrayList<>(nebulaNames);
        this.asteroidPrefix = new ViewableArrayList<>(asteroidPrefix);
        this.asteroidPostfix = new ViewableArrayList<>();
        asteroidPostfix.forEach(c -> this.asteroidPostfix.add(new ViewableArrayList<>(c)));
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
        List<List<String>> list = new ViewableArrayList<>();
        asteroidPostfix.forEach(l -> list.add(Collections.unmodifiableList(l)));
        return list.getView();
    }
}
