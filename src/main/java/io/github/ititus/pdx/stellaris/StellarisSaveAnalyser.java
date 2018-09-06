package io.github.ititus.pdx.stellaris;

import io.github.ititus.pdx.Main;
import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.user.StellarisUserData;
import io.github.ititus.pdx.stellaris.user.save.*;
import io.github.ititus.pdx.util.Util;
import io.github.ititus.pdx.util.collection.CollectionUtil;
import io.github.ititus.pdx.util.collection.Tuple3D;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.multimap.ImmutableMultimap;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.tuple.Tuples;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StellarisSaveAnalyser implements Runnable {

    private static final String USER_HOME = System.getProperty("user.home");
    private static final String[] TEST_FILES = {USER_HOME + "/Desktop/test.txt"};
    private static final String INSTALL_DIR = "D:/Miles/Programme/Steam/SteamApps/common/Stellaris";
    private static final String USER_DATA_DIR = USER_HOME + "/Documents/Paradox Interactive/Stellaris";

    private static final String SAVE_FOLDER = "mpomnidirective_20173703";
    private static final String SAVE_GAME = "mature_save"; // "2270.04.10";

    private static final int PROGRESS_DEPTH = 4;

    private final ReadOnlyBooleanWrapper[] visibilityProps = new ReadOnlyBooleanWrapper[PROGRESS_DEPTH];
    private final ReadOnlyDoubleWrapper[] progressProps = new ReadOnlyDoubleWrapper[PROGRESS_DEPTH];
    private final ReadOnlyStringWrapper[] messageProps = new ReadOnlyStringWrapper[PROGRESS_DEPTH];

    private final Main main;
    private StellarisGame game;
    private StellarisUserData userData;
    private StellarisSave stellarisSave;

    public StellarisSaveAnalyser(Main main) {
        this.main = main;
    }

    private static Resources getResources(GameState gameState, GalacticObject system) {
        Planets planets = gameState.getPlanets();
        return CollectionUtil.stream(system.getPlanets())
                .mapToObj(planets.getPlanets()::get)
                // .flatMap(planet -> Stream.concat(Stream.of(planet), planet.getMoons().collect(planets.getPlanets()::get).stream()))
                .flatMap(planet -> Planet.habitablePlanetClasses.contains(planet.getPlanetClass()) ? null : planet.getTiles().getTiles().values().stream())
                .map(Tile::getResources)
                .filter(Objects::nonNull)
                .collect(Collector.of(
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
                        Resources::of
                ));
    }

    private static Tuple3D combineResource(Tuple3D t1, Tuple3D t2) {
        return Tuple3D.of(t1.getD1() + t2.getD1());
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        int STEPS = 5;

        Platform.runLater(() -> {
            for (int i = 0; i < PROGRESS_DEPTH; i++) {
                ReadOnlyBooleanWrapper visibilityProp = visibilityProps[i] = new ReadOnlyBooleanWrapper(false);
                ReadOnlyDoubleWrapper progressProp = progressProps[i] = new ReadOnlyDoubleWrapper(-1);
                ReadOnlyStringWrapper messageProp = messageProps[i] = new ReadOnlyStringWrapper("");

                HBox hb = new HBox();
                hb.setSpacing(10);
                hb.setAlignment(Pos.CENTER_LEFT);
                hb.visibleProperty().bind(visibilityProp);
                ProgressBar pb = new ProgressBar();
                pb.progressProperty().bind(progressProp);
                Label l = new Label();
                l.textProperty().bind(messageProp);
                hb.getChildren().addAll(pb, l);
                main.vb.getChildren().add(hb);
            }
        });

        updateProgressMessage(0, true, 0, STEPS, "Running Tests");

        List<IPdxScript> testScripts = Arrays.stream(TEST_FILES).map(File::new).map(PdxScriptParser::parse).collect(Collectors.toList());
        List<String> testOutput = testScripts.stream().map(IPdxScript::toPdxScript).collect(Collectors.toList());
        testOutput.forEach(System.out::println);
        updateProgressMessage(0, true, 1, STEPS, "Loading Game Data");

        game = new StellarisGame(INSTALL_DIR, 1, this::updateProgressMessage);
        String gameLocalisationString = game != null && game.getLocalisation() != null ? game.getLocalisation().toYML() : null;
        String gameString = game != null && game.getRawDataLoader() != null ? game.getRawDataLoader().getRawData().toPdxScript() : null;
        updateProgressMessage(0, true, 2, STEPS, "Loading User Data");

        userData = new StellarisUserData(USER_DATA_DIR, 1, this::updateProgressMessage);
        String userDataString = userData != null && userData.getRawDataLoader() != null ? userData.getRawDataLoader().getRawData().toPdxScript() : null;
        updateProgressMessage(0, true, 3, STEPS, "Analysing Save Game");

        stellarisSave = userData != null && userData.getSaves() != null ? userData.getSaves().getSave(SAVE_FOLDER, SAVE_GAME) : null;
        if (stellarisSave != null) {
            GalacticObjects systems = stellarisSave.getGameState().getGalacticObjects();

            ImmutableList<Pair<GalacticObject, Resources>> resourcesInSystems = systems
                    .getGalacticObjects()
                    .values()
                    .stream()
                    .map(system -> Tuples.pair(system, getResources(stellarisSave.getGameState(), system)))
                    .collect(Collectors2.toImmutableList());

            ImmutableList<Pair<Function<Resources, Tuple3D>, String>> list = Lists.immutable.with(
                    Tuples.pair(Resources::getMinerals, "minerals"),
                    Tuples.pair(Resources::getEnergy, "energy"),
                    Tuples.pair(r -> combineResource(r.getMinerals(), r.getEnergy()), "resources"),
                    Tuples.pair(Resources::getPhysicsResearch, "physics"),
                    Tuples.pair(Resources::getSocietyResearch, "society"),
                    Tuples.pair(Resources::getEngineeringResearch, "engineering"),
                    Tuples.pair(r -> combineResource(r.getPhysicsResearch(), combineResource(r.getSocietyResearch(), r.getEngineeringResearch())), "research")
            );
            list.forEach(p -> {
                Comparator<Pair<GalacticObject, Resources>> sorter = Comparator.comparingDouble((Pair<GalacticObject, Resources> resourcesInSystem) -> p.getOne().apply(resourcesInSystem.getTwo()).getD1()).reversed();
                List<Pair<GalacticObject, Resources>> resourceRichSystems =
                        resourcesInSystems
                                .stream()
                                .sorted(sorter)
                                .limit(10)
                                .collect(Collectors.toList());

                resourceRichSystems.stream().map(pair -> pair.getOne().getName() + ": " + p.getOne().apply(pair.getTwo()).getD1() + " " + p.getTwo()).forEachOrdered(System.out::println);
                System.out.println("-------------------------");
            });
        }
        updateProgressMessage(0, true, 4, STEPS, "Gathering Errors");

        int ERROR_STEPS = 3;

        updateProgressMessage(1, true, 0, ERROR_STEPS, "Gathering Unknown Literals");
        ImmutableList<String> unknownLiterals = PdxScriptParser.getUnknownLiterals();

        updateProgressMessage(1, true, 1, ERROR_STEPS, "Gathering Game Errors");

        int GAME_ERROR_STEPS = 2;

        updateProgressMessage(2, true, 0, GAME_ERROR_STEPS, "Gathering Game Errors");
        ImmutableList<Pair<String, Throwable>> gameErrors = game != null && game.getRawDataLoader() != null ? game.getRawDataLoader().getErrors() : null;

        updateProgressMessage(2, true, 1, GAME_ERROR_STEPS, "Gathering Localisation Errors");

        int LOCALISATION_ERROR_STEPS = 2;

        updateProgressMessage(3, true, 0, LOCALISATION_ERROR_STEPS, "Gathering Missing Localisations");
        ImmutableMap<String, ImmutableMap<String, String>> missingLocalisation = game != null && game.getLocalisation() != null ? game.getLocalisation().getMissingLocalisation() : null;

        updateProgressMessage(3, true, 1, LOCALISATION_ERROR_STEPS, "Gathering Extra Localisations");
        ImmutableMap<String, ImmutableMap<String, String>> extraLocalisation = game != null && game.getLocalisation() != null ? game.getLocalisation().getExtraLocalisation() : null;

        updateProgressMessage(3, false, 3, LOCALISATION_ERROR_STEPS, "Done");

        updateProgressMessage(2, false, 2, GAME_ERROR_STEPS, "Done");

        updateProgressMessage(1, true, 2, ERROR_STEPS, "Gathering User Errors");

        int USER_ERROR_STEPS = 2;

        updateProgressMessage(2, true, 0, USER_ERROR_STEPS, "Gathering User Errors");
        ImmutableList<Pair<String, Throwable>> userErrors = userData != null && userData.getRawDataLoader() != null ? userData.getRawDataLoader().getErrors() : null;

        updateProgressMessage(2, true, 1, USER_ERROR_STEPS, "Gathering Save Errors");

        int SAVE_ERROR_STEPS = 2;

        updateProgressMessage(3, true, 0, SAVE_ERROR_STEPS, "Gathering Save Errors");
        ImmutableList<Pair<String, Throwable>> saveErrors = userData != null && userData.getSaves() != null ? userData.getSaves().getErrors() : null;

        updateProgressMessage(3, true, 1, SAVE_ERROR_STEPS, "Gathering Save Parsing Errors");
        ImmutableMultimap<String, String> saveParseErrors = stellarisSave != null ? stellarisSave.getErrors() : null;
        if (saveParseErrors != null) {
            saveParseErrors.toMap().entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).map(p -> p.getKey() + " = " + p.getValue()).forEachOrdered(System.out::println);
        }

        updateProgressMessage(3, false, 2, SAVE_ERROR_STEPS, "Done");

        updateProgressMessage(2, false, 2, USER_ERROR_STEPS, "Done");

        updateProgressMessage(1, false, 3, ERROR_STEPS, "Done");

        updateProgressMessage(0, false, 5, STEPS, "Done");

        System.out.println("Time: " + (System.currentTimeMillis() - time) / 1000D + " s");
        Platform.runLater(main::transition);
    }

    private void updateProgressMessage(int index, boolean visible, int workDone, int totalWork, String msg) {
        Platform.runLater(() -> {
            visibilityProps[index].set(visible);
            double progress = (double) workDone / totalWork;
            progressProps[index].set(progress);
            messageProps[index].set(workDone + " / " + totalWork + " (" + (int) Math.round(100 * progress) + " %) " + msg);
        });
    }

    public StellarisGame getGame() {
        return game;
    }

    public StellarisUserData getUserData() {
        return userData;
    }

    public StellarisSave getStellarisSave() {
        return stellarisSave;
    }

    @FunctionalInterface
    public interface ProgressMessageUpdater {

        void updateProgressMessage(int index, boolean visible, int workDone, int totalWork, String msg);
    }
}
