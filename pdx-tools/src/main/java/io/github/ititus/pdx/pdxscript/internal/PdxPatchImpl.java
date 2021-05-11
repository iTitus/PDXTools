package io.github.ititus.pdx.pdxscript.internal;

import com.github.difflib.patch.Patch;
import com.github.difflib.patch.PatchFailedException;
import io.github.ititus.pdx.pdxscript.PdxPatch;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;

import java.util.List;

public class PdxPatchImpl implements PdxPatch {

    private final String path;
    private final MutableList<Patch<String>> patches;

    public PdxPatchImpl(String path) {
        this.path = path;
        this.patches = Lists.mutable.empty();
    }

    public void addPatch(Patch<String> patch) {
        patches.add(patch);
    }

    @Override
    public List<String> apply(List<String> lines) {
        MutableList<PatchFailedException> suppressed = Lists.mutable.empty();

        for (Patch<String> patch : patches) {
            try {
                return patch.applyTo(lines);
            } catch (PatchFailedException ignored) {
            }
        }

        RuntimeException e = new RuntimeException("unable to patch pdx script file " + path);
        suppressed.forEach(e::addSuppressed);
        throw e;
    }
}
