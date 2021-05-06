package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

import java.util.function.Predicate;

public class CustomTooltipTrigger extends TriggerBasedTrigger {

    private static final Predicate<String> FILTER = s -> !"text".equals(s) && !"fail_text".equals(s) && !"success_text".equals(s);

    public final String failText;
    public final String successText;

    public CustomTooltipTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s, FILTER);
        PdxScriptObject o = s.expectObject();
        String fallbackText = o.getString("text", null);
        this.failText = o.getString("fail_text", fallbackText);
        this.successText = o.getString("success_text", fallbackText);
        if (this.failText == null || this.successText == null) {
            throw new IllegalArgumentException("needs both fail_text and success_text");
        }
    }

    @Override
    public boolean evaluate(Scope scope) {
        return evaluateAnd(scope, children);
    }

    @Override
    protected ImmutableList<String> localise(String language, int indent) {
        MutableList<String> list = Lists.mutable.of("custom_tooltip:");
        list.add(indent(indent + 1) + " - fail_text: " + localisation.translate(language, failText));
        list.add(indent(indent + 1) + " - success_text: " + localisation.translate(language, successText));
        list.add(indent(indent + 1) + " - triggers:");
        localiseChildren(list, language, indent + 2);
        return list.toImmutable();
    }
}
