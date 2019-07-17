package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class TradeRoutes {

    private final ImmutableIntObjectMap<TradeRoute> tradeRoutes;

    public TradeRoutes(PdxScriptObject o) {
        this.tradeRoutes = o.getAsIntObjectMap(TradeRoute::new);
    }

    public TradeRoutes(ImmutableIntObjectMap<TradeRoute> tradeRoutes) {
        this.tradeRoutes = tradeRoutes;
    }

    public ImmutableIntObjectMap<TradeRoute> getTradeRoutes() {
        return tradeRoutes;
    }
}
