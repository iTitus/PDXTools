package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class PreCommunicationsName {

    public final int second;

    public PreCommunicationsName(PdxScriptObject o) {
        this.second = o.getInt("second", -1);
    }
}
