package io.github.ititus.pdx.pdxlocalisation;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PdxLocalisationParser {

    public static Object parse(File yamlFile) {
        String src;
        try (Stream<String> stream = Files.lines(yamlFile.toPath(), StandardCharsets.UTF_8)) {
            src = stream.collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
            src = "";
        }
        return parse(src);
    }

    public static Object parse(String src) {
        // TODO: this
        return null;
    }

}
