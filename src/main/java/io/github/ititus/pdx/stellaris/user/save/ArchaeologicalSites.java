package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class ArchaeologicalSites {

    private final ImmutableIntObjectMap<ArchaeologicalSite> archaeologicalSites;

    public ArchaeologicalSites(PdxScriptObject o) {
        this.archaeologicalSites = o.getObject("sites").getAsIntObjectMap(ArchaeologicalSite::new);
    }

    public ArchaeologicalSites(ImmutableIntObjectMap<ArchaeologicalSite> archaeologicalSites) {
        this.archaeologicalSites = archaeologicalSites;
    }

    public ImmutableIntObjectMap<ArchaeologicalSite> getArchaeologicalSites() {
        return archaeologicalSites;
    }
}
