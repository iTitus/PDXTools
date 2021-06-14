package io.github.ititus.pdx.stellaris.game.gfx;

import io.github.ititus.io.FileExtensionFilter;
import io.github.ititus.io.PathFilter;
import io.github.ititus.io.PathUtil;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.shared.ProgressMessageUpdater;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Gfx {

    private static final ImmutableSet<String> BLACKLIST = Sets.immutable.of(
            // Not PDXScript
            "arrows", "cursors", "event_pictures", "fonts", "FX", "interface", "keyicons", "loadingscreens", "map",
            "portraits/city_sets", "portraits/environments", "portraits/leaders", "portraits/misc",
            "projectiles/planet_destruction/example.txt", "ugc"
    );
    // "advisorwindow" <- *.txt
    // "lights" <- *.asset
    // "models" <- *.asset
    // "particles" <- *.asset, *.gfx
    // "pingmap" <- *.txt
    // "portraits/asset_selectors" <- *.txt, "portraits/portraits" <- *.txt
    // "projectiles" <- *.txt
    // "shipview" <- *.txt
    // "worldgfx" <- *.txt
    private static final PathFilter FILTER = new FileExtensionFilter("asset", "gfx", "txt");
    private static final PathFilter ASSET = new FileExtensionFilter("asset");

    public final ImmutableList<Light> lights;
    public final ImmutableList<Animation> animations;
    public final ImmutableList<Entity> entities;
    public final ImmutableList<Particle> particles;

    private final Path gfxDir;

    public Gfx(Path installDir, Path gfxDir, int index, ProgressMessageUpdater progressMessageUpdater) {
        if (installDir == null || !Files.isDirectory(installDir) || gfxDir == null || !Files.isDirectory(gfxDir)) {
            throw new IllegalArgumentException();
        }

        this.gfxDir = gfxDir;

        // FIXME: disabled for performance
        // Path[] files = findFiles(ASSET);
        PdxScriptObject o = PdxScriptObject.builder().build(); // PdxScriptParser.parseWithDefaultPatches(files).expectObject();

        this.lights = o.getImplicitListAsList("light", Light::new);
        this.animations = o.getImplicitListAsList("animation", Animation::new);
        this.entities = o.getImplicitListAsList("entity", Entity::new);
        this.particles = o.getImplicitListAsList("particle", Particle::new);
    }

    private Path[] findFiles(Predicate<? super Path> filter) {
        try (Stream<Path> stream = Files.walk(gfxDir)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(filter)
                    .filter(p -> {
                        Path r = gfxDir.relativize(p);
                        return BLACKLIST.stream().noneMatch(r::startsWith);
                    })
                    .sorted(PathUtil.ASCIIBETICAL)
                    .toArray(Path[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Path getGfxDir() {
        return gfxDir;
    }
}
