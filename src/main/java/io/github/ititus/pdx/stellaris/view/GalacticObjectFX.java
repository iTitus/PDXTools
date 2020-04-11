package io.github.ititus.pdx.stellaris.view;

import io.github.ititus.pdx.stellaris.user.save.Bypass;
import io.github.ititus.pdx.stellaris.user.save.GalacticObject;
import io.github.ititus.pdx.stellaris.user.save.MegaStructure;
import io.github.ititus.pdx.stellaris.user.save.NaturalWormhole;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Translate;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;
import org.eclipse.collections.api.tuple.primitive.IntObjectPair;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;

import java.util.stream.Stream;

public class GalacticObjectFX extends Group {

    private final GalaxyView galaxyView;
    private final IntObjectPair<GalacticObject> systemPair;
    private final Sphere systemSphere;
    private final Tooltip systemTooltip;
    private final Group hyperlaneGroup;

    public GalacticObjectFX(GalaxyView galaxyView, IntObjectPair<GalacticObject> systemPair) {
        this.galaxyView = galaxyView;
        this.systemPair = systemPair;

        this.systemSphere = new Sphere(2);
        this.systemSphere.getTransforms().add(new Translate(systemPair.getTwo().getCoordinate().getX(),
                systemPair.getTwo().getCoordinate().getY(), 0));
        this.systemSphere.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            galaxyView.onClickInGalaxyView(systemPair);
            event.consume();
        });

        this.systemTooltip = new Tooltip(systemPair.getTwo().getName() + " (#" + systemPair.getOne() + ")");

        this.hyperlaneGroup = new Group();

        ImmutableIntObjectMap<NaturalWormhole> wormholes =
                galaxyView.getSave().getGameState().getNaturalWormholes().getNaturalWormholes();
        ImmutableIntObjectMap<Bypass> bypasses = galaxyView.getSave().getGameState().getBypasses().getBypasses();
        ImmutableIntObjectMap<MegaStructure> megaStructures =
                galaxyView.getSave().getGameState().getMegaStructures().getMegaStructures();

        ImmutableList<VisualHyperlane> visualHyperlanes = Stream.concat(
                systemPair.getTwo().getHyperlanes().stream()
                        .map(hyperlane -> new VisualHyperlane(systemPair.getOne(), hyperlane.getTo(),
                                hyperlane.isBridge() ? VisualHyperlane.Type.BRIDGE : VisualHyperlane.Type.NORMAL)),
                systemPair.getTwo().getBypasses().primitiveStream()
                        .mapToObj(bypasses::get)
                        .flatMap(b -> b.getConnections().primitiveStream().mapToObj(id -> PrimitiveTuples.pair(b, id)))
                        .map(p -> {
                            Bypass bypassFrom = p.getOne();
                            int targetId = p.getTwo();
                            Bypass targetBypass = bypasses.get(targetId);
                            int targetSystemId;
                            if (bypassFrom.getType().equals("wormhole")) {
                                targetSystemId = wormholes.stream()
                                        .filter(wormhole -> wormhole.getBypass() == targetId)
                                        .findAny()
                                        .get()
                                        .getCoordinate()
                                        .getOrigin();
                            } else {
                                targetSystemId = megaStructures.stream()
                                        .filter(megaStructure -> megaStructure.getBypass() == targetId)
                                        .findAny()
                                        .get()
                                        .getCoordinate()
                                        .getOrigin();
                            }
                            return new VisualHyperlane(systemPair.getOne(), targetSystemId,
                                    bypassFrom.isActive() && targetBypass.isActive() ?
                                            VisualHyperlane.Type.BYPASS_ACTIVE : VisualHyperlane.Type.BYPASS_INACTIVE);
                        })
        ).collect(Collectors2.toImmutableList());


        Platform.runLater(() -> {
            Tooltip.install(this.systemSphere, this.systemTooltip);
            this.getChildren().add(this.systemSphere);
            for (VisualHyperlane visualHyperlane : visualHyperlanes) {
                if (!galaxyView.containsHyperlane(visualHyperlane)) {
                    hyperlaneGroup.getChildren().add(new HyperlaneFX(galaxyView, visualHyperlane));
                }
            }
            this.getChildren().add(this.hyperlaneGroup);
        });
    }
}
