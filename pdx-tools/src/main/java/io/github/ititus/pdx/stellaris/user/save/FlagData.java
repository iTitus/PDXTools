package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FlagData {

    public final int flagDate, flagDays;

    private FlagData(PdxScriptObject o) {
        this.flagDate = o.getInt("flag_date");
        this.flagDays = o.getInt("flag_days");
    }

    private FlagData(int flagDate, int flagDays) {
        this.flagDate = flagDate;
        this.flagDays = flagDays;
    }

    public static FlagData of(IPdxScript s) {
        if (s instanceof PdxScriptObject) {
            return new FlagData(s.expectObject());
        }

        return new FlagData(s.expectValue().expectInt(), 0);
    }
}
