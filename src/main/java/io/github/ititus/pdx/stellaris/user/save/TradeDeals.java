package io.github.ititus.pdx.stellaris.user.save;

import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TradeDeals {

    private final IntObjMap<TradeDeal> tradeDeals;

    public TradeDeals(PdxScriptObject o) {
        this.tradeDeals = o.getAsIntObjMap(Integer::parseInt, PdxScriptObject.nullOr(TradeDeal::new));
    }

    public TradeDeals(IntObjMap<TradeDeal> tradeDeals) {
        this.tradeDeals = HashIntObjMaps.newImmutableMap(tradeDeals);
    }

    public IntObjMap<TradeDeal> getTradeDeals() {
        return tradeDeals;
    }
}
