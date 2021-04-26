package io.github.ititus.pdx.shared.trigger;

import io.github.ititus.pdx.pdxlocalisation.PdxLocalisation;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

import java.util.function.Predicate;

public abstract class TriggerBasedTrigger extends Trigger {

    public final ImmutableList<Trigger> children;

    protected TriggerBasedTrigger(Triggers triggers, IPdxScript s) {
        this(triggers, s, null);
    }

    protected TriggerBasedTrigger(Triggers triggers, IPdxScript s, Predicate<String> keyFilter) {
        super(triggers);
        this.children = create(s, keyFilter);
    }

    protected TriggerBasedTrigger(Triggers triggers, ImmutableList<Trigger> children) {
        super(triggers);
        this.children = children;
    }

    protected void localiseChildren(MutableList<String> list, PdxLocalisation localisation, String language, int indent) {
        list.addAllIterable(localise(localisation, language, indent, children));
    }
}
