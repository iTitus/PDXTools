package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

@Deprecated
public class TradeDeal {

    public final TradeAgreement first;
    public final TradeAgreement second;
    public final int length;
    public final LocalDate date;

    public TradeDeal(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.first = o.getObjectAs("first", TradeAgreement::new);
        this.second = o.getObjectAs("second", TradeAgreement::new);
        this.length = o.getInt("length");
        this.date = o.getDate("date");
    }
}
