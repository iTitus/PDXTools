package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

@Deprecated
public class TradeDealListItem {

    public final int tradeDeal;

    public TradeDealListItem(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.tradeDeal = o.getInt("trade_deal");
    }
}
