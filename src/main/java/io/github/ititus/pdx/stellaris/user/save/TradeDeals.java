package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class TradeDeals {

    private final ImmutableIntObjectMap<TradeDeal> tradeDeals;

    public TradeDeals(PdxScriptObject o) {
        this.tradeDeals = o.getAsIntObjectMap(Integer::parseInt, PdxScriptObject.nullOr(TradeDeal::new));
        // TODO: always none
    }

    public TradeDeals(ImmutableIntObjectMap<TradeDeal> tradeDeals) {
        this.tradeDeals = tradeDeals;
    }

    public ImmutableIntObjectMap<TradeDeal> getTradeDeals() {
        return tradeDeals;
    }
}
