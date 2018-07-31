package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class Ethos {

    private final ImmutableList<String> ethics;

    public Ethos(PdxScriptObject o) {
        this.ethics = o.getImplicitList("ethic").getAsStringList();
    }

    public Ethos(ImmutableList<String> ethics) {
        this.ethics = ethics;
    }

    public ImmutableList<String> getEthics() {
        return ethics;
    }
}
