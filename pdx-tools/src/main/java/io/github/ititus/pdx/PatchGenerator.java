package io.github.ititus.pdx;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public final class PatchGenerator {

    private static final Path PATCHES_DIR = toRealPath(Path.of(System.getProperty("user.home"), "Desktop/pdx/patches"));
    private static final Path INSTALL_DIR = toRealPath(Path.of("C:/Program Files (x86)/Steam/steamapps/common"));
    private static final Path OUTPUT_DIR = toRealPath(Path.of("pdx-tools/src/main/resources/patches"));

    private static final int CONTEXT_SIZE = 3;

    private PatchGenerator() {
    }

    public static void main(String[] args) {
        try (Stream<Path> stream = Files.walk(PATCHES_DIR)) {
            stream
                    .filter(Files::isRegularFile)
                    .map(PatchGenerator::toRealPath)
                    .forEach(PatchGenerator::generatePatch);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void generatePatch(Path changedFile) {
        System.out.println("#".repeat(80));

        Path relative = PATCHES_DIR.relativize(changedFile);
        String relativePath = relative.toString().replace('\\', '/');
        Path originalFile = INSTALL_DIR.resolve(PATCHES_DIR.relativize(changedFile));

        List<String> original, revised;
        try {
            original = Files.readAllLines(originalFile);
            revised = Files.readAllLines(changedFile);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        System.out.println("Generating diff for " + relativePath + ":");
        Patch<String> diff = DiffUtils.diff(original, revised);
        if (diff.getDeltas().isEmpty()) {
            System.out.println("Empty!");
            return;
        }

        List<String> unifiedDiff = UnifiedDiffUtils.generateUnifiedDiff("a/" + relativePath, "b/" + relativePath, original, diff, CONTEXT_SIZE);
        unifiedDiff.forEach(System.out::println);

        Path outputFile = OUTPUT_DIR.resolve(relativePath + ".patch");
        try {
            Files.createDirectories(outputFile.getParent());
            Files.write(outputFile, unifiedDiff);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Path toRealPath(Path p) {
        try {
            return p.toRealPath();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
