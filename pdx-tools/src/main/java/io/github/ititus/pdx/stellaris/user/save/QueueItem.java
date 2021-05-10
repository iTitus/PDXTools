package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class QueueItem {

    public QueueItem(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
    }
}
