package io.github.ititus.pdx;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;
import io.github.ititus.pdx.util.io.IOUtil;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PatchGenerator {

    private static final Path USER_HOME = Path.of(System.getProperty("user.home"));
    private static final Path DESKTOP_PDX_PATCHES_DIR = USER_HOME.resolve("Desktop/pdx/patches");
    private static final Path INSTALL_DIR = Path.of("C:/Program Files (x86)/Steam/steamapps/common");

    private PatchGenerator() {
    }

    public static void main(String[] args) throws Exception {
        Path fileRelative = Path.of("gfx/models/planets/_planetary_entities.asset");

        List<Path> changedFiles;
        try (Stream<Path> stream = Files.walk(DESKTOP_PDX_PATCHES_DIR)) {
            changedFiles = stream
                    .filter(Objects::nonNull)
                    .filter(Files::isRegularFile)
                    .sorted(IOUtil.ASCIIBETICAL)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        for (Path changedFile : changedFiles) {
            Path relative = DESKTOP_PDX_PATCHES_DIR.relativize(changedFile);
            Path originalFile = INSTALL_DIR.resolve(DESKTOP_PDX_PATCHES_DIR.relativize(changedFile));
            String path = relative.toString().replace('\\', '/');

            List<String> original = Files.readAllLines(originalFile);
            List<String> revised = Files.readAllLines(changedFile);

            Patch<String> diff = DiffUtils.diff(original, revised);
            List<String> unifiedDiff = UnifiedDiffUtils.generateUnifiedDiff("a/" + path, "b/" + path, original, diff, 3);
            unifiedDiff.forEach(System.out::println);
        }
    }
}