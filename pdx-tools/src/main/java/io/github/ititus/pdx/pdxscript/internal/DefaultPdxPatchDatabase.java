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
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultPdxPatchDatabase implements PdxPatchDatabase {

    public static final DefaultPdxPatchDatabase INSTANCE = new DefaultPdxPatchDatabase();

    private final Map<String, PdxPatchImpl> patches;

    private DefaultPdxPatchDatabase() {
        this.patches = loadPatches();
    }

    private static Map<String, PdxPatchImpl> loadPatches() {
        Map<String, PdxPatchImpl> map = new LinkedHashMap<>();
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
                    .forEachOrdered(d -> map
                            .computeIfAbsent(
                                    d.getFromFile(),
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
    public Optional<PdxPatch> findPatch(URI scriptFile) {
        if (!scriptFile.isAbsolute() || scriptFile.getRawFragment() != null || scriptFile.getRawQuery() != null) {
            throw new IllegalArgumentException("illegal uri " + scriptFile);
        }

        String p;
        if ("file".equals(scriptFile.getScheme())) {
            p = scriptFile.getPath();
        } else if ("jar".equals(scriptFile.getScheme())) {
            p = scriptFile.getRawSchemeSpecificPart();
        } else {
            throw new IllegalArgumentException("illegal scheme for uri " + scriptFile);
        }

        for (Map.Entry<String, PdxPatchImpl> entry : patches.entrySet()) {
            if (p.endsWith(entry.getKey())) {
                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }
}
