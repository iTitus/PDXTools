package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class CountryBudget {

    private final Budget currentMonth, lastMonth;

    public CountryBudget(PdxScriptObject o) {
        PdxScriptObject o1 = o.getObject("current_month");
        this.currentMonth = o1 != null ? o1.getAs(Budget::new) : null;
        o1 = o.getObject("last_month");
        this.lastMonth = o1 != null ? o1.getAs(Budget::new) : null;
    }

    public CountryBudget(Budget currentMonth, Budget lastMonth) {
        this.currentMonth = currentMonth;
        this.lastMonth = lastMonth;
    }

    public Budget getCurrentMonth() {
        return currentMonth;
    }

    public Budget getLastMonth() {
        return lastMonth;
    }
}
