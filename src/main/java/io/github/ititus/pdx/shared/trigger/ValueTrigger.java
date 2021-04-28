package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

public class ValueTrigger extends Trigger {

    public final String name;
    public final PdxScriptValue v;

    public ValueTrigger(String name, Triggers triggers, IPdxScript s) {
        super(triggers);
        this.name = name;
        this.v = s.expectValue();
    }

    public static TriggerFactory factory(String name) {
        return (triggers, s) -> new ValueTrigger(name, triggers, s);
    }

    @Override
    public boolean evaluate(Scope scope) {
        return scope.evaluateValueTrigger(name, v);
    }

    @Override
    public ImmutableList<String> localise(String language, int indent) {
        return Lists.immutable.of(v.toPdxScript(0, false, name));
    }
}
