package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;
import org.eclipse.collections.api.list.ImmutableList;

import java.util.Objects;

public class Ethos {

    private static final Deduplicator<Ethos> DEDUPLICATOR = new Deduplicator<>();

    private final ImmutableList<String> ethics;

    private Ethos(PdxScriptObject o) {
        this.ethics = o.getImplicitList("ethic").getAsStringList();
    }

    private Ethos(ImmutableList<String> ethics) {
        this.ethics = ethics;
    }

    public static Ethos of(PdxScriptObject o) {
        return DEDUPLICATOR.deduplicate(new Ethos(o));
    }

    public static Ethos of(ImmutableList<String> ethics) {
        return DEDUPLICATOR.deduplicate(new Ethos(ethics));
    }

    public ImmutableList<String> getEthics() {
        return ethics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ethos)) {
            return false;
        }
        return Objects.equals(ethics, ((Ethos) o).ethics);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ethics);
    }
}
