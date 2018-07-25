package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.CollectionUtil;
import io.github.ititus.pdx.util.collection.ViewableArrayList;
import io.github.ititus.pdx.util.collection.ViewableList;

import java.util.Collection;
import java.util.List;

public class UsedSpeciesClassAssets {

    private final String speciesClass;
    private final ViewableList<Integer> values;

    public UsedSpeciesClassAssets(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.speciesClass = o.getString("class");
        PdxScriptList l = o.getList("values");
        this.values = l != null ? l.getAsIntegerList() : CollectionUtil.viewableListOf();
    }

    public UsedSpeciesClassAssets(String speciesClass, Collection<Integer> values) {
        this.speciesClass = speciesClass;
        this.values = new ViewableArrayList<>(values);
    }

    public String getSpeciesClass() {
        return speciesClass;
    }

    public List<Integer> getValues() {
        return values.getView();
    }
}
