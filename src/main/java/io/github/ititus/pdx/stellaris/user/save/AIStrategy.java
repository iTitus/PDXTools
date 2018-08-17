package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class AIStrategy {

    private final int id, target, value, type;

    public AIStrategy(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.id = o.getInt("id");
        this.target = o.getInt("target");
        this.value = o.getInt("value");
        this.type = o.getInt("type");
    }

    public AIStrategy(int id, int target, int value, int type) {
        this.id = id;
        this.target = target;
        this.value = value;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getTarget() {
        return target;
    }

    public int getValue() {
        return value;
    }

    public int getType() {
        return type;
    }
}
