package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class PreCommunicationsName {

    private final int second;

    public PreCommunicationsName(PdxScriptObject o) {
        this.second = o.getInt("second", -1);
    }

    public PreCommunicationsName(int second) {
        this.second = second;
    }

    public int getSecond() {
        return second;
    }
}
