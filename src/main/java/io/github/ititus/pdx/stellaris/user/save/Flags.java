package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.primitive.ImmutableObjectIntMap;

import java.util.function.Function;

public class Flags {

    private final ImmutableObjectIntMap<String> intFlags;
    private final ImmutableMap<String, FlagData> complexFlags;

    public Flags(PdxScriptObject o) {
        this.intFlags = o.getAsObjectIntMap(Function.identity(), PdxConstants.TO_INT);
        this.complexFlags = o.getAsMap(Function.identity(), PdxScriptObject.objectOrNull(FlagData::new));
    }

    public Flags(ImmutableObjectIntMap<String> intFlags, ImmutableMap<String, FlagData> complexFlags) {
        this.intFlags = intFlags;
        this.complexFlags = complexFlags;
    }

    public ImmutableObjectIntMap<String> getIntFlags() {
        return intFlags;
    }

    public ImmutableMap<String, FlagData> getComplexFlags() {
        return complexFlags;
    }
}
