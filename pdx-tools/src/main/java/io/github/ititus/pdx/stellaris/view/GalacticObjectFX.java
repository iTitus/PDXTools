package io.github.ititus.pdx.stellaris.view;

import io.github.ititus.pdx.stellaris.user.save.Bypass;
import io.github.ititus.pdx.stellaris.user.save.GalacticObject;
import io.github.ititus.pdx.stellaris.user.save.Megastructure;
import io.github.ititus.pdx.stellaris.user.save.NaturalWormhole;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Translate;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;

import java.util.stream.Stream;

public class GalacticObjectFX extends Group {

    private final GalaxyView galaxyView;
    private final GalacticObject systemPair;
    private final Sphere systemSphere;
    private final Tooltip systemTooltip;
    private final Group hyperlaneGroup;

    public GalacticObjectFX(GalaxyView galaxyView, GalacticObject system) {
        this.galaxyView = galaxyView;
        this.systemPair = system;

        this.systemSphere = new Sphere(2);
        this.systemSphere.getTransforms().add(new Translate(
                system.coordinate.x,
                system.coordinate.y,
                0
        ));
        this.systemSphere.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            galaxyView.onClickInGalaxyView(system);
            event.consume();
        });

        this.systemTooltip = new Tooltip(system.name + " (#" + system.id + ")");

        this.hyperlaneGroup = new Group();

        ImmutableIntObjectMap<NaturalWormhole> wormholes = galaxyView.getSave().gameState.naturalWormholes;
        ImmutableIntObjectMap<Bypass> bypasses = galaxyView.getSave().gameState.bypasses;
        ImmutableIntObjectMap<Megastructure> megaStructures = galaxyView.getSave().gameState.megaStructures;

        ImmutableList<VisualHyperlane> visualHyperlanes = Stream.concat(
                system.hyperlanes.stream()
                        .map(hyperlane -> new VisualHyperlane(system.id, hyperlane.to, hyperlane.bridge ? VisualHyperlane.Type.BRIDGE : VisualHyperlane.Type.NORMAL)),
                system.bypasses.primitiveStream()
                        .mapToObj(bypasses::get)
                        .flatMap(b -> b.connections.primitiveStream().mapToObj(id -> PrimitiveTuples.pair(b, id)))
                        .map(p -> {
                            Bypass bypassFrom = p.getOne();

                            int targetId = p.getTwo();
                            Bypass targetBypass = bypasses.get(targetId);

                            if (bypassFrom.type.equals("wormhole")) {
                                int targetSystemId = wormholes.stream()
                                        .filter(wormhole -> wormhole.bypass == targetId)
                                        .findAny()
                                        .orElseThrow()
                                        .coordinate
                                        .origin;
                                return new VisualHyperlane(
                                        system.id,
                                        targetSystemId,
                                        bypassFrom.active && targetBypass.active ? VisualHyperlane.Type.WORMHOLE_BYPASS_ACTIVE : VisualHyperlane.Type.WORMHOLE_BYPASS_INACTIVE
                                );
                            }

                            int targetSystemId = megaStructures.stream()
                                    .filter(megastructure -> megastructure.bypass == targetId)
                                    .findAny()
                                    .orElseThrow()
                                    .coordinate
                                    .origin;
                            return new VisualHyperlane(
                                    system.id,
                                    targetSystemId,
                                    bypassFrom.active && targetBypass.active ? VisualHyperlane.Type.MEGASTRUCTURE_BYPASS_ACTIVE : VisualHyperlane.Type.MEGASTRUCTURE_BYPASS_INACTIVE
                            );
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
