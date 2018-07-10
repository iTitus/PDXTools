package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Ethos {

    private final List<String> ethics;

    public Ethos(PdxScriptObject o) {
        this.ethics = o.getImplicitList("ethic").getAsStringList();
    }

    public Ethos(Collection<String> ethics) {
        this.ethics = new ArrayList<>(ethics);
    }

    public List<String> getEthics() {
        return Collections.unmodifiableList(ethics);
    }
}
