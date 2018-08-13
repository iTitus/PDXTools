package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.multimap.ImmutableMultimap;

import java.util.function.Function;

public class CountingList {

    private final ImmutableMultimap<String, Integer> map;

    public CountingList(PdxScriptObject o) {
        this.map = o.getAsMultimap(Function.identity(), PdxConstants.NULL_OR_INTEGER);
    }

    public CountingList(ImmutableMultimap<String, Integer> map) {
        this.map = map;
    }

    public ImmutableMultimap<String, Integer> getMap() {
        return map;
    }
}
