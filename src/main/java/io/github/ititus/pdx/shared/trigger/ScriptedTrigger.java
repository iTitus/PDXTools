package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public class ScriptedTrigger extends TriggerBasedTrigger {

    public final String name;
    public final boolean expected;

    private ScriptedTrigger(Triggers triggers, PdxScriptObject scriptedTrigger, String name, boolean expected) {
        super(triggers, scriptedTrigger);
        this.name = name;
        this.expected = expected;
    }

    public static ScriptedTrigger of(Triggers triggers, String name, PdxScriptObject scriptedTrigger, IPdxScript s) {
        boolean expected;
        PdxScriptObject parameters;
        if (s instanceof PdxScriptObject) {
            expected = true;
            parameters = s.expectObject();
        } else {
            expected = s.expectValue().expectBoolean();
            parameters = null;
        }

        if (parameters != null) {
            throw new UnsupportedOperationException();
        }

        return new ScriptedTrigger(triggers, scriptedTrigger, name, expected);
    }

    @Override
    public boolean evaluate(Scope scope) {
        return evaluateAnd(scope, children) == expected;
    }

    @Override
    protected ImmutableList<String> localise(String language, int indent) {
        MutableList<String> list = Lists.mutable.of("scripted_trigger " + name + "=" + expected + ", where " + name + " means:");
        localiseChildren(list, localisation, language, indent + 1);
        return list.toImmutable();
    }
}
