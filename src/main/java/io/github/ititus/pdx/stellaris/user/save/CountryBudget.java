package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class CountryBudget {

    public final Budget currentMonth;
    public final Budget lastMonth;

    public CountryBudget(PdxScriptObject o) {
        this.currentMonth = o.getObjectAsNullOr("current_month", Budget::new);
        this.lastMonth = o.getObjectAsNullOr("last_month", Budget::new);
    }
}
