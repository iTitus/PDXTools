package io.github.ititus.pdx.stellaris.game.common;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IOUtil;
import io.github.ititus.pdx.util.io.IPathFilter;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

public class PlanetClasses {

    private static final ImmutableSet<String> BLACKLIST = Sets.immutable.of();
    private static final IPathFilter FILTER = new FileExtensionFilter("txt");

    public final PdxScriptObject planetClasses;

    public PlanetClasses(Path dir) {
        Path[] files;
        try (Stream<Path> stream = Files.list(dir)) {
            files = stream
                    .filter(Objects::nonNull)
                    .filter(Files::isRegularFile)
                    .filter(FILTER)
                    .filter(p -> !BLACKLIST.contains(p.getFileName().toString()))
                    .sorted(IOUtil.asciibetical(dir))
                    .toArray(Path[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        this.planetClasses = PdxScriptParser.parse(files).expectObject();
    }
}
