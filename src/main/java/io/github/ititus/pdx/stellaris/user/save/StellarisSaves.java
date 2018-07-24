package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.util.collection.Pair;
import io.github.ititus.pdx.util.io.IOUtil;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StellarisSaves {

    private final File saveGameFolder;
    private final Map<String, Map<String, StellarisSave>> saves;
    private final List<Pair<String, Throwable>> errors;

    public StellarisSaves(File saveGameFolder) {
        if (saveGameFolder == null || !saveGameFolder.isDirectory()) {
            throw new IllegalArgumentException();
        }
        this.saveGameFolder = saveGameFolder;
        this.saves = new HashMap<>();
        this.errors = new ArrayList<>();

        File[] files = saveGameFolder.listFiles();
        if (files != null) {
            for (File saveGame : files) {
                if (saveGame != null && saveGame.isDirectory()) {
                    Map<String, StellarisSave> saveMap = saves.computeIfAbsent(saveGame.getName(), k -> new HashMap<>());

                    File[] saveGames = saveGame.listFiles();
                    if (saveGames != null) {
                        for (File saveGameFile : saveGames) {
                            if (StellarisSave.isValidSaveFile(saveGameFile)) {
                                try {
                                    saveMap.put(IOUtil.getNameWithoutExtension(saveGameFile), new StellarisSave(saveGameFile));
                                } catch (Exception e) {
                                    String path = saveGame.getName() + '/' + IOUtil.getNameWithoutExtension(saveGameFile);
                                    Throwable t = e.getCause() != null ? e.getCause() : e;
                                    Throwable[] suppressed = t.getSuppressed();
                                    Throwable cause = t.getCause();
                                    System.out.println("Error while parsing " + path + ": " + t + (suppressed != null && suppressed.length > 0 ? ", Supressed: " + Arrays.toString(suppressed) : "") + (cause != null ? ", Caused By: " + cause : ""));
                                    errors.add(Pair.of(path, t));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public File getSaveGameFolder() {
        return saveGameFolder;
    }

    public Map<String, Map<String, StellarisSave>> getSaves() {
        Map<String, Map<String, StellarisSave>> map = new HashMap<>();
        saves.forEach((name, saveMap) -> map.put(name, Collections.unmodifiableMap(saveMap)));
        return Collections.unmodifiableMap(map);
    }

    public List<Pair<String, Throwable>> getErrors() {
        return Collections.unmodifiableList(errors.stream().sorted(Comparator.comparing((Function<Pair<String, Throwable>, String>) String::valueOf).thenComparing(Pair::getKey)).collect(Collectors.toList()));
    }

    public StellarisSave getSave(String saveFolder, String saveGame) {
        return saves.get(saveFolder).get(saveGame);
    }
}
