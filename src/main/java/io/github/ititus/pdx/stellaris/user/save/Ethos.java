package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Ethos {

    private final List<String> ethics;

    public Ethos(PdxScriptObject o) {
        IPdxScript s = o.get("ethic");
        if (s instanceof PdxScriptList) {
            this.ethics = ((PdxScriptList) s).getAsStringList();
            o.use("ethic", PdxConstants.LIST);
        } else {
            this.ethics = new ArrayList<>(Collections.singleton((String) ((PdxScriptValue) s).getValue()));
            o.use("ethic", PdxConstants.STRING);
        }
    }

    public Ethos(Collection<String> ethics) {
        this.ethics = new ArrayList<>(ethics);
    }

    public List<String> getEthics() {
        return Collections.unmodifiableList(ethics);
    }
}
