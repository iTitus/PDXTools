package io.github.ititus.pdx.pdxlocalisation;

import io.github.ititus.pdx.util.FileExtensionFilter;
import io.github.ititus.pdx.util.IOUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PdxLocalisationParser {

    private static final String UTF_8_BOM = "\uFEFF";

    private PdxLocalisationParser() {
    }

    public static PDXLocalisation parse(File installDir) {
        return parse(installDir, new FileExtensionFilter("yml"));
    }

    public static PDXLocalisation parse(File installDir, FileFilter filter) {
        File[] validFiles;
        try (Stream<Path> stream = Files.walk(installDir.toPath(), FileVisitOption.FOLLOW_LINKS)) {
            validFiles = stream.filter(Objects::nonNull).map(Path::toFile).filter(f -> !f.isDirectory() && (filter == null || filter.accept(f))).distinct().sorted(IOUtil.asciibetical(installDir)).toArray(File[]::new);
        } catch (IOException e) {
            e.printStackTrace();
            validFiles = null;
        }
        return parseAll(validFiles);
    }

    public static PDXLocalisation parseAll(File... localisationFiles) {
        return localisationFiles != null ?
                new PDXLocalisation(
                        Arrays.stream(localisationFiles)
                                .filter(Objects::nonNull)
                                .filter(File::isFile)
                                .map(PdxLocalisationParser::parseInternal)
                                .reduce(new HashMap<>(),
                                        (acc, r) -> {
                                            r.forEach((language, languageMap) -> acc.computeIfAbsent(language, k -> new HashMap<>()).putAll(languageMap));
                                            return acc;
                                        }
                                )
                ) :
                null;
    }

    private static Map<String, Map<String, String>> parseInternal(File localisationFile) {
        if (localisationFile != null) {
            List<String> lines;
            try (Stream<String> stream = Files.lines(localisationFile.toPath(), StandardCharsets.UTF_8)) {
                lines = stream.collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
                lines = null;
            }

            Pattern languageP = Pattern.compile("^(?<language>l_(\\w+)):$");
            Pattern translationP = Pattern.compile("^(?<indent> )(?<key>[\\w.]+):0 \"(?<value>.*)\"$");

            String first = lines.get(0);
            if (first.startsWith(UTF_8_BOM)) {
                lines.set(0, first.substring(1));
            } else {
                throw new RuntimeException("Localisation file (" + localisationFile + ") has no BOM");
            }

            lines.removeIf(s -> s == null || s.isEmpty() || (!languageP.matcher(s).matches() && !translationP.matcher(s).matches()));

            String language = null;
            Map<String, Map<String, String>> localisation = new HashMap<>();

            for (String line : lines) {
                Matcher m = languageP.matcher(line);
                if (m.matches()) {
                    language = m.group("language");
                    continue;
                }
                if (language != null) {
                    m = translationP.matcher(line);
                    if (m.matches()) {
                        String indent = m.group("indent");
                        if (indent != null && indent.length() == 1) {
                            localisation.computeIfAbsent(language, k -> new HashMap<>()).put(m.group("key"), m.group("value"));
                            continue;
                        }
                    }
                }
                throw new RuntimeException("Found unknown line " + line);
            }

            return localisation;
        }
        return null;
    }

}
