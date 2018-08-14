package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class TradeDealListItem {

    private final int tradeDeal;

    public TradeDealListItem(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.tradeDeal = o.getInt("trade_deal");
    }

    public TradeDealListItem(int tradeDeal) {
        this.tradeDeal = tradeDeal;
    }

    public int getTradeDeal() {
        return tradeDeal;
    }
}
