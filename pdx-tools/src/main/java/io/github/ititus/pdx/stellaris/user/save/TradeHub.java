package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class TradeHub {

    public final double collected;
    public final ImmutableIntList collectedFrom;
    public final ImmutableIntList sources;
    public final int destination;

    public TradeHub(PdxScriptObject o) {
        this.collected = o.getDouble("collected");
        this.collectedFrom = o.getListAsEmptyOrIntList("collected_from");
        this.sources = o.getListAsEmptyOrIntList("sources");
        this.destination = o.getInt("destination", -1);
    }
}
