package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptValue;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Flags {

    private final Map<String, Integer> intFlags;
    private final Map<String, FlagData> complexFlags;

    public Flags(PdxScriptObject o) {
        this.intFlags = o.getAsMap(Function.identity(), s -> {
            if (s instanceof PdxScriptValue) {
                PdxScriptValue v = (PdxScriptValue) s;
                Object i = v.getValue();
                if (i instanceof Integer) {
                    return (Integer) i;
                }
            }
            return null;
        });
        this.complexFlags = o.getAsMap(Function.identity(), PdxScriptObject.objectOrNull(FlagData::new));
    }

    public Flags(Map<String, Integer> intFlags, Map<String, FlagData> complexFlags) {
        this.intFlags = new HashMap<>(intFlags);
        this.complexFlags = new HashMap<>(complexFlags);
    }
}
