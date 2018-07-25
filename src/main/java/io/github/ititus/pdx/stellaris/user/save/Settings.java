package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

public class Settings {

    private final Map<String, Boolean> settings;

    public Settings(PdxScriptObject o) {
        this.settings = o.getAsMap(Function.identity(), PdxConstants.NULL_OR_BOOLEAN);
    }

    public Settings(Map<String, Boolean> settings) {
        this.settings = Collections.unmodifiableMap(settings);
    }

    public Map<String, Boolean> getSettings() {
        return Collections.unmodifiableMap(settings);
    }
}
