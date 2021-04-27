package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.tuple.Tuples;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class SwitchTrigger extends Trigger {

    public final String trigger;
    public final Map<String, Pair<Trigger, ImmutableList<Trigger>>> cases;
    public final ImmutableList<Trigger> defaultTriggers;

    public SwitchTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        PdxScriptObject o = s.expectObject();
        this.trigger = o.getString("trigger");
        Map<String, Pair<Trigger, ImmutableList<Trigger>>> cases = new LinkedHashMap<>();
        o.forEach((k, v) -> {
            if (!"trigger".equals(k) && !"default".equals(k)) {
                cases.put(k, Tuples.pair(
                        create(PdxScriptObject.builder().add(trigger, PdxScriptValue.of(v.getRelation(), k)).build()).getOnly(),
                        create(v)
                ));
            }
        });
        this.cases = Collections.unmodifiableMap(cases);
        this.defaultTriggers = o.getObjectAsNullOr("default", this::create);
    }

    @Override
    public boolean evaluate(Scope scope) {
        for (Map.Entry<String, Pair<Trigger, ImmutableList<Trigger>>> e : cases.entrySet()) {
            if (e.getValue().getOne().evaluate(scope)) {
                return evaluateAnd(scope, e.getValue().getTwo());
            }
        }

        return defaultTriggers != null && evaluateAnd(scope, defaultTriggers);
    }

    @Override
    public ImmutableList<String> localise(String language, int indent) {
        MutableList<String> list = Lists.mutable.empty();
        list.add("switch on " + trigger + ":");
        cases.forEach((v, t) -> {
            list.add(indent(indent + 1) + " - case:");
            list.add(indent(indent + 2) + " - condition:");
            list.addAllIterable(localise(language, indent + 3, t.getOne()));
            list.add(indent(indent + 2) + " - triggers:");
            list.addAllIterable(localise(language, indent + 3, t.getTwo()));
        });

        if (defaultTriggers != null) {
            list.add(indent(indent + 1) + " - default triggers:");
            list.addAllIterable(localise(language, indent + 2, defaultTriggers));
        }

        return list.toImmutable();
    }
}
