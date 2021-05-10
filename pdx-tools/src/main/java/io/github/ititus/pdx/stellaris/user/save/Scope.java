package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableLongList;
import org.eclipse.collections.api.map.primitive.ImmutableObjectDoubleMap;

public class Scope {

    public final int id;
    public final String type;
    public final ImmutableLongList random;
    public final ImmutableList<SavedEventTarget> savedEventTarget;
    public final Scope root;
    public final Scope from;
    public final Scope prev;
    public final ImmutableObjectDoubleMap<String> variables;

    public Scope(PdxScriptObject o) {
        this.type = o.getNullOrString("type");
        this.id = o.getUnsignedInt("id");
        this.random = o.getListAsLongList("random");
        this.root = o.getObjectAsNullOr("root", Scope::new);
        this.from = o.getObjectAsNullOr("from", Scope::new);
        this.prev = o.getObjectAsNullOr("prev", Scope::new);
        this.savedEventTarget = o.getImplicitListAsList("saved_event_target", SavedEventTarget::new);
        this.variables = o.getObjectAsEmptyOrStringDoubleMap("variables");
    }
}
