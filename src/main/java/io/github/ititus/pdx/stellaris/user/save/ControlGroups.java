package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.multimap.ImmutableMultimap;

public class ControlGroups {

    private final ImmutableMultimap<Integer, Property> controlGroups;

    public ControlGroups(PdxScriptObject o) {
        this.controlGroups = o.getAsMultimap(Integer::valueOf, Property::new);
    }

    public ControlGroups(ImmutableMultimap<Integer, Property> controlGroups) {
        this.controlGroups = controlGroups;
    }

    public ImmutableMultimap<Integer, Property> getControlGroups() {
        return controlGroups;
    }

}
