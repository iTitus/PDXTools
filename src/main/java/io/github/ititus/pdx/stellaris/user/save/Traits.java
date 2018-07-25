package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.ViewableArrayList;
import io.github.ititus.pdx.util.collection.ViewableList;

import java.util.Collection;
import java.util.List;

public class Traits {

    private final ViewableList<String> traits;

    public Traits(PdxScriptObject o) {
        this.traits = o.getImplicitList("trait").getAsStringList();
    }

    public Traits(Collection<String> traits) {
        this.traits = new ViewableArrayList<>(traits);
    }

    public List<String> getTraits() {
        return traits.getView();
    }
}
