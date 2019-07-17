package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptValue;
import io.github.ititus.pdx.util.Deduplicator;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.primitive.ImmutableObjectIntMap;

import java.util.Objects;

public class Flags {

    private static final Deduplicator<Flags> DEDUPLICATOR = new Deduplicator<>(f -> f.getComplexFlags().isEmpty());

    private final ImmutableObjectIntMap<String> intFlags;
    private final ImmutableMap<String, FlagData> complexFlags;

    private Flags(PdxScriptObject o) {
        this.intFlags = o.getAsObjectIntMap(s -> o.get(s) instanceof PdxScriptValue ? s : null);
        this.complexFlags = o.getAsMap(s -> o.get(s) instanceof PdxScriptObject ? s : null, PdxScriptObject.objectOrNull(FlagData::new));
    }

    private Flags(ImmutableObjectIntMap<String> intFlags, ImmutableMap<String, FlagData> complexFlags) {
        this.intFlags = intFlags;
        this.complexFlags = complexFlags;
    }

    public static Flags of(PdxScriptObject o) {
        return DEDUPLICATOR.deduplicate(new Flags(o));
    }

    public static Flags of(ImmutableObjectIntMap<String> intFlags, ImmutableMap<String, FlagData> complexFlags) {
        return DEDUPLICATOR.deduplicate(new Flags(intFlags, complexFlags));
    }

    public ImmutableObjectIntMap<String> getIntFlags() {
        return intFlags;
    }

    public ImmutableMap<String, FlagData> getComplexFlags() {
        return complexFlags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Flags)) {
            return false;
        }
        Flags flags = (Flags) o;
        return Objects.equals(intFlags, flags.intFlags) && Objects.equals(complexFlags, flags.complexFlags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(intFlags, complexFlags);
    }
}
