package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class PreCommunicationsName {

    private final int second, unSecond;

    public PreCommunicationsName(PdxScriptObject o) {
        this.second = o.getInt("second", -1);
        this.unSecond = o.getInt("un_second", -1);
    }

    public PreCommunicationsName(int second, int unSecond) {
        this.second = second;
        this.unSecond = unSecond;
    }

    public int getSecond() {
        return second;
    }

    public int getUnSecond() {
        return unSecond;
    }
}
