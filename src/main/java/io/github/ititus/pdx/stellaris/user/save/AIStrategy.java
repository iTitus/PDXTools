package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class AIStrategy {

    public final int id;
    public final int target;
    public final int value;
    public final int type;

    public AIStrategy(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.id = o.getInt("id");
        this.target = o.getInt("target");
        this.value = o.getInt("value");
        this.type = o.getInt("type");
    }
}
