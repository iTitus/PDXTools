package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

public class Scope {

    private final int id;
    private final String type;
    private final ImmutableIntList random;
    private final ImmutableList<SavedEventTarget> savedEventTarget;
    private final Scope root, from, prev;
    private final Variables variables;

    public Scope(PdxScriptObject o) {
        this.type = o.getString("type");
        this.id = o.getUnsignedInt("id");
        this.random = o.getList("random").getAsIntList();
        PdxScriptObject o1 = o.getObject("root");
        this.root = o1 != null ? o1.getAs(Scope::new) : null;
        o1 = o.getObject("from");
        this.from = o1 != null ? o1.getAs(Scope::new) : null;
        o1 = o.getObject("prev");
        this.prev = o1 != null ? o1.getAs(Scope::new) : null;
        this.savedEventTarget = o.getImplicitList("saved_event_target").getAsList(SavedEventTarget::new);
        o1 = o.getObject("variables");
        this.variables = o1 != null ? o1.getAs(Variables::new) : null;
    }

    public Scope(int id, String type, ImmutableIntList random, ImmutableList<SavedEventTarget> savedEventTarget,
                 Scope root, Scope from, Scope prev, Variables variables) {
        this.id = id;
        this.type = type;
        this.random = random;
        this.savedEventTarget = savedEventTarget;
        this.root = root;
        this.from = from;
        this.prev = prev;
        this.variables = variables;
    }

    public int getId() {
        return id;
    }

    public ImmutableIntList getRandom() {
        return random;
    }

    public ImmutableList<SavedEventTarget> getSavedEventTarget() {
        return savedEventTarget;
    }

    public String getType() {
        return type;
    }

    public Scope getRoot() {
        return root;
    }

    public Scope getFrom() {
        return from;
    }

    public Scope getPrev() {
        return prev;
    }

    public Variables getVariables() {
        return variables;
    }
}
