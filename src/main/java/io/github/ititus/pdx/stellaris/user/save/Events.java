package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.primitive.IntLists;

public class Events {

    private final int nextSpecialProjectId;
    private final ImmutableIntList anomalies;
    private final ImmutableList<PoI> pois;
    private final ImmutableList<SpecialProject> specialProjects;
    private final ImmutableList<EventChain> eventChains;

    public Events(PdxScriptObject o) {
        PdxScriptList l = o.getList("poi");
        this.pois = l != null ? l.getAsList(PoI::new) : Lists.immutable.empty();
        this.specialProjects = o.getImplicitList("special_project").getAsList(SpecialProject::new);
        this.nextSpecialProjectId = o.getInt("next_special_project_id");
        this.eventChains = o.getImplicitList("event_chain").getAsList(EventChain::new);
        l = o.getList("anomalies");
        this.anomalies = l != null ? l.getAsIntList() : IntLists.immutable.empty();
    }

    public Events(int nextSpecialProjectId, ImmutableIntList anomalies, ImmutableList<PoI> pois, ImmutableList<SpecialProject> specialProjects, ImmutableList<EventChain> eventChains) {
        this.nextSpecialProjectId = nextSpecialProjectId;
        this.anomalies = anomalies;
        this.pois = pois;
        this.specialProjects = specialProjects;
        this.eventChains = eventChains;
    }

    public int getNextSpecialProjectId() {
        return nextSpecialProjectId;
    }

    public ImmutableIntList getAnomalies() {
        return anomalies;
    }

    public ImmutableList<PoI> getPois() {
        return pois;
    }

    public ImmutableList<SpecialProject> getSpecialProjects() {
        return specialProjects;
    }

    public ImmutableList<EventChain> getEventChains() {
        return eventChains;
    }
}
