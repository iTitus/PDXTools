package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class AuraModifier {

    private final Map<String, Double> modifiers;

    public AuraModifier(PdxScriptObject o) {
        this.modifiers = o.getAsMap(Function.identity(), PdxScriptObject.nullOrDouble());
    }

    public AuraModifier(Map<String, Double> modifiers) {
        this.modifiers = new HashMap<>(modifiers);
    }

    public Map<String, Double> getModifiers() {
        return Collections.unmodifiableMap(modifiers);
    }
}
