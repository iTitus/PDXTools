package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class CountryBudget {

    private final Budget lastMonth;

    public CountryBudget(PdxScriptObject o) {
        PdxScriptObject o1 = o.getObject("last_month");
        this.lastMonth = o1 != null ? o1.getAs(Budget::new) : null;
    }

    public CountryBudget(Budget lastMonth) {
        this.lastMonth = lastMonth;
    }

    public Budget getLastMonth() {
        return lastMonth;
    }
}
