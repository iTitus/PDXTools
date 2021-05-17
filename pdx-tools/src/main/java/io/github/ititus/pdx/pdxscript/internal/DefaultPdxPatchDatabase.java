package io.github.ititus.pdx.pdxscript.internal;

import com.github.difflib.unifieddiff.UnifiedDiff;
import com.github.difflib.unifieddiff.UnifiedDiffReader;
import io.github.ititus.pdx.pdxscript.PdxPatch;
import io.github.ititus.pdx.pdxscript.PdxPatchDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultPdxPatchDatabase implements PdxPatchDatabase {

    public static final DefaultPdxPatchDatabase INSTANCE = new DefaultPdxPatchDatabase();

    private final Map<Path, PdxPatchImpl> patches;

    private DefaultPdxPatchDatabase() {
        this.patches = loadPatches();
    }

    private static Map<Path, PdxPatchImpl> loadPatches() {
        Map<Path, PdxPatchImpl> map = new HashMap<>();
        ModuleReference module = ModuleLayer.boot().configuration().findModule(DefaultPdxPatchDatabase.class.getModule().getName()).orElseThrow().reference();
        try (ModuleReader reader = module.open()) {
            reader.list()
                    .filter(s -> s.startsWith("patches/") && s.endsWith(".patch"))
                    .flatMap(s -> {
                        UnifiedDiff diff;
                        try (InputStream is = reader.open(s).orElseThrow()) {
                            diff = UnifiedDiffReader.parseUnifiedDiff(is);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }

                        return diff.getFiles().stream();
                    })
                    .forEach(d -> map
                            .computeIfAbsent(
                                    Path.of(d.getFromFile()),
                                    k -> new PdxPatchImpl(d.getFromFile())
                            )
                            .addPatch(d.getPatch())
                    );
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return Map.copyOf(map);
    }

    @Override
    public Optional<PdxPatch> findPatch(Path scriptFile) {
        Path p;
        try {
            p = scriptFile.toRealPath();
        } catch (IOException e) {
            throw new UncheckedIOException("unable to normalize given path", e);
        }

        for (Map.Entry<Path, PdxPatchImpl> entry : patches.entrySet()) {
            if (p.endsWith(entry.getKey())) {
                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }
}