package io.github.ititus.pdx;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.user.StellarisUserData;
import io.github.ititus.pdx.stellaris.user.save.*;
import io.github.ititus.pdx.util.collection.CollectionUtil;
import io.github.ititus.pdx.util.collection.Pair;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final String USER_HOME = System.getProperty("user.home");
    private static final String[] TEST_FILES = {USER_HOME + "/Desktop/test.txt"};
    private static final String INSTALL_DIR = "D:/Miles/Programme/Steam/SteamApps/common/Stellaris";
    private static final String USER_DATA_DIR = USER_HOME + "/Documents/Paradox Interactive/Stellaris";

    private static final String SAVE_FOLDER = "mpomnidirective_20173703";
    private static final String SAVE_GAME = "2270.04.10";

    public static void main(String[] args) {
        List<IPdxScript> testScripts = Arrays.stream(TEST_FILES).map(File::new).map(PdxScriptParser::parse).collect(Collectors.toList());
        List<String> testOutput = testScripts.stream().map(IPdxScript::toPdxScript).collect(Collectors.toList());
        testOutput.forEach(System.out::println);
        System.out.println("done1");

        long time = System.currentTimeMillis();
        StellarisGame game = new StellarisGame(INSTALL_DIR);
        StellarisUserData userData = new StellarisUserData(USER_DATA_DIR);

        List<Pair<String, Throwable>> gameErrors = game != null && game.getRawDataLoader() != null ? game.getRawDataLoader().getErrors() : null;
        Map<String, Map<String, String>> missingLocalisation = game != null && game.getLocalisation() != null ? game.getLocalisation().getMissingLocalisation() : null;
        Map<String, Map<String, String>> extraLocalisation = game != null && game.getLocalisation() != null ? game.getLocalisation().getExtraLocalisation() : null;
        String gameString = game != null && game.getRawDataLoader() != null ? game.getRawDataLoader().getRawData().toPdxScript() : null;
        String gameLocalisationString = game != null && game.getLocalisation() != null ? game.getLocalisation().toYML() : null;

        List<Pair<String, Throwable>> userErrors = userData != null && userData.getRawDataLoader() != null ? userData.getRawDataLoader().getErrors() : null;
        List<Pair<String, Throwable>> saveErrors = userData != null && userData.getSaves() != null ? userData.getSaves().getErrors() : null;
        String userDataString = userData != null && userData.getRawDataLoader() != null ? userData.getRawDataLoader().getRawData().toPdxScript() : null;

        List<String> unknownLiterals = PdxScriptParser.getUnknownLiterals();

        System.out.println((System.currentTimeMillis() - time) / 1000D + " s");
        System.out.println("done2");

        System.out.println("-------------------------");
        StellarisSave stellarisSave = userData != null && userData.getSaves() != null ? userData.getSaves().getSave(SAVE_FOLDER, SAVE_GAME) : null;
        if (stellarisSave != null) {
            GalacticObjects systems = stellarisSave.getGameState().getGalacticObjects();

            List<Pair<GalacticObject, Resources>> resourcesInSystems = systems
                    .getGalacticObjects()
                    .values()
                    .stream()
                    .map(system -> Pair.of(system, getResources(stellarisSave.getGameState(), system)))
                    .collect(Collectors.toList());

            List<Pair<Function<Resources, List<Double>>, String>> list = CollectionUtil.listOf(
                    Pair.of(Resources::getMinerals, "minerals"),
                    Pair.of(Resources::getEnergy, "energy"),
                    Pair.of(r -> combineResource(r.getMinerals(), r.getEnergy()), "resources"),
                    Pair.of(Resources::getPhysicsResearch, "physics"),
                    Pair.of(Resources::getSocietyResearch, "society"),
                    Pair.of(Resources::getEngineeringResearch, "engineering"),
                    Pair.of(r -> combineResource(r.getPhysicsResearch(), combineResource(r.getSocietyResearch(), r.getEngineeringResearch())), "research")
            );
            list.forEach(p -> {
                Comparator<Pair<GalacticObject, Resources>> sorter = Comparator.comparingDouble((Pair<GalacticObject, Resources> resourcesInSystem) -> p.getKey().apply(resourcesInSystem.getValue()).get(0)).reversed();
                List<Pair<GalacticObject, Resources>> resourceRichSystems =
                        resourcesInSystems
                                .stream()
                                .sorted(sorter)
                                .limit(10)
                                .collect(Collectors.toList());

                resourceRichSystems.stream().map(pair -> pair.getKey().getName() + ": " + p.getKey().apply(pair.getValue()).get(0) + " " + p.getValue()).forEachOrdered(System.out::println);
                System.out.println("-------------------------");
            });

            System.out.println((System.currentTimeMillis() - time) / 1000D + " s");
            System.out.println("done3");
            Map<String, Set<String>> errors = stellarisSave.getErrors();

            System.out.println("-------------------------");
            errors.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).map(p -> p.getKey() + " = " + p.getValue()).forEachOrdered(System.out::println);
        }

        System.out.println((System.currentTimeMillis() - time) / 1000D + " s");
        System.out.println("done4");
    }

    private static Resources getResources(GameState gameState, GalacticObject system) {
        Planets planets = gameState.getPlanets();
        List<Integer> systemPlanets = system.getPlanets();

        return systemPlanets
                .stream()
                .map(planets.getPlanets()::get)
                .flatMap(planet -> Stream.concat(Stream.of(planet), planet.getMoons().stream().map(planets.getPlanets()::get)))
                .flatMap(planet -> Planet.habitablePlanetClasses.contains(planet.getPlanetClass()) ? null : planet.getTiles().getTiles().values().stream())
                .map(Tile::getResources)
                .reduce(
                        new Resources(),
                        (acc, r) -> new Resources(
                                combineResource(acc.getEnergy(), r.getEnergy()),
                                combineResource(acc.getMinerals(), r.getMinerals()),
                                combineResource(acc.getFood(), r.getFood()),
                                combineResource(acc.getPhysicsResearch(), r.getPhysicsResearch()),
                                combineResource(acc.getSocietyResearch(), r.getSocietyResearch()),
                                combineResource(acc.getEngineeringResearch(), r.getEngineeringResearch()),
                                combineResource(acc.getInfluence(), r.getInfluence()),
                                combineResource(acc.getUnity(), r.getUnity()),
                                combineResource(acc.getAldar(), r.getAldar()),
                                combineResource(acc.getDarkMatter(), r.getDarkMatter()),
                                combineResource(acc.getEngos(), r.getEngos()),
                                combineResource(acc.getGaranthium(), r.getGaranthium()),
                                combineResource(acc.getLivingMetal(), r.getLivingMetal()),
                                combineResource(acc.getLythuric(), r.getLythuric()),
                                combineResource(acc.getOrillium(), r.getOrillium()),
                                combineResource(acc.getPitharan(), r.getPitharan()),
                                combineResource(acc.getSatramene(), r.getSatramene()),
                                combineResource(acc.getTeldar(), r.getTeldar()),
                                combineResource(acc.getTerraformGases(), r.getTerraformGases()),
                                combineResource(acc.getTerraformLiquids(), r.getTerraformLiquids()),
                                combineResource(acc.getYurantic(), r.getYurantic()),
                                combineResource(acc.getZro(), r.getZro()),
                                combineResource(acc.getAlienPets(), r.getAlienPets()),
                                combineResource(acc.getBetharian(), r.getBetharian())
                        )
                );
    }

    private static List<Double> combineResource(List<Double> l1, List<Double> l2) {
        checkResourceLists(l1);
        checkResourceLists(l2);
        return CollectionUtil.listOf((l1.size() > 0 ? l1.get(0) : 0.0) + (l2.size() > 0 ? l2.get(0) : 0.0));
    }

    private static void checkResourceLists(List<Double> list) {
        if (list.size() >= 2) {
            if (!list.get(0).equals(list.get(1))) {
                throw new IllegalArgumentException();
            }
            if (list.size() >= 3) {
                if (!list.get(2).equals(0.0)) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }
}
