package io.github.ititus.pdx;

import io.github.ititus.dds.D3dFormat;
import io.github.ititus.dds.DdsFile;
import io.github.ititus.dds.DdsHeader;
import io.github.ititus.io.FileExtensionFilter;
import io.github.ititus.io.PathFilter;
import io.github.ititus.io.PathUtil;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadADdsFile {

    private static final Path LOG_FILE = toRealPath(Path.of(System.getProperty("user.home"), "Desktop/pdx/dds.log"), false);
    private static final Path INSTALL_DIR = toRealPath(Path.of("C:/Program Files (x86)/Steam/steamapps/common/Stellaris"), true);
    private static final PathFilter FILTER = new FileExtensionFilter("dds");

    private final List<String> log = new ArrayList<>();

    public static void main(String[] args) {
        new ReadADdsFile().run();
    }

    private static String pathToString(Path p) {
        return INSTALL_DIR.relativize(p).toString().replace('\\', '/');
    }

    private static Path toRealPath(Path p, boolean isDir) {
        try {
            Files.createDirectories(isDir ? p : p.normalize().getParent());
            return p.toRealPath();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void run() {
        List<Path> files;
        try (Stream<Path> stream = Files.walk(INSTALL_DIR)) {
            files = stream
                    .filter(Files::isRegularFile)
                    .filter(FILTER)
                    .map(p -> toRealPath(p, false))
                    .sorted(PathUtil.ASCIIBETICAL)
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        log("Found " + files.size() + " dds files");

        enum Type {
            FLAT, FLAT_MIPPED, CUBEMAP, CUBEMAP_MIPPED, VOLUME, VOLUME_MIPPED
        }

        Map<DdsHeader, List<Path>> headers = new LinkedHashMap<>();
        Map<D3dFormat, List<Path>> formats = new LinkedHashMap<>();
        Map<Type, List<Path>> types = new LinkedHashMap<>();
        for (Path p : files) {
            DdsFile dds;
            try {
                dds = read(p);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

            if (dds.isDx10()) {
                throw new RuntimeException("dds file contains unexpected dx10 header");
            }

            headers.computeIfAbsent(dds.header(), k -> new ArrayList<>()).add(p);
            formats.computeIfAbsent(dds.d3dFormat(), k -> new ArrayList<>()).add(p);
            types.computeIfAbsent(dds.isCubemap() ? (dds.hasMipmaps() ? Type.CUBEMAP_MIPPED : Type.CUBEMAP) : dds.isVolumeTexture() ? (dds.hasMipmaps() ? Type.VOLUME_MIPPED : Type.VOLUME) : (dds.hasMipmaps() ? Type.FLAT_MIPPED : Type.FLAT), k -> new ArrayList<>()).add(p);
        }

        log("\n\n");
        log("#".repeat(120));
        log("Headers");
        log("#".repeat(120));
        log("");
        headers.entrySet().stream()
                .sorted(Comparator.comparingInt((Map.Entry<DdsHeader, List<Path>> e) -> e.getValue().size()).reversed())
                .forEachOrdered(e -> {
                    log("");
                    log("(" + e.getValue().size() + "): " + e.getKey());
                    log(e.getValue().stream().map(ReadADdsFile::pathToString).collect(Collectors.joining(", ")));
                });

        log("\n\n");
        log("#".repeat(120));
        log("Formats");
        log("#".repeat(120));
        log("");
        formats.entrySet().stream()
                .sorted(Comparator.comparingInt((Map.Entry<D3dFormat, List<Path>> e) -> e.getValue().size()).reversed())
                .forEachOrdered(e -> {
                    log("");
                    log("(" + e.getValue().size() + "): " + e.getKey());
                    log(e.getValue().stream().map(ReadADdsFile::pathToString).collect(Collectors.joining(", ")));
                });

        log("\n\n");
        log("#".repeat(120));
        log("Types");
        log("#".repeat(120));
        log("");
        types.entrySet().stream()
                .sorted(Comparator.comparingInt((Map.Entry<Type, List<Path>> e) -> e.getValue().size()).reversed())
                .forEachOrdered(e -> {
                    log("");
                    log("(" + e.getValue().size() + "): " + e.getKey());
                    log(e.getValue().stream().map(ReadADdsFile::pathToString).collect(Collectors.joining(", ")));
                });

        dumpLog();

        /*BufferedImage img = ImageIO.read(p.toFile());
        System.out.println(img);*/
    }

    private void dumpLog() {
        try {
            Files.write(LOG_FILE, log);
            log.clear();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private DdsFile read(Path p) throws IOException {
        DdsFile dds = DdsFile.load(p);
        /*if ((dds.header().dwCaps() & DDSCAPS_TEXTURE) != DDSCAPS_TEXTURE) {
            System.out.println(pathToString(p) + ": dwCaps (0x" + Integer.toHexString(dds.header().dwCaps()) + ") doesnt contain DDSCAPS_TEXTURE (0x" + Integer.toHexString(DDSCAPS_TEXTURE) + ")");
        }*/
        log(pathToString(p) + ": " + dds);
        return dds;
    }

    private void log(String msg) {
        log.add(msg);
        // System.out.println(msg);
    }
}
