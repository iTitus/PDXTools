package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class RandomNameDatabase {

    private final ImmutableList<String> speciesModificationPrefix, speciesModificationPostfix, starNames,
            blackHoleNames, nebulaNames, asteroidPrefix;
    private final ImmutableList<ImmutableList<String>> asteroidPostfix;

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

    public RandomNameDatabase(ImmutableList<String> speciesModificationPrefix,
                              ImmutableList<String> speciesModificationPostfix, ImmutableList<String> starNames,
                              ImmutableList<String> blackHoleNames, ImmutableList<String> nebulaNames,
                              ImmutableList<String> asteroidPrefix,
                              ImmutableList<ImmutableList<String>> asteroidPostfix) {
        this.speciesModificationPrefix = speciesModificationPrefix;
        this.speciesModificationPostfix = speciesModificationPostfix;
        this.starNames = starNames;
        this.blackHoleNames = blackHoleNames;
        this.nebulaNames = nebulaNames;
        this.asteroidPrefix = asteroidPrefix;
        this.asteroidPostfix = asteroidPostfix;
    }

    public ImmutableList<String> getSpeciesModificationPrefix() {
        return speciesModificationPrefix;
    }

    public ImmutableList<String> getSpeciesModificationPostfix() {
        return speciesModificationPostfix;
    }

    public ImmutableList<String> getStarNames() {
        return starNames;
    }

    public ImmutableList<String> getBlackHoleNames() {
        return blackHoleNames;
    }

    public ImmutableList<String> getNebulaNames() {
        return nebulaNames;
    }

    public ImmutableList<String> getAsteroidPrefix() {
        return asteroidPrefix;
    }

    public ImmutableList<ImmutableList<String>> getAsteroidPostfix() {
        return asteroidPostfix;
    }
}
