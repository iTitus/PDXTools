package io.github.ititus.pdx;

import com.koloboke.collect.map.ObjObjMap;
import com.koloboke.collect.set.hash.HashObjSet;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.user.StellarisUserData;
import io.github.ititus.pdx.stellaris.user.save.*;
import io.github.ititus.pdx.util.Pair;
import io.github.ititus.pdx.util.Tuple3D;
import io.github.ititus.pdx.util.Util;
import io.github.ititus.pdx.util.collection.CollectionUtil;
import io.github.ititus.pdx.util.collection.CollectorImpl;

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
        ObjObjMap<String, ObjObjMap<String, String>> missingLocalisation = game != null && game.getLocalisation() != null ? game.getLocalisation().getMissingLocalisation() : null;
        ObjObjMap<String, ObjObjMap<String, String>> extraLocalisation = game != null && game.getLocalisation() != null ? game.getLocalisation().getExtraLocalisation() : null;
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

            List<Pair<Function<Resources, Tuple3D>, String>> list = CollectionUtil.listOf(
                    Pair.of(Resources::getMinerals, "minerals"),
                    Pair.of(Resources::getEnergy, "energy"),
                    Pair.of(r -> combineResource(r.getMinerals(), r.getEnergy()), "resources"),
                    Pair.of(Resources::getPhysicsResearch, "physics"),
                    Pair.of(Resources::getSocietyResearch, "society"),
                    Pair.of(Resources::getEngineeringResearch, "engineering"),
                    Pair.of(r -> combineResource(r.getPhysicsResearch(), combineResource(r.getSocietyResearch(), r.getEngineeringResearch())), "research")
            );
            list.forEach(p -> {
                Comparator<Pair<GalacticObject, Resources>> sorter = Comparator.comparingDouble((Pair<GalacticObject, Resources> resourcesInSystem) -> p.getKey().apply(resourcesInSystem.getValue()).getD1()).reversed();
                List<Pair<GalacticObject, Resources>> resourceRichSystems =
                        resourcesInSystems
                                .stream()
                                .sorted(sorter)
                                .limit(10)
                                .collect(Collectors.toList());

                resourceRichSystems.stream().map(pair -> pair.getKey().getName() + ": " + p.getKey().apply(pair.getValue()).getD1() + " " + p.getValue()).forEachOrdered(System.out::println);
                System.out.println("-------------------------");
            });

            System.out.println((System.currentTimeMillis() - time) / 1000D + " s");
            System.out.println("done3");
            ObjObjMap<String, HashObjSet<String>> errors = stellarisSave.getErrors();

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
                .filter(Objects::nonNull)
                .collect(new CollectorImpl<>(
                        () -> new double[24],
                        (array, r) -> {
                            int i = 0;
                            array[i++] += r.getEnergy().getD1();
                            array[i++] += r.getMinerals().getD1();
                            array[i++] += r.getFood().getD1();
                            array[i++] += r.getPhysicsResearch().getD1();
                            array[i++] += r.getSocietyResearch().getD1();
                            array[i++] += r.getEngineeringResearch().getD1();
                            array[i++] += r.getInfluence().getD1();
                            array[i++] += r.getUnity().getD1();
                            array[i++] += r.getAldar().getD1();
                            array[i++] += r.getDarkMatter().getD1();
                            array[i++] += r.getEngos().getD1();
                            array[i++] += r.getGaranthium().getD1();
                            array[i++] += r.getLivingMetal().getD1();
                            array[i++] += r.getLythuric().getD1();
                            array[i++] += r.getOrillium().getD1();
                            array[i++] += r.getPitharan().getD1();
                            array[i++] += r.getSatramene().getD1();
                            array[i++] += r.getTeldar().getD1();
                            array[i++] += r.getTerraformGases().getD1();
                            array[i++] += r.getTerraformLiquids().getD1();
                            array[i++] += r.getYurantic().getD1();
                            array[i++] += r.getZro().getD1();
                            array[i++] += r.getAlienPets().getD1();
                            array[i] += r.getBetharian().getD1();
                        },
                        Util::addArrays,
                        Resources::new,
                        CollectionUtil.CH_NOID
                ));
    }

    private static Tuple3D combineResource(Tuple3D t1, Tuple3D t2) {
        return Tuple3D.of(t1.getD1() + t2.getD1(), 0, 0);
    }
}
