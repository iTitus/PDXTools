package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxRawDataLoader;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.util.collection.CollectionUtil;
import io.github.ititus.pdx.util.io.IFileFilter;
import io.github.ititus.pdx.util.io.IOUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class StellarisSave {

    private static final String META = "meta";
    private static final String GAMESTATE = "gamestate";

    private static final Set<String> BLACKLIST = CollectionUtil.setOf();
    private static final IFileFilter FILTER = f -> f != null && (f.getName().equals(META) || f.getName().equals(GAMESTATE));

    private final File save;
    private final PdxRawDataLoader saveDataLoader;
    private Meta meta;
    private GameState gameState;

    public StellarisSave(File saveFile) {
        if (saveFile == null || !saveFile.exists() || !(saveFile.isDirectory() || IOUtil.getExtension(saveFile).equals("sav"))) {
            throw new IllegalArgumentException();
        }

        this.save = saveFile;
        this.saveDataLoader = new PdxRawDataLoader(saveFile, BLACKLIST, FILTER);
        this.meta = this.saveDataLoader.getRawData().getObject(META).getAs(Meta::new);
        this.gameState = this.saveDataLoader.getRawData().getObject(GAMESTATE).getAs(GameState::new);
    }

    private static IPdxScript parse(ZipFile zipFile, ZipEntry zipEntry) throws IOException {
        return PdxScriptParser.parse(IOUtil.getCharacterStream(new InputStreamReader(zipFile.getInputStream(zipEntry))));
    }

    public static StellarisSave loadNewest(String saveDirPath) {
        File saveDir = new File(saveDirPath);
        if (!saveDir.exists() || !saveDir.isDirectory()) {
            throw new IllegalArgumentException(saveDirPath);
        }

        File[] files = saveDir.listFiles();
        if (files == null) {
            throw new IllegalArgumentException();
        }


        File newestSave = null;
        Date newestDate = null;

        for (File saveFile : files) {
            if (!isValidSaveFile(saveFile)) {
                System.out.println("Found non Stellaris save file " + saveFile + " in save directory, skipping...");
                continue;
            }
            Date lastModified = new Date(saveFile.lastModified());
            if (newestDate == null || newestDate.before(lastModified)) {
                newestSave = saveFile;
                newestDate = lastModified;
            }
        }

        if (!isValidSaveFile(newestSave)) {
            throw new RuntimeException("No valid save file found in save dir " + saveDir);
        }

        System.out.println("Found newest save file: " + newestSave);

        return new StellarisSave(newestSave);
    }

    public static boolean isValidSaveFile(File saveFile) {
        return saveFile != null && ((saveFile.isFile() && IOUtil.getExtension(saveFile).equals("sav")) || saveFile.isDirectory());
    }

    public PdxRawDataLoader getSaveDataLoader() {
        return saveDataLoader;
    }

    public File getSave() {
        return save;
    }

    public Meta getMeta() {
        return meta;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Map<String, Set<String>> getErrors() {
        Map<String, Set<String>> errors = new HashMap<>();
        saveDataLoader.getRawData().getErrors().forEach((k, v) -> errors.computeIfAbsent(k, k_ -> new HashSet<>()).addAll(v));
        return errors;
    }

}
