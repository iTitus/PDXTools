package io.github.ititus.stellaris.viewer;

import io.github.ititus.ddsfx.DdsFx;
import io.github.ititus.io.PathUtil;
import io.github.ititus.math.vector.Vec2f;
import io.github.ititus.math.vector.Vec3f;
import io.github.ititus.math.vector.Vec3i;
import io.github.ititus.pdx.pdxasset.IPdxAsset;
import io.github.ititus.pdx.pdxasset.PdxMesh;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.VertexFormat;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ModelViewer extends Application {

    private static final Path INSTALL_DIR = PathUtil.resolveRealDir(Path.of("C:/Program Files (x86)/Steam/steamapps/common/Stellaris"));
    private static final Path OUT_DIR = PathUtil.createOrResolveRealDir(Path.of(System.getProperty("user.home"), "Desktop/pdx/dds_out"));
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
            "gfx/interface/icons/buildings/building_capital",
            "gfx/interface/icons/districts/district_city",
            /* DXT3 */
            /*"gfx/interface/fleet_view/fleet_view_upgradable_design",*/
            "gfx/interface/galacticCommunity/old/vote_freeze_resolution",
            /* DXT1 */
            "gfx/event_pictures/space_dragon_blue",
            /* Model comparison */
            "gfx/models/planets/continental_01_diffuse", "gfx/models/planets/continental_01_normals", "gfx/models/planets/continental_01_spec"//,
            // "gfx/models/planets/poles_1_diffuse", "gfx/models/planets/poles_1_normal", "gfx/models/planets/poles_1_specular"
    };

    private static Image loadImage(Path path) {
        try {
            return new Image(path.toUri().toURL().toString(), true);
        } catch (MalformedURLException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        DdsFx.setup();
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);

        int n = 0;
        for (String image : IMAGES) {
            Image img1 = loadImage(INSTALL_DIR.resolve(image + ".dds"));
            if (img1.isError()) {
                throw new RuntimeException(img1.getException());
            }

            Image img2 = loadImage(OUT_DIR.resolve(image + ".png"));
            if (img2.isError()) {
                throw new RuntimeException(img2.getException());
            }

            ImageView view1 = new ImageView(img1);
            ImageView view2 = new ImageView(img2);
            grid.add(view1, 0, n);
            grid.add(view2, 1, n++);
        }

        ScrollPane sp = new ScrollPane(grid);
        SubScene sub2d = new SubScene(sp, 500, 500);

        ModelView v = new ModelView();

        v.show(
                //IPdxAsset.load(INSTALL_DIR.resolve("gfx/models/ships/humanoid_01/humanoid_01_battleship_frame.mesh")),
                //IPdxAsset.load(INSTALL_DIR.resolve("gfx/models/ships/humanoid_01/humanoid_01_battleship_bow_XL1.mesh"))//,
                //IPdxAsset.load(INSTALL_DIR.resolve("gfx/models/ships/humanoid_01/humanoid_01_battleship_mid_L3.mesh")),
                //IPdxAsset.load(INSTALL_DIR.resolve("gfx/models/ships/humanoid_01/humanoid_01_battleship_stern_L1.mesh"))
                IPdxAsset.load(INSTALL_DIR.resolve("gfx/models/planets/planet_clouded.mesh"))
        );

        HBox hb = new HBox(sub2d, v);
        Scene scene = new Scene(hb);

        primaryStage.setTitle("Model Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static class ModelView extends SubScene {

        private final Group content;
        private final PerspectiveCamera camera;

        public ModelView() {
            super(new Group(), 500, 500, true, SceneAntialiasing.BALANCED);

            setFill(Color.BLACK);

            Group root = new Group();
            setRoot(root);

            this.camera = new PerspectiveCamera(true);
            camera.setFieldOfView(35);
            camera.setNearClip(0.1);
            camera.setFarClip(1000);
            camera.setTranslateZ(-25);
            root.getChildren().add(camera);
            setCamera(camera);

            this.content = new Group(new Sphere(10));
            root.getChildren().add(content);

            Rotate rotate = new Rotate(0, new Point3D(0, 1, 0));
            Rotate rotate2 = new Rotate(0, new Point3D(1, 1, 0).normalize());
            content.getTransforms().addAll(rotate, rotate2);

            Timeline timeline1 = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                    new KeyFrame(Duration.seconds(8), new KeyValue(rotate.angleProperty(), 360))
            );
            timeline1.setCycleCount(Animation.INDEFINITE);
            timeline1.play();
            Timeline timeline2 = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(rotate2.angleProperty(), 0)),
                    new KeyFrame(Duration.seconds(17), new KeyValue(rotate2.angleProperty(), 360))
            );
            timeline2.setCycleCount(Animation.INDEFINITE);
            timeline2.play();

            AmbientLight al = new AmbientLight(Color.WHITE.darker().darker());
            al.getScope().add(content);
            root.getChildren().add(al);

            PointLight pl = new PointLight(Color.WHITE);
            Translate t = new Translate(10, 0, -25);
            Rotate rX = new Rotate();
            Rotate rY = new Rotate();
            lookAt(Point3D.ZERO, rX, rY, t);
            pl.getTransforms().addAll(t, rX, rY);
            pl.getScope().add(content);
            root.getChildren().add(pl);
        }

        private static Group generate(PdxMesh pdxMesh) {
            List<Node> meshViews = new ArrayList<>();

            for (PdxMesh.Mesh mesh : pdxMesh.meshes) {
                if ("Collision".equals(mesh.material().shader()) || "PdxMeshAtmosphere".equals(mesh.material().shader())) {
                    continue;
                }

                TriangleMesh fxMesh = new TriangleMesh(VertexFormat.POINT_NORMAL_TEXCOORD);

                for (Vec3f vertex : mesh.vertices()) {
                    fxMesh.getPoints().addAll(vertex.x(), vertex.y(), vertex.z());
                }

                if (mesh.normals() != null) {
                    for (Vec3f normal : mesh.normals()) {
                        fxMesh.getNormals().addAll(normal.x(), normal.y(), normal.z());
                    }
                } else {
                    fxMesh.setVertexFormat(VertexFormat.POINT_TEXCOORD);
                }

                for (Vec2f uv : mesh.uv0()) {
                    fxMesh.getTexCoords().addAll(uv.x(), uv.y());
                }

                for (Vec3i triangle : mesh.triangles()) {
                    fxMesh.getFaces().addAll(
                            triangle.x(), triangle.x(), triangle.x(),
                            triangle.y(), triangle.y(), triangle.y(),
                            triangle.z(), triangle.z(), triangle.z()
                    );
                }

                MeshView meshView = new MeshView(fxMesh);

                PhongMaterial mat = new PhongMaterial();
                if (mesh.material().shader() != null) {
                    System.out.println("shader: " + mesh.material().shader());
                }
                if (mesh.material().diffuse() != null) {
                    Path p = pdxMesh.path.resolveSibling(mesh.material().diffuse());
                    System.out.println("diffuse: " + INSTALL_DIR.relativize(p).toString().replace(File.separatorChar, '/'));
                    mat.setDiffuseMap(loadImage(p));
                }
                if (mesh.material().normal() != null) {
                    Path p = pdxMesh.path.resolveSibling(mesh.material().normal());
                    System.out.println("normal: " + INSTALL_DIR.relativize(p).toString().replace(File.separatorChar, '/'));
                    mat.setBumpMap(loadImage(p));
                }
                if (mesh.material().specular() != null) {
                    Path p = pdxMesh.path.resolveSibling(mesh.material().specular());
                    System.out.println("specular: " + INSTALL_DIR.relativize(p).toString().replace(File.separatorChar, '/'));
                    mat.setSpecularMap(loadImage(p));
                }
                meshView.setMaterial(mat);

                meshViews.add(meshView);
            }

            return new Group(meshViews);
        }

        public void show(IPdxAsset... pdxMeshes) {
            Group all = new Group();
            for (IPdxAsset asset : pdxMeshes) {
                if (asset instanceof PdxMesh pdxMesh) {
                    all.getChildren().add(generate(pdxMesh));
                }
            }

            content.getChildren().clear();
            content.getChildren().add(all);
        }
    }

    private static void lookAt(Point3D lookAtPos, Rotate rX, Rotate rY, Translate translate) {
        Point3D camDirection = lookAtPos.subtract(translate.getX(), translate.getY(), translate.getZ()).normalize();

        double xRotation = Math.toDegrees(Math.asin(-camDirection.getY()));
        double yRotation = Math.toDegrees(Math.atan2(camDirection.getX(), camDirection.getZ()));

        rX.setAngle(xRotation);
        rX.setPivotX(translate.getX());
        rX.setPivotY(translate.getY());
        rX.setPivotZ(translate.getZ());
        rX.setAxis(Rotate.X_AXIS);

        rY.setAngle(yRotation);
        rY.setPivotX(translate.getX());
        rY.setPivotY(translate.getY());
        rY.setPivotZ(translate.getZ());
        rY.setAxis(Rotate.Y_AXIS);
    }
}
