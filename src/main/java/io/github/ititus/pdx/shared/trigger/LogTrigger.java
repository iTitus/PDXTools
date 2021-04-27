package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;

public class LogTrigger extends Trigger {

    public final String text;

    public LogTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        this.text = s.expectValue().expectString();
    }

    @Override
    public boolean evaluate(Scope scope) {
        System.out.println("log trigger: " + text);
        return true;
    }

    @Override
    public ImmutableList<String> localise(String language, int indent) {
        return Lists.immutable.of("log=" + text);
    }
}
