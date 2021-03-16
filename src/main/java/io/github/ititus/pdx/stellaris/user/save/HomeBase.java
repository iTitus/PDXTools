package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class HomeBase {

    public final Orbitable orbitable;

    public HomeBase(PdxScriptObject o) {
        this.orbitable = o.getObjectAs("orbitable", Orbitable::new);
    }
}
