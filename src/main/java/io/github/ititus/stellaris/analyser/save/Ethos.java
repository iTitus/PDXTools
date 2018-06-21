package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.IPdxScript;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptList;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptValue;

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
        } else {
            this.ethics = new ArrayList<>(Collections.singleton((String) ((PdxScriptValue) s).getValue()));
        }
    }

    public Ethos(Collection<String> ethics) {
        this.ethics = new ArrayList<>(ethics);
    }

    public List<String> getEthics() {
        return Collections.unmodifiableList(ethics);
    }
}
