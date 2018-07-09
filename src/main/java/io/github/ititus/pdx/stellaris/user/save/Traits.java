package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.*;
import io.github.ititus.pdx.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Traits {

    private final List<String> traits;

    public Traits(PdxScriptObject o) {
        IPdxScript s = o.get("trait");
        if (s instanceof PdxScriptList) {
            this.traits = ((PdxScriptList) s).getAsStringList();
            o.use("trait", PdxConstants.LIST);
        } else if (s == null) {
            this.traits = CollectionUtil.listOf();
        } else {
            this.traits = CollectionUtil.listOf((String) ((PdxScriptValue) s).getValue());
            o.use("trait", PdxConstants.STRING);
        }
    }

    public Traits(Collection<String> traits) {
        this.traits = new ArrayList<>(traits);
    }

    public List<String> getTraits() {
        return Collections.unmodifiableList(traits);
    }
}
