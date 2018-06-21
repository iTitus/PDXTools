package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TradeDeals {

    private final Map<Integer, TradeDeal> tradeDeals;

    public TradeDeals(PdxScriptObject o) {
        this.tradeDeals = o.getAsMap(Integer::valueOf, PdxScriptObject.nullOr(TradeDeal::new));
    }

    public TradeDeals(Map<Integer, TradeDeal> tradeDeals) {
        this.tradeDeals = new HashMap<>(tradeDeals);
    }

    public Map<Integer, TradeDeal> getTradeDeals() {
        return Collections.unmodifiableMap(tradeDeals);
    }
}
