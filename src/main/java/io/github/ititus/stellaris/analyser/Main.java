package io.github.ititus.stellaris.analyser;

import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptParser;
import io.github.ititus.stellaris.analyser.save.*;
import io.github.ititus.stellaris.analyser.util.CollectionUtil;
import io.github.ititus.stellaris.analyser.util.Pair;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final String SAVE = "C:\\Users\\Vella\\Documents\\Paradox Interactive\\Stellaris\\save games\\mpomnidirective_20173703";
    private static final String[] TEST_FILES = {"C:\\Users\\Vella\\Desktop\\test.txt"/*, "C:\\Users\\Vella\\Documents\\Paradox Interactive\\Stellaris\\settings.txt", "C:\\Users\\Vella\\Documents\\Paradox Interactive\\Stellaris\\user_empire_designs.txt"*/};

    public static void main(String[] args) {


        List<PdxScriptObject> testScripts = Arrays.stream(TEST_FILES).map(File::new).map(PdxScriptParser::parse).collect(Collectors.toList());
        for (PdxScriptObject testScript : testScripts) {
            System.out.println(testScript.toPdxScript(0, false, false));
        }
        System.out.println("done");

        System.exit(0);

        StellarisSave stellarisSave = StellarisSave.loadNewest(SAVE);
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
        for (Pair<Function<Resources, List<Double>>, String> p : list) {
            Comparator<Pair<GalacticObject, Resources>> sorter = Comparator.comparingDouble((Pair<GalacticObject, Resources> resourcesInSystem) -> p.getKey().apply(resourcesInSystem.getValue()).get(0)).reversed();
            List<Pair<GalacticObject, Resources>> resourceRichSystems =
                    resourcesInSystems
                            .stream()
                            .sorted(sorter)
                            .limit(10)
                            .collect(Collectors.toList());

            resourceRichSystems.forEach(pair -> System.out.println(pair.getKey().getName() + ": " + p.getKey().apply(pair.getValue()).get(0) + " " + p.getValue()));
            System.out.println("-------------------------");
        }

        System.out.println("done2");
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
                        new Resources(CollectionUtil.listOf(0.0), CollectionUtil.listOf(0.0), CollectionUtil.listOf(0.0), CollectionUtil.listOf(0.0), CollectionUtil.listOf(0.0), CollectionUtil.listOf(0.0), CollectionUtil.listOf(0.0), CollectionUtil.listOf(0.0)),
                        (acc, r) -> new Resources(
                                combineResource(acc.getEnergy(), r.getEnergy()),
                                combineResource(acc.getMinerals(), r.getMinerals()),
                                combineResource(acc.getFood(), r.getFood()),
                                combineResource(acc.getPhysicsResearch(), r.getPhysicsResearch()),
                                combineResource(acc.getSocietyResearch(), r.getSocietyResearch()),
                                combineResource(acc.getEngineeringResearch(), r.getEngineeringResearch()),
                                combineResource(acc.getInfluence(), r.getInfluence()),
                                combineResource(acc.getUnity(), r.getUnity())
                        ));
    }

    private static List<Double> combineResource(List<Double> l1, List<Double> l2) {
        if (l1.size() >= 2) {
            if (!l1.get(0).equals(l1.get(1))) {
                throw new IllegalArgumentException();
            }
            if (l1.size() >= 3) {
                if (!l1.get(2).equals(0.0)) {
                    throw new IllegalArgumentException();
                }
            }
        }
        if (l2.size() >= 2) {
            if (!l2.get(0).equals(l2.get(1))) {
                throw new IllegalArgumentException();
            }
            if (l2.size() >= 3) {
                if (!l2.get(2).equals(0.0)) {
                    throw new IllegalArgumentException();
                }
            }
        }
        return CollectionUtil.listOf((l1.size() > 0 ? l1.get(0) : 0.0) + (l2.size() > 0 ? l2.get(0) : 0.0));
    }
}
