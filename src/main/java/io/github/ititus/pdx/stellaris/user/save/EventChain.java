package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class EventChain {

    private final String eventChain;
    private final Scope scope;
    private final Flags counter;

    public EventChain(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.eventChain = o.getString("event_chain");
        this.scope = o.getObject("scope").getAs(Scope::new);
        PdxScriptObject o1 = o.getObject("counter");
        this.counter = o1 != null ? o1.getAs(Flags::of) : null;
    }

    public EventChain(String eventChain, Scope scope, Flags counter) {
        this.eventChain = eventChain;
        this.scope = scope;
        this.counter = counter;
    }

    public String getEventChain() {
        return eventChain;
    }

    public Scope getScope() {
        return scope;
    }

    public Flags getCounter() {
        return counter;
    }
}
