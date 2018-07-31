package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.primitive.ImmutableObjectBooleanMap;

import java.util.function.Function;

public class Settings {

    private final ImmutableObjectBooleanMap<String> settings;

    public Settings(PdxScriptObject o) {
        this.settings = o.getAsObjectBooleanMap(Function.identity(), PdxConstants.TO_BOOLEAN);
    }

    public Settings(ImmutableObjectBooleanMap<String> settings) {
        this.settings = settings;
    }

    public ImmutableObjectBooleanMap<String> getSettings() {
        return settings;
    }
}
