package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;


public class RelationsManager {

    private final ImmutableList<Relation> relations;

    public RelationsManager(PdxScriptObject o) {
        this.relations = o.getImplicitList("relation").getAsList(Relation::new);
    }

    public RelationsManager(ImmutableList<Relation> relations) {
        this.relations = relations;
    }

    public ImmutableList<Relation> getRelations() {
        return relations;
    }
}
