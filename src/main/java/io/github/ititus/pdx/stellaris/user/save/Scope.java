package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Arrays;

public class Scope {

    private final int id;
    private final int[] random;
    private final String type;
    private final Scope from;
    private final SavedEventTarget savedEventTarget;
    private final Variables variables;

    public Scope(PdxScriptObject o) {
        this.type = o.getString("type");
        this.id = o.getInt("id");
        this.random = o.getList("random").getAsIntArray();
        PdxScriptObject o1 = o.getObject("from");
        this.from = o1 != null ? o1.getAs(Scope::new) : null;
        o1 = o.getObject("saved_event_target");
        this.savedEventTarget = o1 != null ? o1.getAs(SavedEventTarget::new) : null;
        o1 = o.getObject("variables");
        this.variables = o1 != null ? o1.getAs(Variables::new) : null;
    }

    public Scope(int id, int[] random, String type, Scope from, SavedEventTarget savedEventTarget, Variables variables) {
        this.id = id;
        this.random = Arrays.copyOf(random, random.length);
        this.type = type;
        this.from = from;
        this.savedEventTarget = savedEventTarget;
        this.variables = variables;
    }

    public int getId() {
        return id;
    }

    public int[] getRandom() {
        return Arrays.copyOf(random, random.length);
    }

    public String getType() {
        return type;
    }

    public Scope getFrom() {
        return from;
    }

    public SavedEventTarget getSavedEventTarget() {
        return savedEventTarget;
    }

    public Variables getVariables() {
        return variables;
    }
}
