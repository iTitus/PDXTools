package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;

public class DummyTrigger extends Trigger {

    public final String name;
    public final IPdxScript s;

    public DummyTrigger(String name, Triggers triggers, IPdxScript s) {
        super(triggers);
        this.name = name;
        this.s = s;
    }

    public static TriggerFactory factory(String name) {
        return (triggers, s) -> new DummyTrigger(name, triggers, s);
    }

    @Override
    public boolean evaluate(Scope scope) {
        throw new UnsupportedOperationException();
    }
}
