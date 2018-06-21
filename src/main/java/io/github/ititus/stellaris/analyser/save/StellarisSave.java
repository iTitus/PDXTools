package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptParser;
import io.github.ititus.stellaris.analyser.util.ZipUtil;

import java.io.File;
import java.util.Date;

public class StellarisSave {

    private final File saveFolder;
    private final Meta meta;
    private final GameState gameState;

    public StellarisSave(File saveFile, boolean zipped) {
        if (zipped) {
            String name = saveFile.getName();
            this.saveFolder = new File(saveFile.getParent(), name.substring(0, name.length() - ".sav".length()) + "_extracted");
            ZipUtil.unzip(saveFile, saveFolder);
        } else {
            this.saveFolder = saveFile;
        }

        this.meta = PdxScriptParser.parse(new File(this.saveFolder, "meta")).getAs(Meta::new);
        this.gameState = PdxScriptParser.parse(new File(this.saveFolder, "gamestate")).getAs(GameState::new);
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
                System.out.println("Found non Stellaris save file " + saveFile + " in save directory, skipping");
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

        return new StellarisSave(newestSave, true);
    }

    public static boolean isValidSaveFile(File saveFile) {
        return saveFile != null && saveFile.exists() && saveFile.isFile() && saveFile.getName().endsWith(".sav");
    }

    public File getSaveFolder() {
        return saveFolder;
    }

    public Meta getMeta() {
        return meta;
    }

    public GameState getGameState() {
        return gameState;
    }

}
