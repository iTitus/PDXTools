package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class FlagData {

    public final String name;
    public final int flagDate, flagDays;

    private FlagData(String name, PdxScriptObject o) {
        this.name = name;
        this.flagDate = o.getInt("flag_date");
        this.flagDays = o.getInt("flag_days");
    }

    private FlagData(String name, int flagDate, int flagDays) {
        this.name = name;
        this.flagDate = flagDate;
        this.flagDays = flagDays;
    }

    public static FlagData of(String name, IPdxScript s) {
        if (s instanceof PdxScriptObject) {
            return new FlagData(name, s.expectObject());
        }

        return new FlagData(name, s.expectValue().expectInt(), 0);
    }
}
