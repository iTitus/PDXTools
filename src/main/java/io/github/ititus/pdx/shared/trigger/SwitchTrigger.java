package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.shared.scope.Scope;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class SwitchTrigger extends Trigger {

    public final String trigger;
    public final Map<String, ImmutableList<Trigger>> cases;
    public final ImmutableList<Trigger> defaultTriggers;

    public SwitchTrigger(Triggers triggers, IPdxScript s) {
        super(triggers);
        PdxScriptObject o = s.expectObject();
        this.trigger = o.getString("trigger");
        Map<String, ImmutableList<Trigger>> cases = new LinkedHashMap<>();
        o.forEach((k, v) -> {
            if (!"trigger".equals(k) && !"default".equals(k)) {
                cases.put(k, create(v));
            }
        });
        this.cases = Collections.unmodifiableMap(cases);
        this.defaultTriggers = o.getObjectAsNullOr("default", this::create);
    }

    @Override
    public boolean evaluate(Scope scope) {
        // scopes: all
        /*
        value = scope.getValue(trigger)
        foreach case, triggers:
            if case == scope: return evaluate(scope, triggers)
        else: return evaluate(scope, defaultTriggers)
        */
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableList<String> localise(PdxLocalisation localisation, String language, int indent) {
        MutableList<String> list = Lists.mutable.empty();
        list.add("switch on " + trigger + ":");
        cases.forEach((v, t) -> {
            list.add("case " + v + ":");
            list.addAllIterable(localise(localisation, language, indent + 1, t));
        });

        if (defaultTriggers != null) {
            list.add("default:");
            list.addAllIterable(localise(localisation, language, indent + 1, defaultTriggers));
        }

        return list.toImmutable();
    }
}
