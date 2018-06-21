package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.IPdxScript;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;

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
}
