package io.github.ititus.pdx.stellaris;

import io.github.ititus.math.time.DurationFormatter;
import io.github.ititus.math.time.StopWatch;
import io.github.ititus.pdx.Main;
import io.github.ititus.pdx.pdxscript.PdxScriptParser;
import io.github.ititus.pdx.stellaris.game.StellarisGame;
import io.github.ititus.pdx.stellaris.user.StellarisUserData;
import io.github.ititus.pdx.stellaris.user.save.StellarisSave;
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
import org.eclipse.collections.api.tuple.Pair;

import java.nio.file.Path;

public class StellarisSaveAnalyser implements Runnable {

    private static final Path USER_HOME = Path.of(System.getProperty("user.home"));
    private static final Path INSTALL_DIR = Path.of("C:", "Program Files (x86)", "Steam", "steamapps", "common", "Stellaris");
    private static final Path USER_DATA_DIR = USER_HOME.resolve("Documents/Paradox Interactive/Stellaris");

    private static final String SAVE_FOLDER = "mpempireofthebugs_847499604";
    private static final String SAVE_GAME = "2248.02.26";

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

    @Override
    public void run() {
        StopWatch s = StopWatch.createRunning();
        int steps = 4;

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

        updateProgressMessage(0, true, 0, steps, "Loading Game Data");

        game = new StellarisGame(INSTALL_DIR, 1, this::updateProgressMessage);
        String gameLocalisationString = game != null && game.getLocalisation() != null ?
                game.getLocalisation().toYML() : null;
        String gameString = game != null && game.getRawDataLoader() != null ?
                game.getRawDataLoader().getRawData().toPdxScript() : null;
        updateProgressMessage(0, true, 1, steps, "Loading User Data");

        userData = new StellarisUserData(USER_DATA_DIR, 1, this::updateProgressMessage);
        String userDataString = userData != null && userData.getRawDataLoader() != null ?
                userData.getRawDataLoader().getRawData().toPdxScript() : null;
        updateProgressMessage(0, true, 2, steps, "Analysing Save Game");

        stellarisSave = userData != null && userData.getSaves() != null ? userData.getSaves().getSave(SAVE_FOLDER, SAVE_GAME) : null;

        updateProgressMessage(0, true, 3, steps, "Gathering Errors");

        int errorSteps = 3;

        updateProgressMessage(1, true, 0, errorSteps, "Gathering Unknown Literals");
        ImmutableList<String> unknownLiterals = PdxScriptParser.getUnknownLiterals();

        updateProgressMessage(1, true, 1, errorSteps, "Gathering Game Errors");

        int gameErrorSteps = 2;

        updateProgressMessage(2, true, 0, gameErrorSteps, "Gathering Game Errors");
        ImmutableList<Pair<String, Throwable>> gameErrors = game != null && game.getRawDataLoader() != null ?
                game.getRawDataLoader().getErrors() : null;

        updateProgressMessage(2, true, 1, gameErrorSteps, "Gathering Localisation Errors");

        int localisationErrorSteps = 2;

        updateProgressMessage(3, true, 0, localisationErrorSteps, "Gathering Missing Localisations");
        ImmutableMap<String, ImmutableMap<String, String>> missingLocalisation =
                game != null && game.getLocalisation() != null ? game.getLocalisation().getMissingLocalisation() : null;

        updateProgressMessage(3, true, 1, localisationErrorSteps, "Gathering Extra Localisations");
        ImmutableMap<String, ImmutableMap<String, String>> extraLocalisation =
                game != null && game.getLocalisation() != null ? game.getLocalisation().getExtraLocalisation() : null;

        updateProgressMessage(3, false, 3, localisationErrorSteps, "Done");

        updateProgressMessage(2, false, 2, gameErrorSteps, "Done");

        updateProgressMessage(1, true, 2, errorSteps, "Gathering User Errors");

        int userErrorSteps = 2;

        updateProgressMessage(2, true, 0, userErrorSteps, "Gathering User Errors");
        ImmutableList<Pair<String, Throwable>> userErrors = userData != null && userData.getRawDataLoader() != null ?
                userData.getRawDataLoader().getErrors() : null;

        updateProgressMessage(2, true, 1, userErrorSteps, "Gathering Save Errors");

        int saveErrorSteps = 2;

        updateProgressMessage(3, true, 0, saveErrorSteps, "Gathering Save Errors");
        ImmutableList<Pair<String, Throwable>> saveErrors = userData != null && userData.getSaves() != null ?
                userData.getSaves().getErrors() : null;

        updateProgressMessage(3, true, 1, saveErrorSteps, "Gathering Save Parsing Errors");
        ImmutableList<String> saveParseErrors = stellarisSave != null ? stellarisSave.getErrors() : null;
        /*if (saveParseErrors != null) {
            saveParseErrors.forEach(System.out::println);
        }*/

        updateProgressMessage(3, false, 2, saveErrorSteps, "Done");

        updateProgressMessage(2, false, 2, userErrorSteps, "Done");

        updateProgressMessage(1, false, 3, errorSteps, "Done");

        updateProgressMessage(0, false, 4, steps, "Done");

        System.out.println("Time: " + DurationFormatter.formatSeconds(s.stop()));
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
