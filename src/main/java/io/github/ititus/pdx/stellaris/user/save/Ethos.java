package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.ViewableList;
import io.github.ititus.pdx.util.collection.ViewableUnmodifiableArrayList;

import java.util.Collection;
import java.util.List;

public class Ethos {

    private final ViewableList<String> ethics;

    public Ethos(PdxScriptObject o) {
        this.ethics = o.getImplicitList("ethic").getAsStringList();
    }

    public Ethos(Collection<String> ethics) {
        this.ethics = new ViewableUnmodifiableArrayList<>(ethics);
    }

    public List<String> getEthics() {
        return ethics.getView();
    }
}
