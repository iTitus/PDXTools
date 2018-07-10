package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RandomNameDatabase {

    private final List<String> speciesModificationPrefix, speciesModificationPostfix, starNames, blackHoleNames, nebulaNames, asteroidPrefix;
    private final List<List<String>> asteroidPostfix;

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
        this.speciesModificationPrefix = new ArrayList<>(speciesModificationPrefix);
        this.speciesModificationPostfix = new ArrayList<>(speciesModificationPostfix);
        this.starNames = new ArrayList<>(starNames);
        this.blackHoleNames = new ArrayList<>(blackHoleNames);
        this.nebulaNames = new ArrayList<>(nebulaNames);
        this.asteroidPrefix = new ArrayList<>(asteroidPrefix);
        this.asteroidPostfix = new ArrayList<>();
        asteroidPostfix.forEach(c -> this.asteroidPostfix.add(new ArrayList<>(c)));
    }

    public List<String> getSpeciesModificationPrefix() {
        return Collections.unmodifiableList(speciesModificationPrefix);
    }

    public List<String> getSpeciesModificationPostfix() {
        return Collections.unmodifiableList(speciesModificationPostfix);
    }

    public List<String> getStarNames() {
        return Collections.unmodifiableList(starNames);
    }

    public List<String> getBlackHoleNames() {
        return Collections.unmodifiableList(blackHoleNames);
    }

    public List<String> getNebulaNames() {
        return Collections.unmodifiableList(nebulaNames);
    }

    public List<String> getAsteroidPrefix() {
        return Collections.unmodifiableList(asteroidPrefix);
    }

    public List<List<String>> getAsteroidPostfix() {
        List<List<String>> list = new ArrayList<>();
        asteroidPostfix.forEach(l -> list.add(Collections.unmodifiableList(l)));
        return Collections.unmodifiableList(list);
    }
}
