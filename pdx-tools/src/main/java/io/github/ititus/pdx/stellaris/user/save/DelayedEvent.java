package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class DelayedEvent {

    public final String event;
    public final int days;
    public final Scope scope;

    public DelayedEvent(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.event = o.getString("event");
        this.days = o.getInt("days");
        this.scope = o.getObjectAs("scope", Scope::new);
    }
}
