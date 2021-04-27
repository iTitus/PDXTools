package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.util.mutable.MutableBoolean;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;

import java.util.Locale;
import java.util.function.Predicate;

public class Triggers {

    private final StellarisGame game;
    private final MutableMap<String, TriggerFactory> triggers;
    private final MutableMap<String, PdxScriptObject> scriptedTriggers;

    public Triggers(StellarisGame game) {
        this.game = game;
        this.triggers = Maps.mutable.empty();
        this.scriptedTriggers = Maps.mutable.empty();
    }

    public void addEngineTrigger(String name, TriggerFactory factory) {
        name = name.toLowerCase(Locale.ROOT);
        if (triggers.put(name, factory) != null) {
            throw new IllegalArgumentException("engine trigger " + name + " already exists");
        }
    }

    public void addScriptedTrigger(String name, IPdxScript scriptedTrigger) {
        if (scriptedTriggers.put(name, scriptedTrigger.expectObject()) != null) {
            throw new IllegalArgumentException("scripted trigger " + name + " already exists");
        }
    }

    public void addScriptedTriggers(IPdxScript s) {
        s.expectObject().forEach(this::addScriptedTrigger);
    }

    public TriggerFactory getFactory(String name) {
        String lowerName = name.toLowerCase(Locale.ROOT);
        TriggerFactory factory = triggers.get(lowerName);
        if (factory != null) {
            return factory;
        }

        PdxScriptObject scriptedTrigger = scriptedTriggers.get(lowerName);
        if (scriptedTrigger != null) {
            return (triggers, s) -> ScriptedTrigger.of(triggers, lowerName, scriptedTrigger, s);
        }

        throw new RuntimeException("unknown trigger " + lowerName);
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
        MutableBoolean ifElseFound = new MutableBoolean();
        IfElseTrigger.Builder[] ifElseTriggerBuilder = new IfElseTrigger.Builder[1];
        o.forEach((k, v) -> {
            k = k.toLowerCase(Locale.ROOT);
            if (keyFilter != null && !keyFilter.test(k)) {
                return;
            }

            TriggerFactory factory = getFactory(k);
            if (factory == null) {
                throw new RuntimeException("unknown trigger " + k + " with value " + v);
            }

            switch (k) {
                case "if" -> {
                    if (ifElseFound.get()) {
                        throw new IllegalStateException();
                    }

                    ifElseFound.set(true);
                    ifElseTriggerBuilder[0] = IfElseTrigger.builder(this).addIf(v);
                }
                case "else_if" -> ifElseTriggerBuilder[0].addElseIf(v);
                case "else" -> ifElseTriggerBuilder[0].addElse(v);
                default -> {
                    if (ifElseFound.get()) {
                        triggers.add(ifElseTriggerBuilder[0].build());
                        ifElseTriggerBuilder[0] = null;
                    }

                    v.expectImplicitList().forEach(s -> triggers.add(factory.create(this, s)));
                }
            }
        });
        if (ifElseTriggerBuilder[0] != null) {
            triggers.add(ifElseTriggerBuilder[0].build());
        }

        return triggers.toImmutable();
    }

    public StellarisGame getGame() {
        return game;
    }
}
