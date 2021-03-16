package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class Events {

    public final int nextSpecialProjectId;
    public final ImmutableIntList anomalies;
    public final ImmutableList<PoI> pois;
    public final ImmutableList<SpecialProject> specialProjects;
    public final ImmutableList<EventChain> eventChains;

    public Events(PdxScriptObject o) {
        this.pois = o.getListAsEmptyOrList("poi", PoI::new);
        this.specialProjects = o.getImplicitListAsList("special_project", SpecialProject::new);
        this.nextSpecialProjectId = o.getInt("next_special_project_id", -1);
        this.eventChains = o.getImplicitListAsList("event_chain", EventChain::new);
        this.anomalies = o.getListAsEmptyOrIntList("anomalies");
    }
}
