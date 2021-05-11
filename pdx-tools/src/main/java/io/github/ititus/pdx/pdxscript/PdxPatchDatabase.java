package io.github.ititus.pdx.pdxscript;

import io.github.ititus.pdx.pdxscript.internal.DefaultPdxPatchDatabase;

import java.nio.file.Path;
import java.util.Optional;

public interface PdxPatchDatabase {

    PdxPatchDatabase DEFAULT = DefaultPdxPatchDatabase.INSTANCE;

    Optional<PdxPatch> findPatch(Path scriptFile);

}
