package io.github.ititus.pdx.pdxlocalisation;

import io.github.ititus.pdx.pdxscript.PdxConstants;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.stellaris.StellarisSaveAnalyser;
import io.github.ititus.pdx.util.io.FileExtensionFilter;
import io.github.ititus.pdx.util.io.IOUtil;
import io.github.ititus.pdx.util.io.IPathFilter;
import io.github.ititus.pdx.util.mutable.MutableBoolean;
import io.github.ititus.pdx.util.mutable.MutableString;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.tuple.Tuples;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
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
        Path[] files;
        try (Stream<Path> stream = Files.walk(installDir)) {
            files = stream
                    .filter(Files::isRegularFile)
                    .filter(p -> (filter == null || filter.test(p)))
                    .sorted(IOUtil.ASCIIBETICAL)
                    .toArray(Path[]::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        MutableSet<String> languages = Sets.mutable.empty();
        MutableMap<String, MutableMap<String, String>> localisation = Maps.mutable.empty();
        for (Path p : files) {
            parseInternal(languages, localisation, installDir, p);
        }

        return new PdxLocalisation(languages.toImmutable(), localisation.collect((k, v) -> Tuples.pair(k, v.toImmutable())).toImmutable());
    }

    private static void parseInternal(MutableSet<String> languages, MutableMap<String, MutableMap<String, String>> localisation, Path installDir, Path p) {
        MutableBoolean first = new MutableBoolean(true);
        MutableString language = new MutableString();
        try (Stream<String> stream = Files.lines(p)) {
            stream
                    .filter(s -> !s.isEmpty())
                    .forEachOrdered(line -> {
                        if (first.get()) {
                            if (line.charAt(0) == UTF_8_BOM) {
                                line = line.substring(1);
                            } else {
                                throw new RuntimeException("Localisation file (" + installDir.relativize(p) + ") has no BOM");
                            }

                            first.set(false);
                        }

                        line = line.strip();
                        int len = line.length();
                        if (len == 0 || line.charAt(0) == '#') { // empty line or comment
                            return;
                        } else if (len > 2 && line.charAt(0) == 'l' && line.charAt(1) == '_' && line.charAt(2) != 'c') {
                            int colon = line.indexOf(':', 2);
                            if (colon >= 0 && (len == colon + 1 || !PdxScriptParser.isDigit(line.charAt(colon + 1)))) {
                                language.set(line.substring(0, colon));
                                languages.add(language.get());
                                return;
                            }
                        }

                        if (language.isNotNull()) {
                            int separator = line.indexOf(':');
                            if (separator >= 0) {
                                int valueStart = line.indexOf('"', separator + 1);
                                if (valueStart >= 0) {
                                    int valueEnd = line.lastIndexOf('"');
                                    if (valueEnd > valueStart) {
                                        String key = line.substring(0, separator).toLowerCase(Locale.ROOT);
                                        String value = line.substring(valueStart + 1, valueEnd);
                                        localisation.computeIfAbsent(key, k -> Maps.mutable.withInitialCapacity(PdxConstants.LANGUAGE_COUNT))
                                                .put(language.get(), value);

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
    }
}
