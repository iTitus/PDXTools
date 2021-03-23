package io.github.ititus.pdx.stellaris.game.common.planet_classes;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.ImmutableMap;

import static java.util.function.Predicate.not;

public class PlanetClasses {

    public final ImmutableMap<String, PlanetClass> planetClasses;
    public final ImmutableList<PlanetNameList> nameLists;

    public PlanetClasses(PdxScriptObject o) {
        this.planetClasses = o.getAsStringObjectMap(not("random_list"::equals), PlanetClass::new);
        this.nameLists = o.getImplicitListAsList("random_list", PlanetNameList::new);
    }
}
