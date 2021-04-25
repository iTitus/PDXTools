package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.list.ImmutableList;

public class ScriptedTrigger extends Trigger {

    public final String name;
    public final boolean expected;
    public final ImmutableList<Trigger> triggers;

    public ScriptedTrigger(Triggers triggers, String name, PdxScriptObject scriptedTrigger, IPdxScript s) {
        super(triggers);
        this.name = name;

        PdxScriptObject parameters;
        if (s instanceof PdxScriptObject) {
            this.expected = true;
            parameters = s.expectObject();
        } else {
            this.expected = s.expectValue().expectBoolean();
            parameters = null;
        }

        if (parameters != null) {
            throw new UnsupportedOperationException();
        }

        this.triggers = create(scriptedTrigger);
    }

    @Override
    public boolean evaluate(Scope scope) {
        return evaluateAnd(scope, triggers) == expected;
    }
}
