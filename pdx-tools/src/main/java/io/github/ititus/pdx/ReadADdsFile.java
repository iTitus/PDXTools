package io.github.ititus.pdx;

import io.github.ititus.dds.D3dFormat;
import io.github.ititus.dds.DdsFile;
import io.github.ititus.dds.DdsHeader;
import io.github.ititus.io.FileExtensionFilter;
import io.github.ititus.io.PathFilter;
import io.github.ititus.io.PathUtil;
import io.github.ititus.pdx.util.IOUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadADdsFile {

    private static final Path LOG_FILE = IOUtil.resolveRealFile(Path.of(System.getProperty("user.home"), "Desktop/pdx/dds.log"));
    private static final Path OUT_DIR = IOUtil.resolveRealDir(Path.of(System.getProperty("user.home"), "Desktop/pdx/dds_out"));
    private static final Path INSTALL_DIR = IOUtil.resolveRealDir(Path.of("C:/Program Files (x86)/Steam/steamapps/common/Stellaris"));
    private static final PathFilter FILTER = new FileExtensionFilter("dds");

    private final List<String> log = new ArrayList<>();
    private final List<Path> files;

    private ReadADdsFile() {
        try (Stream<Path> stream = Files.walk(INSTALL_DIR)) {
            files = stream
                    .filter(Files::isRegularFile)
                    .filter(FILTER)
                    .map(IOUtil::resolveRealFile)
                    .sorted(PathUtil.ASCIIBETICAL)
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        log("Found " + files.size() + " dds files");
    }

    public static void main(String[] args) {
        new ReadADdsFile().run();
    }

    private static String pathToString(Path p) {
        return INSTALL_DIR.relativize(p).toString().replace('\\', '/');
    }

    private void run() {
        try {
            categorizeAllDds();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            convertSampleImages();
            // convertAllImages();
        } catch (Exception e) {
            e.printStackTrace();
        }

        dumpLog();
    }

    private void categorizeAllDds() {
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
    }

    private void convertSampleImages() throws IOException {
        // A8R8G8B8
        convertImageFlat("flags/backgrounds/diagonal_stripe.dds");

        // R8G8B8
        convertImageFlat("flags/backgrounds/circle.dds");

        // A1R5G5B5
        convertImageFlat("gfx/interface/icons/dlc/ancient_relics_big.dds");

        // A8B8G8R8
        convertImageFlat("gfx/interface/buttons/standard_button_200_24_dlc_overlay_animated.dds");

        // DXT5
        convertImageFlat("flags/human/flag_human_1.dds");

        // DXT3
        convertImageFlat("gfx/interface/fleet_view/fleet_view_upgradable_design.dds");

        // DXT1
        convertImageFlat("gfx/event_pictures/space_dragon_blue.dds");
    }

    private void convertAllImages() throws IOException {
        for (Path file : files) {
            Path relative = INSTALL_DIR.relativize(file);
            convertImage(file, OUT_DIR.resolve(relative).resolveSibling(relative.getFileName().toString().replace(".dds", ".png")));
        }
    }

    private void convertImageFlat(String path) throws IOException {
        String outPath = path.replace('/', '_');
        outPath = outPath.replace(".dds", ".png");

        convertImage(INSTALL_DIR.resolve(path), OUT_DIR.resolve(outPath));
    }

    private void convertImage(Path inPath, Path outPath) throws IOException {
        log("converting " + inPath);

        DdsFile dds = DdsFile.load(inPath);
        log("format=" + dds.d3dFormat());

        Files.createDirectories(outPath.normalize().getParent());
        BufferedImage img = ImageIO.read(inPath.toFile());

        ImageIO.write(img, "png", outPath.toFile());
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
        log(pathToString(p) + ": " + dds);
        return dds;
    }

    private void log(String msg) {
        log.add(msg);
        // System.out.println(msg);
    }
}
