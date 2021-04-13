package io.github.ititus.pdx.stellaris.game.gfx;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IOUtil;
import io.github.ititus.pdx.util.io.IPathFilter;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

public class Gfx {

    private static final ImmutableSet<String> BLACKLIST = Sets.immutable.of(
            // Not PDXScript
            "fonts/arimo/LICENSE.txt",
            // FIXME: disabled for performance
            "advisorwindow", "lights", "particles", "pingmap", "portraits", "projectiles", "shipview", "worldgfx"
    );
    private static final IPathFilter TXT = new FileExtensionFilter("txt");
    private static final IPathFilter FILTER = new FileExtensionFilter("asset", "gfx", "txt");

    public final ImmutableList<Entity> entities;

    private final Path installDir;
    private final Path gfxDir;

    public Gfx(Path installDir, Path gfxDir, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (installDir == null || !Files.isDirectory(installDir) || gfxDir == null || !Files.isDirectory(gfxDir)) {
            throw new IllegalArgumentException();
        }

        this.installDir = installDir;
        this.gfxDir = gfxDir;

        Path[] files;
        try (Stream<Path> stream1 = Files.list(installDir.resolve(Path.of("common", "scripted_variables")));
             Stream<Path> stream2 = Files.walk(gfxDir)) {
            files = concat(
                    stream1
                            .filter(Objects::nonNull)
                            .filter(Files::isRegularFile)
                            .filter(TXT)
                            .sorted(IOUtil.ASCIIBETICAL),
                    stream2
                            .filter(Objects::nonNull)
                            .filter(Files::isRegularFile)
                            .filter(FILTER)
                            .filter(p -> {
                                Path r = gfxDir.relativize(p);
                                return BLACKLIST.stream().noneMatch(r::startsWith);
                            })
                            .sorted(IOUtil.ASCIIBETICAL)
            )
                    .toArray(Path[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        PdxScriptObject o = PdxScriptParser.parseWithDefaultPatches(files).expectObject();
        this.entities = o.getImplicitListAsList("entity", Entity::new);
    }

    public Path getInstallDir() {
        return installDir;
    }

    public Path getGfxDir() {
        return gfxDir;
    }
}
