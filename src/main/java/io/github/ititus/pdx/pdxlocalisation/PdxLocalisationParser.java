package io.github.ititus.pdx.pdxlocalisation;

import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IOUtil;
import io.github.ititus.pdx.util.io.IPathFilter;
import io.github.ititus.pdx.util.mutable.MutableBoolean;
import io.github.ititus.pdx.util.mutable.MutableInt;
import io.github.ititus.pdx.util.mutable.MutableString;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Maps;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static io.github.ititus.pdx.pdxscript.PdxConstants.UTF_8_BOM;

public final class PdxLocalisationParser {

    private PdxLocalisationParser() {
    }

    public static PDXLocalisation parse(Path installDir, int index,
                                        StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        return parse(installDir, new FileExtensionFilter("yml"), index, progressMessageUpdater);
    }

    public static PDXLocalisation parse(Path installDir, IPathFilter filter, int index,
                                        StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        Path[] validFiles;
        try (Stream<Path> stream = Files.walk(installDir)) {
            validFiles = stream
                    .filter(Objects::nonNull)
                    .filter(Predicate.not(Files::isDirectory))
                    .filter(p -> (filter == null || filter.test(p)))
                    .sorted(IOUtil.asciibetical(installDir))
                    .toArray(Path[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        int fileCount = validFiles.length;
        MutableInt progress = new MutableInt();
        PDXLocalisation localisation = new PDXLocalisation(
                Arrays.stream(validFiles)
                        .filter(Objects::nonNull)
                        .filter(Files::isRegularFile)
                        .map(p -> {
                            if (progressMessageUpdater != null) {
                                progressMessageUpdater.updateProgressMessage(index, true, progress.getAndIncrement(),
                                        fileCount, "Loading Localisation File " + installDir.relativize(p));
                            }
                            return parseInternal(installDir, p);
                        })
                        .collect(Collector.of(
                                Maps.mutable::<String, MutableMap<String, String>>of,
                                (mutableMap, map) -> map.forEachKeyValue((lang, langMap) -> mutableMap.computeIfAbsent(lang, k -> Maps.mutable.empty()).putAll(langMap)),
                                (mutableMap, map) -> {
                                    map.forEachKeyValue((lang, langMap) -> mutableMap.computeIfAbsent(lang,
                                            k -> Maps.mutable.empty()).putAll(langMap));
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
        progressMessageUpdater.updateProgressMessage(index, false, fileCount, fileCount, "Done");
        return localisation;
    }

    private static MutableMap<String, MutableMap<String, String>> parseInternal(Path installDir,
                                                                                Path localisationFile) {
        if (localisationFile != null) {
            MutableBoolean first = new MutableBoolean(true);
            MutableString language = new MutableString();
            MutableMap<String, MutableMap<String, String>> localisation = Maps.mutable.empty();
            try (Stream<String> stream = Files.lines(localisationFile)) {
                stream
                        .filter(Objects::nonNull)
                        .filter(s -> !s.isEmpty())
                        .forEachOrdered(line -> {
                            if (first.get()) {
                                if (line.charAt(0) == UTF_8_BOM) {
                                    line = line.substring(1);
                                } else {
                                    throw new RuntimeException("Localisation file (" + installDir.relativize(localisationFile) + ") has no BOM");
                                }
                                first.set(false);
                            }

                            if (line.startsWith("l_") && line.charAt(line.length() - 1) == ':') {
                                String langName = line.substring(0, line.length() - 1)/*.intern()*/;
                                language.set(langName);
                            } else if (language.isNotNull() && line.length() > 1 && line.charAt(0) == ' ' && !Character.isWhitespace(line.charAt(1)) && line.charAt(line.length() - 1) == '"') {
                                int separator = line.indexOf(':');
                                if (separator != -1) {
                                    String key = line.substring(1, separator);
                                    int valueStart = line.indexOf('"', separator);
                                    if (valueStart != -1) {
                                        String value = line.substring(valueStart + 1, line.length() - 1);
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
