package io.github.ititus.pdx.pdxlocalisation;

import com.koloboke.collect.map.hash.HashObjObjMaps;
import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IOUtil;
import io.github.ititus.pdx.util.mutable.MutableBoolean;
import io.github.ititus.pdx.util.mutable.MutableString;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.stream.Stream;

public final class PdxLocalisationParser implements PdxConstants {

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
                                .reduce(HashObjObjMaps.newUpdatableMap(),
                                        (acc, r) -> {
                                            r.forEach((language, languageMap) -> acc.computeIfAbsent(language, k -> HashObjObjMaps.newUpdatableMap()).putAll(languageMap));
                                            return acc;
                                        }
                                )
                ) :
                null;
    }

    private static Map<String, Map<String, String>> parseInternal(File localisationFile) {
        if (localisationFile != null) {
            MutableBoolean first = new MutableBoolean(true);
            MutableString language = new MutableString();
            Map<String, Map<String, String>> localisation = HashObjObjMaps.newUpdatableMap();
            try (Stream<String> stream = Files.lines(localisationFile.toPath(), StandardCharsets.UTF_8)) {
                stream
                        .filter(s -> s != null && !s.isEmpty())
                        .forEachOrdered(line -> {
                            if (first.get()) {
                                if (line.charAt(0) == UTF_8_BOM) {
                                    line = line.substring(1);
                                } else {
                                    throw new RuntimeException("Localisation file (" + localisationFile + ") has no BOM");
                                }
                                first.set(false);
                            }

                            Matcher m = LANGUAGE_PATTERN.matcher(line);
                            if (m.matches()) {
                                language.set(m.group(KEY_LANGUAGE).intern());
                            } else if (language.isNotNull()) {
                                m.usePattern(TRANSLATION_PATTERN);
                                if (m.matches()) {
                                    String indent = m.group(KEY_INDENT);
                                    if (indent != null && indent.length() == 1) {
                                        String key = m.group(KEY_KEY).intern();
                                        String value = m.group(KEY_VALUE).intern();
                                        localisation.computeIfAbsent(language.get(), k -> HashObjObjMaps.newUpdatableMap()).put(key, value);
                                    }
                                }
                            }
                        });
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

            return localisation;
        }
        return null;
    }
}
