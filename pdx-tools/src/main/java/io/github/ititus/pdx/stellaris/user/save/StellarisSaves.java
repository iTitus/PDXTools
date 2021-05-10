package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.io.PathUtil;
import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.tuple.Tuples;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class StellarisSaves {

    private final Path saveGameFolder;
    private final ImmutableMap<String, ImmutableMap<String, StellarisSave>> saves;

    private MutableList<Pair<String, Throwable>> errors;

    public StellarisSaves(Path saveGameFolder, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (saveGameFolder == null || !Files.isDirectory(saveGameFolder)) {
            throw new IllegalArgumentException();
        }
        this.saveGameFolder = saveGameFolder;

        MutableMap<String, ImmutableMap<String, StellarisSave>> saves = Maps.mutable.empty();
        Path[] saveFolders;
        try (Stream<Path> stream = Files.list(saveGameFolder)) {
            saveFolders = stream
                    .filter(Files::isDirectory)
                    .toArray(Path[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        int saveFolderCount = saveFolders.length;
        int progress0 = 0;

        for (Path saveFolder : saveFolders) {
            progressMessageUpdater.updateProgressMessage(index, true, progress0++, saveFolderCount, "Loading Save Game Folder " + saveFolder.getFileName());

            MutableMap<String, StellarisSave> saveMap = Maps.mutable.empty();
            Path[] saveGames;
            try (Stream<Path> stream = Files.list(saveFolder)) {
                saveGames = stream
                        .filter(StellarisSave::isValidSaveFileOrDir)
                        .toArray(Path[]::new);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

            int saveGameCount = saveFolders.length;
            int progress1 = 0;

            for (Path saveFile : saveGames) {
                progressMessageUpdater.updateProgressMessage(index + 1, true, progress1++, saveGameCount, "Loading Save Game " + saveFile.getFileName());

                try {
                    saveMap.put(PathUtil.getNameWithoutExtension(saveFile), new StellarisSave(saveFile));
                } catch (Exception e) {
                    String path = saveFolder.getFileName().toString() + '/' + PathUtil.getNameWithoutExtension(saveFile);
                    Throwable t = e.getCause() != null ? e.getCause() : e;
                    Throwable[] suppressed = t.getSuppressed();
                    Throwable cause = t.getCause();
                    System.out.println("Error while parsing " + path + ": " + t + (suppressed != null && suppressed.length > 0 ? ", Suppressed: " + Arrays.toString(suppressed) : "") + (cause != null ? ", Caused By: " + cause : ""));
                    if (errors == null) {
                        errors = Lists.mutable.empty();
                    }
                    errors.add(Tuples.pair(path, t));
                }
            }

            progressMessageUpdater.updateProgressMessage(index + 1, false, saveGameCount, saveGameCount, "Done");

            saves.put(saveFolder.getFileName().toString(), saveMap.toImmutable());
        }

        progressMessageUpdater.updateProgressMessage(index, false, saveFolderCount, saveFolderCount, "Done");

        this.saves = saves.toImmutable();
    }

    public Path getSaveGameFolder() {
        return saveGameFolder;
    }

    public ImmutableMap<String, ImmutableMap<String, StellarisSave>> getSaves() {
        return saves;
    }

    public ImmutableList<Pair<String, Throwable>> getErrors() {
        return errors != null ?
                errors.stream()
                        .sorted(
                                Comparator.comparing((Pair<String, Throwable> p) -> p.getTwo().toString())
                                        .thenComparing(Pair::getOne)
                        )
                        .collect(Collectors2.toImmutableList())
                : Lists.immutable.empty();
    }

    public StellarisSave getSave(String saveFolder, String saveGame) {
        return saves.get(saveFolder).get(saveGame);
    }
}
