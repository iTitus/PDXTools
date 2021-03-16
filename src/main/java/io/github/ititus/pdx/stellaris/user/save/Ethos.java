package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class Ethos {

    public final ImmutableList<String> ethics;

    public Ethos(PdxScriptObject o) {
        this.ethics = o.getImplicitListAsStringList("ethic");
    }
}
