package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Date;

public class TradeDeal {

    private final int length;
    private final Date date;
    private final TradeAgreement first, second;

    public TradeDeal(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.first = o.getObject("first").getAs(TradeAgreement::new);
        this.second = o.getObject("second").getAs(TradeAgreement::new);
        this.length = o.getInt("length");
        this.date = o.getDate("date");
    }

    public TradeDeal(int length, Date date, TradeAgreement first, TradeAgreement second) {
        this.length = length;
        this.date = date;
        this.first = first;
        this.second = second;
    }

    public int getLength() {
        return length;
    }

    public Date getDate() {
        return date;
    }

    public TradeAgreement getFirst() {
        return first;
    }

    public TradeAgreement getSecond() {
        return second;
    }
}
