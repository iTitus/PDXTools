package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.util.IOUtil;
import io.github.ititus.pdx.util.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class StellarisSave {

    private final File save;
    private final boolean zipped;
    private Meta meta;
    private GameState gameState;

    public StellarisSave(File saveFile) {
        if (saveFile == null || !saveFile.exists() || !(saveFile.isDirectory() || IOUtil.getExtension(saveFile).equals("sav"))) {
            throw new IllegalArgumentException();
        }

        this.save = saveFile;
        this.zipped = !saveFile.isDirectory();

        if (zipped) {
            ZipUtil.readZipContents(saveFile, this::readFromZip);
        } else {
            this.meta = new Meta(PdxScriptParser.parse(new File(this.save, "meta")));
            this.gameState = new GameState(PdxScriptParser.parse(new File(this.save, "gamestate")));
        }
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

    private void readFromZip(ZipFile zipFile, ZipEntry zipEntry) throws IOException {
        switch (zipEntry.getName()) {
            case "meta":
                this.meta = new Meta(parse(zipFile, zipEntry));
                break;
            case "gamestate":
                this.gameState = new GameState(parse(zipFile, zipEntry));
        }
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
        gameState.getErrors().forEach((k, v) -> errors.computeIfAbsent("gamestate." + k, k_ -> new HashSet<>()).addAll(v));
        meta.getErrors().forEach((k, v) -> errors.computeIfAbsent("meta." + k, k_ -> new HashSet<>()).addAll(v));
        return errors;
    }

}
