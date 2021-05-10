package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class TradePiracy {

    public final double throughput;
    public final double total;
    public final double max;
    public final double active;
    public final double used;
    public final ImmutableList<TradeTarget> targets;

    public TradePiracy(PdxScriptObject o) {
        this.throughput = o.getDouble("throughput");
        this.total = o.getDouble("total");
        this.max = o.getDouble("max");
        this.active = o.getDouble("active");
        this.used = o.getDouble("used");
        this.targets = o.getListAsEmptyOrList("targets", TradeTarget::new);
    }
}
