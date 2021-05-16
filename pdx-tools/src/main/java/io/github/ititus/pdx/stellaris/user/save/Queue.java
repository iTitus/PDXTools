package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Queue {

    public final int id;

    public Queue(int id, IPdxScript s) {
        this.id = id;
        PdxScriptObject o = s.expectObject();
    }
}
