package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Util;

public class FlagData {

    private final int flagDate, flagDays;

    public FlagData(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;
        this.flagDate = o.getInt("flag_date");
        this.flagDays = o.getInt("flag_days");
    }

    public int getFlagDate() {
        return flagDate;
    }

    public int getFlagDays() {
        return flagDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FlagData)) {
            return false;
        }
        FlagData flagData = (FlagData) o;
        return flagDate == flagData.flagDate && flagDays == flagData.flagDays;
    }

    @Override
    public int hashCode() {
        return Util.hash(flagDate, flagDays);
    }
}
