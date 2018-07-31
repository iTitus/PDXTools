package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class PopFactions {

    private final ImmutableIntObjectMap<PopFaction> popFactions;

    public PopFactions(PdxScriptObject o) {
        this.popFactions = o.getAsIntObjectMap(Integer::parseInt, PdxScriptObject.nullOr(PopFaction::new));
    }

    public PopFactions(ImmutableIntObjectMap<PopFaction> popFactions) {
        this.popFactions = popFactions;
    }

    public ImmutableIntObjectMap<PopFaction> getPopFactions() {
        return popFactions;
    }
}
