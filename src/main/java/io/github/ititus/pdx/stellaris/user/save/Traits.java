package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.Objects;

public class Traits {

    private static final Deduplicator<Traits> DEDUPLICATOR = new Deduplicator<>();

    private final ImmutableList<String> traits;

    private Traits(PdxScriptObject o) {
        this.traits = o.getImplicitList("trait").getAsStringList();
    }

    private Traits(ImmutableList<String> traits) {
        this.traits = traits;
    }

    public static Traits of(PdxScriptObject o) {
        return DEDUPLICATOR.deduplicate(new Traits(o));
    }

    public static Traits of(ImmutableList<String> traits) {
        return DEDUPLICATOR.deduplicate(new Traits(traits));
    }

    public ImmutableList<String> getTraits() {
        return traits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Traits)) {
            return false;
        }
        return Objects.equals(traits, ((Traits) o).traits);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(traits);
    }
}
