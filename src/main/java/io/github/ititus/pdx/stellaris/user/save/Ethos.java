package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.collection.ViewableArrayList;
import io.github.ititus.pdx.util.collection.ViewableList;

import java.util.Collection;
import java.util.List;

public class Ethos {

    private final ViewableList<String> ethics;

    public Ethos(PdxScriptObject o) {
        this.ethics = o.getImplicitList("ethic").getAsStringList();
    }

    public Ethos(Collection<String> ethics) {
        this.ethics = new ViewableArrayList<>(ethics);
    }

    public List<String> getEthics() {
        return ethics.getView();
    }
}
