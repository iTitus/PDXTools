package io.github.ititus.pdx;

import io.github.ititus.ddsfx.DdsFx;
import io.github.ititus.pdx.pdxasset.PdxMesh;
import io.github.ititus.pdx.util.IOUtil;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.VertexFormat;
import javafx.stage.Stage;

import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ModelViewer extends Application {

    private static final Path INSTALL_DIR = IOUtil.resolveRealDir(Path.of("C:/Program Files (x86)/Steam/steamapps/common/Stellaris"));
    private static final Path OUT_DIR = IOUtil.createOrResolveRealDir(Path.of(System.getProperty("user.home"), "Desktop/pdx/dds_out"));
    private static final String[] IMAGES = {
            /* A8R8G8B8 */
            "flags/backgrounds/diagonal_stripe",
            /* R8G8B8 */
            "flags/backgrounds/circle",
            /* A1R5G5B5 */
            "gfx/interface/icons/dlc/ancient_relics_big",
            /* A8B8G8R8 */
            // "gfx/interface/buttons/standard_button_200_24_dlc_overlay_animated",
            /* DXT5 */
            /*"flags/human/flag_human_1",*/
            "gfx/interface/icons/districts/district_city",
            /* DXT3 */
            /*"gfx/interface/fleet_view/fleet_view_upgradable_design",*/
            "gfx/interface/galacticCommunity/old/vote_freeze_resolution",
            /* DXT1 */
            "gfx/event_pictures/space_dragon_blue"
    };

    private static Image loadImage(Path path) {
        try {
            return new Image(path.toUri().toURL().toString(), false);
        } catch (MalformedURLException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Group generate(PdxMesh pdxMesh) {
        List<Node> meshViews = new ArrayList<>();

        for (PdxMesh.Mesh mesh : pdxMesh.meshes) {
            TriangleMesh fxMesh = new TriangleMesh(VertexFormat.POINT_NORMAL_TEXCOORD);
            MeshView meshView = new MeshView(fxMesh);

            PhongMaterial mat = new PhongMaterial();
            //mat.setDiffuseMap(new Image());
            //meshView.setMaterial();
        }

        return new Group(meshViews);
    }

    @Override
    public void init() throws Exception {
        super.init();
        DdsFx.setup();
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();

        int n = 0;
        for (String image : IMAGES) {
            Image img1 = loadImage(INSTALL_DIR.resolve(image + ".dds"));
            Image img2 = loadImage(OUT_DIR.resolve(image + ".png"));
            ImageView view1 = new ImageView(img1);
            ImageView view2 = new ImageView(img2);
            grid.add(view1, 0, n);
            grid.add(view2, 1, n++);
        }

        ScrollPane sp = new ScrollPane(grid);
        Scene scene = new Scene(sp, 500, 500/*, true*/);

        primaryStage.setTitle("Model Viewer");
        /*PerspectiveCamera camera = new PerspectiveCamera(true);
        scene.setCamera(camera);*/
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
