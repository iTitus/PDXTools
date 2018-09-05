package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.util.io.IOUtil;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.tuple.Tuples;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class StellarisSaves {

    private final File saveGameFolder;
    private final ImmutableMap<String, ImmutableMap<String, StellarisSave>> saves;

    private MutableList<Pair<String, Throwable>> errors;

    public StellarisSaves(File saveGameFolder, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        if (saveGameFolder == null || !saveGameFolder.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.saveGameFolder = saveGameFolder;

        MutableMap<String, ImmutableMap<String, StellarisSave>> saves = Maps.mutable.empty();
        File[] saveFolders = saveGameFolder.listFiles(f -> f != null && f.isDirectory());

        if (saveFolders != null) {
            int SAVE_FOLDER_COUNT = saveFolders.length;
            int progress0 = 0;

            for (File saveFolder : saveFolders) {
                progressMessageUpdater.updateProgressMessage(index, true, progress0++, SAVE_FOLDER_COUNT, "Loading Save Game Folder " + saveFolder.getName());

                MutableMap<String, StellarisSave> saveMap = Maps.mutable.empty();
                File[] saveGames = saveFolder.listFiles(StellarisSave::isValidSaveFile);

                if (saveGames != null) {
                    int SAVE_GAME_COUNT = saveFolders.length;
                    int progress1 = 0;

                    for (File saveFile : saveGames) {
                        progressMessageUpdater.updateProgressMessage(index + 1, true, progress1++, SAVE_GAME_COUNT, "Loading Save Game " + saveFile.getName());

                        try {
                            saveMap.put(IOUtil.getNameWithoutExtension(saveFile), new StellarisSave(saveFile));
                        } catch (Exception e) {
                            String path = saveFolder.getName() + '/' + IOUtil.getNameWithoutExtension(saveFile);
                            Throwable t = e.getCause() != null ? e.getCause() : e;
                            Throwable[] suppressed = t.getSuppressed();
                            Throwable cause = t.getCause();
                            System.out.println("Error while parsing " + path + ": " + t + (suppressed != null && suppressed.length > 0 ? ", Supressed: " + Arrays.toString(suppressed) : "") + (cause != null ? ", Caused By: " + cause : ""));
                            if (errors == null) {
                                errors = Lists.mutable.empty();
                            }
                            errors.add(Tuples.pair(path, t));
                        }
                    }

                    progressMessageUpdater.updateProgressMessage(index + 1, false, SAVE_GAME_COUNT, SAVE_GAME_COUNT, "Done");
                }

                saves.put(saveFolder.getName(), saveMap.toImmutable());
            }

            progressMessageUpdater.updateProgressMessage(index, false, SAVE_FOLDER_COUNT, SAVE_FOLDER_COUNT, "Done");
        }

        this.saves = saves.toImmutable();
    }

    public File getSaveGameFolder() {
        return saveGameFolder;
    }

    public ImmutableMap<String, ImmutableMap<String, StellarisSave>> getSaves() {
        return saves;
    }

    public ImmutableList<Pair<String, Throwable>> getErrors() {
        return errors != null ? errors.stream().sorted(Comparator.comparing((Pair<String, Throwable> p) -> p.getTwo().toString()).thenComparing(Pair::getOne)).collect(Collectors2.toImmutableList()) : Lists.immutable.empty();
    }

    public StellarisSave getSave(String saveFolder, String saveGame) {
        return saves.get(saveFolder).get(saveGame);
    }
}
