package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class TradeDealItem {

    public final int tradeDeal;

    public TradeDealItem(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.tradeDeal = o.getInt("trade_deal");
    }
}
