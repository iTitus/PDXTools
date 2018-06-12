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

        if (!o.hasKey("flag_date") || !o.hasKey("flag_date") || o.size() != 2) {
            throw new IllegalArgumentException(String.valueOf(o));
        }

        this.flagDate = o.getInt("flag_date");
        this.flagDays = o.getInt("flag_days");
    }
}
