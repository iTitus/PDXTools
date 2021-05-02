package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

import java.util.function.Predicate;

public class CustomTooltipSuccessTrigger extends TriggerBasedTrigger {

    private static final Predicate<String> FILTER = s -> !"text".equals(s);

    public final String text;

    public CustomTooltipSuccessTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s, FILTER);
        this.text = s.expectObject().getString("text");
    }

    @Override
    public boolean evaluate(Scope scope) {
        return evaluateAnd(scope, children);
    }

    @Override
    protected ImmutableList<String> localise(String language, int indent) {
        MutableList<String> list = Lists.mutable.of("custom_tooltip_success:");
        list.add(indent(indent + 1) + " - text: " + localisation.translate(language, text));
        list.add(indent(indent + 1) + " - triggers:");
        localiseChildren(list, localisation, language, indent + 2);
        return list.toImmutable();
    }
}
