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
import java.util.stream.Collector;
import java.util.stream.Stream;

import static io.github.ititus.pdx.pdxscript.PdxConstants.UTF_8_BOM;

public final class PdxLocalisationParser {

    private static final IPathFilter FILTER = new FileExtensionFilter("yml");

    private PdxLocalisationParser() {
    }

    public static PdxLocalisation parse(Path installDir, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        return parse(installDir, FILTER, index, progressMessageUpdater);
    }

    public static PdxLocalisation parse(Path installDir, IPathFilter filter, int index, StellarisSaveAnalyser.ProgressMessageUpdater progressMessageUpdater) {
        Path[] validFiles;
        try (Stream<Path> stream = Files.walk(installDir)) {
            validFiles = stream
                    .filter(Objects::nonNull)
                    .filter(Files::isRegularFile)
                    .filter(p -> (filter == null || filter.test(p)))
                    .sorted(IOUtil.ASCIIBETICAL)
                    .toArray(Path[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        int fileCount = validFiles.length;
        MutableInt progress = new MutableInt();
        PdxLocalisation localisation = new PdxLocalisation(
                Arrays.stream(validFiles)
                        .map(p -> {
                            if (progressMessageUpdater != null) {
                                progressMessageUpdater.updateProgressMessage(index, true, progress.getAndIncrement(), fileCount, "Loading Localisation File " + installDir.relativize(p));
                            }
                            return parseInternal(installDir, p);
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
        if (progressMessageUpdater != null) {
            progressMessageUpdater.updateProgressMessage(index, false, fileCount, fileCount, "Done");
        }
        return localisation;
    }

    private static MutableMap<String, MutableMap<String, String>> parseInternal(Path installDir, Path localisationFile) {
        if (localisationFile != null) {
            MutableBoolean first = new MutableBoolean(true);
            MutableString language = new MutableString();
            MutableMap<String, MutableMap<String, String>> localisation = Maps.mutable.empty();
            try (Stream<String> stream = Files.lines(localisationFile)) {
                stream
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

                            line = line.strip();
                            int len = line.length();
                            if (len == 0 || line.charAt(0) == '#') { // empty line or comment
                                return;
                            } else if (len > 2 && line.charAt(0) == 'l' && line.charAt(1) == '_' && line.charAt(2) != 'c') {
                                int colon = line.indexOf(':', 2);
                                if (colon >= 0) {
                                    language.set(line.substring(0, colon));
                                    return;
                                }
                            }

                            if (language.isNotNull()) {
                                int separator = line.indexOf(':');
                                if (separator >= 0) {
                                    int valueStart = line.indexOf('"', separator + 2);
                                    if (valueStart >= 0) {
                                        int valueEnd = line.lastIndexOf('"');
                                        if (valueEnd > valueStart) {
                                            String key = line.substring(0, separator);
                                            String value = line.substring(valueStart + 1, valueEnd);
                                            localisation.computeIfAbsent(language.get(), k -> Maps.mutable.empty()).put(key, value);
                                            return;
                                        }
                                    }
                                }
                            }

                            throw new RuntimeException("unexpected line: '" + line + "'");
                        });
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

            return localisation;
        }

        return null;
    }
}
