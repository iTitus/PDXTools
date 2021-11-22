package io.github.ititus.pdx;

import io.github.ititus.math.time.DurationFormatter;
import io.github.ititus.math.time.StopWatch;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.shared.ProgressMessageUpdater;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.game.common.technology.Technology;
import io.github.ititus.pdx.stellaris.game.scope.CountryScope;
import io.github.ititus.pdx.stellaris.user.StellarisUserData;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;
import io.github.ititus.pdx.stellaris.user.save.TechQueueItem;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.multimap.ImmutableMultimap;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.primitive.ObjectDoublePair;
import org.eclipse.collections.impl.factory.Multimaps;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Test {

    private static final Path USER_HOME = Path.of(System.getProperty("user.home"));
    private static final Path USER_DATA_DIR = USER_HOME.resolve("Documents/Paradox Interactive/Stellaris");
    private static final Path INSTALL_DIR = Path.of("C:/Program Files (x86)/Steam/steamapps/common/Stellaris");
    private static final Path SAVE_DIR = USER_DATA_DIR.resolve("save games/unitednationsofearth_-15512622");
    private static final Path PDX_TEMP_DIR = USER_HOME.resolve("Desktop/pdx");
    private static final Path DEBUG_OUT = PDX_TEMP_DIR.resolve("out.txt");
    private static final Path[] TEST_FILES = { /*USER_HOME.resolve("Desktop/pdx/test.txt")*/ };

    private static StellarisGame getStellarisGame() {
        StopWatch s = StopWatch.createRunning();
        StellarisGame game = new StellarisGame(INSTALL_DIR, 0, new TestProgressMessageUpdater());
        System.out.println("Game Data Load Time: " + DurationFormatter.format(s.stop()));
        return game;
    }

    private static StellarisUserData getStellarisUserData() {
        StopWatch s = StopWatch.createRunning();
        StellarisUserData userData = new StellarisUserData(USER_DATA_DIR, 1, new TestProgressMessageUpdater());
        System.out.println("User Data Load Time: " + DurationFormatter.format(s.stop()));
        return userData;
    }

    private static StellarisSave getStellarisSave() {
        StopWatch s = StopWatch.createRunning();
        StellarisSave save = StellarisSave.loadLatestByLastModifiedTime(SAVE_DIR);
        save.loadGamestate();
        System.out.println("Test Save Load Time: " + DurationFormatter.format(s.stop()));
        return save;
    }

    public static void main(String[] args) {
        if (TEST_FILES.length > 0) {
            System.out.println("Running tests:");
            IPdxScript testScript = PdxScriptParser.parse(TEST_FILES);
            System.out.println(testScript.toPdxScript());
            System.out.println("done");
            System.out.println();
        }

        StopWatch s = StopWatch.createRunning();

        StellarisGame game = /*null; //*/ getStellarisGame();
        StellarisUserData userData = null; // getStellarisUserData();
        StellarisSave save = /*null; //*/ getStellarisSave();

        List<String> unknownLiterals = PdxScriptParser.getUnknownLiterals();
        ImmutableList<Pair<String, Throwable>> gameErrors = game != null && game.getRawDataLoader() != null ? game.getRawDataLoader().getErrors() : null;
        ImmutableList<String> gameParseErrors = game != null && game.getRawDataLoader() != null ? game.getRawDataLoader().getRawData().getUsageStatistic().getErrorStrings() : null;
        ImmutableList<Pair<String, Throwable>> userDataErrors = userData != null && userData.getRawDataLoader() != null ? userData.getRawDataLoader().getErrors() : null;
        ImmutableList<String> userDataParseErrors = userData != null && userData.getRawDataLoader() != null ? userData.getRawDataLoader().getRawData().getUsageStatistic().getErrorStrings() : null;
        ImmutableList<String> saveParseErrors = save != null ? save.getErrors() : null;
        // FIXME: disabled for performance
        ImmutableMultimap<String, String> missingLocalisation = /*game != null && game.getLocalisation() != null ? game.getLocalisation().getMissingLocalisation() :*/ null;
        ImmutableMultimap<String, String> extraLocalisation = /*game != null && game.getLocalisation() != null ? game.getLocalisation().getExtraLocalisation() :*/ null;

        try {
            Files.createDirectories(DEBUG_OUT.getParent());
            Files.write(DEBUG_OUT, new byte[0]);
            if (unknownLiterals != null && !unknownLiterals.isEmpty()) {
                Files.write(DEBUG_OUT, List.of("#".repeat(80), "Unknown Literals:", "#".repeat(80), ""), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, unknownLiterals, StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, List.of("", ""), StandardOpenOption.APPEND);
            }
            if (gameErrors != null && !gameErrors.isEmpty()) {
                Files.write(DEBUG_OUT, List.of("#".repeat(80), "Game Errors:", "#".repeat(80), ""), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, gameErrors.collect(p -> p.getOne() + ": " + p.getTwo()), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, List.of(""), StandardOpenOption.APPEND);
            }
            if (gameParseErrors != null && !gameParseErrors.isEmpty()) {
                Files.write(DEBUG_OUT, List.of("#".repeat(80), "Game Parse Errors:", "#".repeat(80), ""), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, gameParseErrors, StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, List.of(""), StandardOpenOption.APPEND);
            }
            if (userDataErrors != null && !userDataErrors.isEmpty()) {
                Files.write(DEBUG_OUT, List.of("#".repeat(80), "User Data Errors:", "#".repeat(80), ""), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, userDataErrors.collect(p -> p.getOne() + ": " + p.getTwo()), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, List.of("", ""), StandardOpenOption.APPEND);
            }
            if (userDataParseErrors != null && !userDataParseErrors.isEmpty()) {
                Files.write(DEBUG_OUT, List.of("#".repeat(80), "User Data Parse Errors:", "#".repeat(80), ""), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, userDataParseErrors, StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, List.of(""), StandardOpenOption.APPEND);
            }
            if (saveParseErrors != null && !saveParseErrors.isEmpty()) {
                Files.write(DEBUG_OUT, List.of("#".repeat(80), "Save Parse Errors:", "#".repeat(80), ""), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, saveParseErrors, StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, List.of("", ""), StandardOpenOption.APPEND);
            }
            if (missingLocalisation != null && !missingLocalisation.isEmpty()) {
                Files.write(DEBUG_OUT, List.of("#".repeat(80), "Missing Localisation:", "#".repeat(80), ""), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, missingLocalisation.keyMultiValuePairsView().collect(p -> p.getOne() + ": " + p.getTwo()), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, List.of("", ""), StandardOpenOption.APPEND);
            }
            if (extraLocalisation != null && !extraLocalisation.isEmpty()) {
                Files.write(DEBUG_OUT, List.of("#".repeat(80), "Extra Localisation:", "#".repeat(80), ""), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, extraLocalisation.keyMultiValuePairsView().collect(p -> p.getOne() + ": " + p.getTwo()), StandardOpenOption.APPEND);
                Files.write(DEBUG_OUT, List.of("", ""), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        System.out.println("Total Loading Time: " + DurationFormatter.format(s.stop()));
        doTechThings(game, userData, save);
        System.out.println("done");
    }

    private static void doTechThings(StellarisGame game, StellarisUserData userData, StellarisSave save) {
        if (game == null || save == null) {
            return;
        }

        System.out.println();

        int countryId = 0;
        CountryScope cs = new CountryScope(game, save, countryId);

        game.common.technologies.get("tech_ascension_theory").localise().forEach(System.out::println);
        System.out.println();

        var techs = game.common.technologies.all().toList();
        techs.removeIf(t -> t.levels() >= 1 && cs.getCountry().techStatus.getTechLevel(t.name()) >= t.levels());
        for (Technology.Area area : Technology.Area.values()) {
            TechQueueItem i = cs.getCountry().techStatus.getCurrentlyResearchedTech(area);
            if (i != null) {
                techs.removeIf(t -> t.name().equals(i.technology));
            }
        }
        techs.removeIf(t -> cs.getCountry().techStatus.alwaysAvailableTech.contains(t.name()));
        techs.removeIf(t -> anyPrerequisitesUnavailable(game, cs, t)); // remove unobtainable/event techs

        var blockedTechs = techs.select(t -> !t.hasAllPrerequisites(cs) || !t.hasTierCondition(cs));
        techs.removeIf(t -> !t.hasAllPrerequisites(cs) || !t.hasTierCondition(cs));

        var availableWeightedTechs = techs.groupByAndCollect(Technology::area, t -> PrimitiveTuples.pair(t, t.getWeight(cs)), Multimaps.mutable.list.empty());
        availableWeightedTechs.forEachKeyValue((k, v) -> {
            if (v.getTwo() <= 0) {
                blockedTechs.add(v.getOne());
            }
        });
        availableWeightedTechs = availableWeightedTechs.rejectKeysValues((k, v) -> v.getTwo() <= 0);

        var blockedWeightedTechs = blockedTechs.groupByAndCollect(Technology::area, t -> PrimitiveTuples.pair(t, t.getWeight(cs)), Multimaps.mutable.list.empty());

        for (Technology.Area area : Technology.Area.values()) {
            TechQueueItem i = cs.getCountry().techStatus.getCurrentlyResearchedTech(area);
            if (i != null) {
                Technology t = game.common.technologies.get(i.technology);
                int level = 1 + cs.getCountry().techStatus.getTechLevel(t.name());
                System.out.println("Currently researching (" + area.getName() + "): " + game.localisation.translate(t.name()) + " (" + t.name() + ", " + t.area().getName() + ", tier=" + t.tier() + (t.isRepeatable() ? ", level=" + level + "/" + t.levels() : "") + ") " + (int) i.progress + "/" + t.cost(cs));
            }
        }

        System.out.println();
        int researchAlternatives = cs.getCountry().techStatus.alternatives.get(Technology.Area.PHYSICS)
                .count(t -> !cs.getCountry().techStatus.alwaysAvailableTech.contains(t));
        System.out.println("Tech Alternatives: " + researchAlternatives);

        System.out.println("Always available: " +
                cs.getCountry().techStatus.alwaysAvailableTech.stream()
                        .map(game.common.technologies::get)
                        .map(t -> game.localisation.translate(t.name()) + " (" + t.name() + ", " + t.area().getName() + ", tier=" + t.tier() + ")")
                        .collect(Collectors.joining(", "))
        );

        for (Technology.Area area : Technology.Area.values()) {
            var techsInArea = availableWeightedTechs.get(area)
                    .toSortedList(Comparator.comparing(ObjectDoublePair<Technology>::getTwo).reversed());
            double totalWeight = techsInArea.sumOfDouble(ObjectDoublePair::getTwo);

            System.out.println();
            System.out.println("Tech Weights for " + cs.getCountry().name + " in " + game.localisation.translate(area.getName()) + ":");
            techsInArea.forEach(p -> {
                int level = 1 + cs.getCountry().techStatus.getTechLevel(p.getOne().name());
                String levelString = p.getOne().isRepeatable() ? ", level=" + level + "/" + p.getOne().levels() : "";
                System.out.printf(Locale.ROOT, "%s (%s, tier=%d, cost=%d%s) W: %.1f (%.1f%%)%n", game.localisation.translate(p.getOne().name()), p.getOne().name(), p.getOne().tier(), p.getOne().cost(cs), levelString, p.getTwo(), 100 * p.getTwo() / totalWeight);
            });
            System.out.println();
        }

        System.out.println("Blocked Techs:");
        for (Technology.Area area : Technology.Area.values()) {
            var blockedTechsInArea = blockedWeightedTechs.get(area)
                    .toSortedList(Comparator.comparing(ObjectDoublePair<Technology>::getTwo).reversed());

            System.out.println();
            System.out.println("Blocked Tech Weights for " + cs.getCountry().name + " in " + game.localisation.translate(area.getName()) + ":");
            blockedTechsInArea.forEach(p -> {
                int level = 1 + cs.getCountry().techStatus.getTechLevel(p.getOne().name());
                String levelString = p.getOne().isRepeatable() ? ", level=" + level + "/" + p.getOne().levels() : "";

                var missingPrereqs = p.getOne().prerequisites().select(prereq -> !cs.getCountry().techStatus.hasTech(prereq));
                String missingPrereqsString = missingPrereqs.notEmpty() ? " missing_prereqs=" + missingPrereqs : "";

                int previousTierRequired = game.common.technologyTiers.tiers.get(p.getOne().tier()).previouslyUnlocked;
                int alreadyUnlocked = (int) cs.getCountry().techStatus.technologies.keySet().stream()
                        .map(game.common.technologies::get)
                        .filter(t -> t.area() == p.getOne().area())
                        .filter(t -> t.tier() == p.getOne().tier() - 1)
                        .count();
                String tierString = alreadyUnlocked < previousTierRequired ? " previous_tier=" + alreadyUnlocked + "/" + previousTierRequired : "";

                System.out.printf(Locale.ROOT, "%s (%s, tier=%d, cost=%d%s)%s%s weight=%.1f%n", game.localisation.translate(p.getOne().name()), p.getOne().name(), p.getOne().tier(), p.getOne().cost(cs), levelString, missingPrereqsString, tierString, p.getTwo());
            });
            System.out.println();
        }
    }

    private static boolean anyPrerequisitesUnavailable(StellarisGame game, CountryScope cs, Technology t) {
        if (!t.hasPotential(cs) || t.getBaseWeight() <= 0) {
            return true;
        }

        return t.prerequisites().stream()
                .filter(name -> !cs.getCountry().techStatus.hasTech(name))
                .map(game.common.technologies::get)
                .anyMatch(prereq -> anyPrerequisitesUnavailable(game, cs, prereq));
    }

    private static final class TestProgressMessageUpdater implements ProgressMessageUpdater {

        private final MutableList<StopWatch> stopWatches = Lists.mutable.empty();

        @Override
        public void updateProgressMessage(int index, boolean visible, int workDone, int totalWork, String msg) {
            String indent = "  ".repeat(index);
            int last = stopWatches.size() - 1;
            StopWatch current;
            if (last >= index) {
                for (; last > index; last--) {
                    stopWatches.remove(last);
                }

                current = stopWatches.get(last);
            } else if (last == index - 1) {
                current = StopWatch.create();
                stopWatches.add(current);
            } else {
                throw new IllegalArgumentException();
            }

            if (current.isRunning()) {
                System.out.println(indent + "...done (" + DurationFormatter.format(current.stop()) + ")");
            }

            System.out.println(indent + msg);
            current.start();
        }
    }
}
