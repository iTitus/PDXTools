package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;

import java.util.function.Function;
import java.util.function.Predicate;

public class Triggers {

    private final MutableMap<String, TriggerFactory> triggers;
    private final MutableMap<String, PdxScriptObject> scriptedTriggers;

    public Triggers() {
        this.triggers = Maps.mutable.empty();
        this.scriptedTriggers = Maps.mutable.empty();
    }

    public void addEngineTrigger(String name, TriggerFactory factory) {
        if (triggers.put(name, factory) != null) {
            throw new IllegalArgumentException("engine trigger " + name + " already exists");
        }
    }

    public void addScriptedTrigger(String name, PdxScriptObject scriptedTrigger) {
        if (scriptedTriggers.put(name, scriptedTrigger) != null) {
            throw new IllegalArgumentException("scripted trigger " + name + " already exists");
        }
    }

    public void addScriptedTriggers(IPdxScript s) {
        s.expectObject().getAsStringObjectMap(IPdxScript::expectObject).forEachKeyValue(this::addScriptedTrigger);
    }

    public TriggerFactory getFactory(String name) {
        TriggerFactory factory = triggers.get(name);
        if (factory != null) {
            return factory;
        }

        PdxScriptObject scriptedTrigger = scriptedTriggers.get(name);
        if (scriptedTrigger != null) {
            return (triggers, s) -> new ScriptedTrigger(triggers, name, scriptedTrigger, s);
        }

        throw new RuntimeException("unknown trigger " + name);
    }

    public ImmutableList<Trigger> create(IPdxScript s) {
        return create(s.expectObject(), null);
    }

    public Trigger createOne(IPdxScript s) {
        return create(s).getOnly();
    }

    public ImmutableList<Trigger> create(IPdxScript s, Predicate<String> keyFilter) {
        return create(s.expectObject(), keyFilter);
    }

    public Trigger createOne(IPdxScript s, Predicate<String> keyFilter) {
        return create(s, keyFilter).getOnly();
    }

    public ImmutableList<Trigger> create(PdxScriptObject o) {
        return create(o, null);
    }

    public Trigger createOne(PdxScriptObject o) {
        return create(o).getOnly();
    }

    public ImmutableList<Trigger> create(PdxScriptObject o, Predicate<String> keyFilter) {
        MutableList<Trigger> triggers = Lists.mutable.empty();
        o.getAsStringObjectMap(keyFilter, Function.identity())
                .forEachKeyValue((k, v) -> {
                    TriggerFactory factory = getFactory(k);
                    if (factory == null) {
                        throw new RuntimeException("unknown trigger " + k + " with value " + v);
                    }

                    triggers.addAllIterable(v.expectImplicitList().getAsList(s -> factory.create(this, s)));
                });
        return triggers.toImmutable();
    }
}
