package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

public class TradeDeal {

    public final int id;
    public final TradeAgreement first;
    public final TradeAgreement second;
    public final int length;
    public final LocalDate date;

    public TradeDeal(int id, IPdxScript s) {
        this.id = id;
        PdxScriptObject o = s.expectObject();
        this.first = o.getObjectAs("first", TradeAgreement::new);
        this.second = o.getObjectAs("second", TradeAgreement::new);
        this.length = o.getInt("length");
        this.date = o.getDate("date");
    }
}
