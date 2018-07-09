package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class DelayedEvent {

    private final int days;
    private final String event;
    private final Scope scope;

    public DelayedEvent(PdxScriptObject o) {
        this.event = o.getString("event");
        this.days = o.getInt("days");
        this.scope = o.getObject("scope").getAs(Scope::new);
    }

    public DelayedEvent(int days, String event, Scope scope) {
        this.days = days;
        this.event = event;
        this.scope = scope;
    }

    public int getDays() {
        return days;
    }

    public String getEvent() {
        return event;
    }

    public Scope getScope() {
        return scope;
    }
}
