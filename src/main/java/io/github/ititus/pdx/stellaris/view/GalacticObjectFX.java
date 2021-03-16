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
        this.systemSphere.getTransforms().add(new Translate(
                systemPair.getTwo().coordinate.x,
                systemPair.getTwo().coordinate.y,
                0
        ));
        this.systemSphere.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            galaxyView.onClickInGalaxyView(systemPair);
            event.consume();
        });

        this.systemTooltip = new Tooltip(systemPair.getTwo().name + " (#" + systemPair.getOne() + ")");

        this.hyperlaneGroup = new Group();

        ImmutableIntObjectMap<NaturalWormhole> wormholes = galaxyView.getSave().gameState.naturalWormholes;
        ImmutableIntObjectMap<Bypass> bypasses = galaxyView.getSave().gameState.bypasses;
        ImmutableIntObjectMap<MegaStructure> megaStructures = galaxyView.getSave().gameState.megaStructures;

        ImmutableList<VisualHyperlane> visualHyperlanes = Stream.concat(
                systemPair.getTwo().hyperlanes.stream()
                        .map(hyperlane -> new VisualHyperlane(systemPair.getOne(), hyperlane.to,
                                hyperlane.bridge ? VisualHyperlane.Type.BRIDGE : VisualHyperlane.Type.NORMAL)),
                systemPair.getTwo().bypasses.primitiveStream()
                        .mapToObj(bypasses::get)
                        .flatMap(b -> b.connections.primitiveStream().mapToObj(id -> PrimitiveTuples.pair(b, id)))
                        .map(p -> {
                            Bypass bypassFrom = p.getOne();
                            int targetId = p.getTwo();
                            Bypass targetBypass = bypasses.get(targetId);
                            int targetSystemId;
                            if (bypassFrom.type.equals("wormhole")) {
                                targetSystemId = wormholes.stream()
                                        .filter(wormhole -> wormhole.bypass == targetId)
                                        .findAny()
                                        .get()
                                        .coordinate
                                        .origin;
                            } else {
                                targetSystemId = megaStructures.stream()
                                        .filter(megaStructure -> megaStructure.bypass == targetId)
                                        .findAny()
                                        .get()
                                        .coordinate
                                        .origin;
                            }
                            return new VisualHyperlane(systemPair.getOne(), targetSystemId,
                                    bypassFrom.active && targetBypass.active ?
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
