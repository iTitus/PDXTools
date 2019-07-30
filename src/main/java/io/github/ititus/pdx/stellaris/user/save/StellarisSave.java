package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.util.io.FileNameFilter;
import io.github.ititus.pdx.util.io.IOUtil;
import io.github.ititus.pdx.util.io.IPathFilter;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class StellarisSave {

    private static final String META = "meta";
    private static final String GAMESTATE = "gamestate";

    private static final ImmutableSet<String> BLACKLIST = Sets.immutable.of();
    private static final IPathFilter FILTER = new FileNameFilter(n -> n.equals(META) || n.equals(GAMESTATE));

    private final Path save;
    private final PdxRawDataLoader saveDataLoader;

    private Meta meta;
    private GameState gameState;

    public StellarisSave(Path saveFile) {
        if (!isValidSaveFile(saveFile)) {
            throw new IllegalArgumentException();
        }

        this.save = saveFile;
        this.saveDataLoader = new PdxRawDataLoader(saveFile, BLACKLIST, FILTER, -1, null);
        this.meta = this.saveDataLoader.getRawData().getObject(META).getAs(Meta::new);
        this.gameState = this.saveDataLoader.getRawData().getObject(GAMESTATE).getAs(GameState::new);
    }

    public static StellarisSave loadLastModified(Path saveDir) {
        if (!Files.isDirectory(saveDir)) {
            throw new IllegalArgumentException();
        }

        Optional<Path> latest;
        try (Stream<Path> stream = Files.list(saveDir)) {
            latest = stream
                    .filter(StellarisSave::isValidSaveFile)
                    .max(Comparator.comparing(p -> {
                        try {
                            return Files.getLastModifiedTime(p);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    }));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return latest.map(StellarisSave::new).get();
    }

    public static boolean isValidSaveFile(Path saveFile) {
        return saveFile != null && (Files.isDirectory(saveFile) || (Files.isRegularFile(saveFile) && IOUtil.getExtension(saveFile).equals("sav")));
    }

    public PdxRawDataLoader getSaveDataLoader() {
        return saveDataLoader;
    }

    public Path getSave() {
        return save;
    }

    public Meta getMeta() {
        return meta;
    }

    public GameState getGameState() {
        return gameState;
    }

    public ImmutableList<String> getErrors() {
        return saveDataLoader.getRawData().getUsageStatistic().getErrorStrings();
    }
}
