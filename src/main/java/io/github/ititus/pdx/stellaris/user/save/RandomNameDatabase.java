package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class RandomNameDatabase {

    public final ImmutableList<String> speciesModificationPrefix;
    public final ImmutableList<String> speciesModificationPostfix;
    public final ImmutableList<String> starNames;
    public final ImmutableList<String> blackHoleNames;
    public final ImmutableList<String> nebulaNames;
    public final ImmutableList<String> asteroidPrefix;
    public final ImmutableList<ImmutableList<String>> asteroidPostfix;
    public final boolean usePrefix;

    public RandomNameDatabase(PdxScriptObject o) {
        this.speciesModificationPrefix = o.getListAsStringList("species_modification_prefix");
        this.speciesModificationPostfix = o.getListAsStringList("species_modification_postfix");
        this.starNames = o.getListAsStringList("star_names");
        this.blackHoleNames = o.getListAsStringList("black_hole_names");
        this.nebulaNames = o.getListAsStringList("nebula_names");
        this.asteroidPrefix = o.getListAsStringList("asteroid_prefix");
        this.asteroidPostfix = o.getImplicitListAsList("asteroid_postfix", s -> s.expectList().getAsStringList());
        this.usePrefix = o.getBoolean("use_prefix");
    }
}
