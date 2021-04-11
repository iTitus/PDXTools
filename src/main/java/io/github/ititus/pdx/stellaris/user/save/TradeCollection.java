package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class TradeCollection {

    public final ImmutableList<TradeTarget> targets;

    public TradeCollection(PdxScriptObject o) {
        this.targets = o.getListAsEmptyOrList("targets", TradeTarget::new);
    }
}
