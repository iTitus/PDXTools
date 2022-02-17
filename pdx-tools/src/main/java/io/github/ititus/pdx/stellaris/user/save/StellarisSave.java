package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.commons.io.PathUtil;
import io.github.ititus.commons.io.ZipUtil;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import org.eclipse.collections.api.list.ImmutableList;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class StellarisSave {

    private static final String META = "meta";
    private static final String GAMESTATE = "gamestate";

    public final Meta meta;
    private final Path save;
    public GameState gameState;
    private ImmutableList<String> errors;

    public StellarisSave(Path saveFile) {
        if (!isValidSaveFileOrDir(saveFile)) {
            throw new IllegalArgumentException();
        }

        this.save = saveFile;
        this.meta = loadMeta();
    }

    public static StellarisSave loadLatestByLastModifiedTime(Path saveDir) {
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

        return latest.map(StellarisSave::new).orElseThrow();
    }

    public static StellarisSave loadLatestByIngameDate(Path saveDir) {
        if (!Files.isDirectory(saveDir)) {
            throw new IllegalArgumentException();
        }

        Optional<StellarisSave> latest;
        try (Stream<Path> stream = Files.list(saveDir)) {
            latest = stream
                    .filter(StellarisSave::isValidSaveFile)
                    .map(StellarisSave::new)
                    .max(Comparator
                            .comparing((StellarisSave s) -> s.meta.date)
                            .thenComparing(s -> {
                                try {
                                    return Files.getLastModifiedTime(s.save);
                                } catch (IOException e) {
                                    throw new UncheckedIOException(e);
                                }
                            })
                    );
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return latest.orElseThrow();
    }

    public static boolean isValidSaveFile(Path saveFile) {
        return saveFile != null && Files.isRegularFile(saveFile) && PathUtil.getExtension(saveFile).orElseThrow().equals("sav");
    }

    public static boolean isValidSaveFileOrDir(Path saveFile) {
        return saveFile != null && (Files.isDirectory(saveFile) || isValidSaveFile(saveFile));
    }

    private Meta loadMeta() {
        return load(META).getAs(Meta::new);
    }

    public void loadGamestate() {
        PdxScriptObject o = load(GAMESTATE);
        this.gameState = o.getAs(GameState::new);
        this.errors = o.getUsageStatistic().getErrorStrings();
    }

    private PdxScriptObject load(String name) {
        if (Files.isRegularFile(save)) {
            try (FileSystem fs = ZipUtil.openZip(save)) {
                return PdxScriptParser.parse(fs.getPath(name)).expectObject();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        return PdxScriptParser.parse(save.resolve(name)).expectObject();
    }

    public Path getSave() {
        return save;
    }

    public ImmutableList<String> getErrors() {
        return errors;
    }
}
