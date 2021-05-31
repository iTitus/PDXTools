package io.github.ititus.pdx.pdxscript.internal;

import com.github.difflib.patch.Patch;
import com.github.difflib.patch.PatchFailedException;
import io.github.ititus.pdx.pdxscript.PdxPatch;

import java.util.ArrayList;
import java.util.List;

public class PdxPatchImpl implements PdxPatch {

    private final String path;
    private final List<Patch<String>> patches;

    public PdxPatchImpl(String path) {
        this.path = path;
        this.patches = new ArrayList<>();
    }

    public void addPatch(Patch<String> patch) {
        patches.add(patch);
    }

    @Override
    public List<String> apply(List<String> lines) {
        List<PatchFailedException> suppressed = new ArrayList<>();

        for (Patch<String> patch : patches) {
            try {
                return patch.applyTo(lines);
            } catch (PatchFailedException e) {
                suppressed.add(e);
            }
        }

        RuntimeException e = new RuntimeException("unable to patch pdx script file " + path);
        for (PatchFailedException s : suppressed) {
            e.addSuppressed(s);
        }

        throw e;
    }
}
