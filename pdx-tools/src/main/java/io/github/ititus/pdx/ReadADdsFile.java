package io.github.ititus.pdx;

import io.github.ititus.dds.D3dFormat;
import io.github.ititus.dds.DdsFile;
import io.github.ititus.dds.DdsHeader;
import io.github.ititus.io.FileExtensionFilter;
import io.github.ititus.io.PathFilter;
import io.github.ititus.io.PathUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
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

    public static void main(String[] args) throws IOException {
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

    private void run() throws IOException {
        // categorizeAllDds();

        // A8R8G8B8
        BufferedImage img1 = ImageIO.read(INSTALL_DIR.resolve("flags/backgrounds/diagonal_stripe.dds").toFile());
        ImageIO.write(img1, "png", new File("out/img1.png"));

        // R8G8B8
        BufferedImage img2 = ImageIO.read(INSTALL_DIR.resolve("flags/backgrounds/circle.dds").toFile());
        ImageIO.write(img2, "png", new File("out/img2.png"));

        // A1R5G5B5
        BufferedImage img3 = ImageIO.read(INSTALL_DIR.resolve("gfx/interface/icons/dlc/ancient_relics_big.dds").toFile());
        ImageIO.write(img3, "png", new File("out/img3.png"));

        // A8B8G8R8
        BufferedImage img4 = ImageIO.read(INSTALL_DIR.resolve("gfx/interface/buttons/standard_button_200_24_dlc_overlay_animated.dds").toFile());
        ImageIO.write(img4, "png", new File("out/img4.png"));

        // DXT5
        BufferedImage img5 = ImageIO.read(INSTALL_DIR.resolve("flags/human/flag_human_1.dds").toFile());
        ImageIO.write(img5, "png", new File("out/img5.png"));

        // DXT3
        BufferedImage img6 = ImageIO.read(INSTALL_DIR.resolve("gfx/interface/fleet_view/fleet_view_upgradable_design.dds").toFile());
        ImageIO.write(img6, "png", new File("out/img6.png"));

        // DXT1
        BufferedImage img7 = ImageIO.read(INSTALL_DIR.resolve("gfx/event_pictures/space_dragon_blue.dds").toFile());
        ImageIO.write(img7, "png", new File("out/img7.png"));

        /*BufferedImage img = ImageIO.read(p.toFile());
        System.out.println(img);*/
    }

    private void categorizeAllDds() {
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
