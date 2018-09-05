package io.github.ititus.pdx.pdxlocalisation;

import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IOUtil;
import io.github.ititus.pdx.util.mutable.MutableBoolean;
import io.github.ititus.pdx.util.mutable.MutableInt;
import io.github.ititus.pdx.util.mutable.MutableString;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Maps;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.stream.Collector;
import java.util.stream.Stream;

public final class PdxLocalisationParser implements PdxConstants {

    private PdxLocalisationParser() {
    }

    public static PDXLocalisation parse(File installDir, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        return parse(installDir, new FileExtensionFilter("yml"), index, progressMessageUpdater);
    }

    public static PDXLocalisation parse(File installDir, FileFilter filter, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        File[] validFiles;
        try (Stream<Path> stream = Files.walk(installDir.toPath(), FileVisitOption.FOLLOW_LINKS)) {
            validFiles = stream.filter(Objects::nonNull).map(Path::toFile).filter(f -> !f.isDirectory() && (filter == null || filter.accept(f))).sorted(IOUtil.asciibetical(installDir)).toArray(File[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        int FILE_COUNT = validFiles.length;
        MutableInt progress = new MutableInt();
        String installDirPath;
        try {
            installDirPath = installDir.getCanonicalPath();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        PDXLocalisation localisation = new PDXLocalisation(
                Arrays.stream(validFiles)
                        .filter(Objects::nonNull)
                        .filter(File::isFile)
                        .map(f -> {
                            progressMessageUpdater.updateProgressMessage(index, true, progress.getAndIncrement(), FILE_COUNT, "Loading Localisation File " + IOUtil.getRelativePath(installDirPath, f));
                            return parseInternal(f);
                        })
                        .collect(Collector.of(
                                Maps.mutable::<String, MutableMap<String, String>>of,
                                (mutableMap, map) -> map.forEachKeyValue((lang, langMap) -> mutableMap.computeIfAbsent(lang, k -> Maps.mutable.empty()).putAll(langMap)),
                                (mutableMap, map) -> {
                                    map.forEachKeyValue((lang, langMap) -> mutableMap.computeIfAbsent(lang, k -> Maps.mutable.empty()).putAll(langMap));
                                    return mutableMap;
                                },
                                mutableMap -> {
                                    MutableMap<String, ImmutableMap<String, String>> map = Maps.mutable.empty();
                                    mutableMap.forEachKeyValue((lang, langMap) -> map.put(lang, langMap.toImmutable()));
                                    return map.toImmutable();
                                }
                                )
                        )
        );
        progressMessageUpdater.updateProgressMessage(index, false, FILE_COUNT, FILE_COUNT, "Done");
        return localisation;
    }

    private static MutableMap<String, MutableMap<String, String>> parseInternal(File localisationFile) {
        if (localisationFile != null) {
            MutableBoolean first = new MutableBoolean(true);
            MutableString language = new MutableString();
            Matcher m = EMPTY_PATTERN.matcher(EMPTY);
            MutableMap<String, MutableMap<String, String>> localisation = Maps.mutable.empty();
            try (Stream<String> stream = Files.lines(localisationFile.toPath(), StandardCharsets.UTF_8)) {
                stream
                        .filter(Objects::nonNull)
                        .filter(s -> !s.isEmpty())
                        .forEachOrdered(line -> {
                            if (first.get()) {
                                if (line.charAt(0) == UTF_8_BOM) {
                                    line = line.substring(1);
                                } else {
                                    throw new RuntimeException("Localisation file (" + localisationFile + ") has no BOM");
                                }
                                first.set(false);
                            }

                            m.usePattern(LANGUAGE_PATTERN).reset(line);
                            if (m.matches()) {
                                language.set(m.group(KEY_LANGUAGE).intern());
                            } else if (language.isNotNull()) {
                                m.usePattern(TRANSLATION_PATTERN);
                                if (m.matches()) {
                                    String indent = m.group(KEY_INDENT);
                                    if (indent != null && indent.length() == 1) {
                                        String key = m.group(KEY_KEY).intern();
                                        String value = m.group(KEY_VALUE).intern();
                                        localisation.computeIfAbsent(language.get(), k -> Maps.mutable.empty()).put(key, value);
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
