package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class RelationsManager {

    public final ImmutableList<Relation> relations;

    public RelationsManager(PdxScriptObject o) {
        this.relations = o.getImplicitListAsList("relation", Relation::new);
    }
}
