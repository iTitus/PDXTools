package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableObjectIntMap;

public class EventChain {

    public final String eventChain;
    public final Scope scope;
    public final ImmutableObjectIntMap<String> counter;

    public EventChain(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.eventChain = o.getString("event_chain");
        this.scope = o.getObjectAs("scope", Scope::new);
        this.counter = o.getObjectAsEmptyOrStringIntMap("counter");
    }
}
