package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;

public class ArchaeologicalSites {

    public final ImmutableIntObjectMap<ArchaeologicalSite> archaeologicalSites;

    public ArchaeologicalSites(PdxScriptObject o) {
        this.archaeologicalSites = o.getObjectAsIntObjectMap("sites", ArchaeologicalSite::new);
    }
}
