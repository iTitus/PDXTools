package io.github.ititus.pdx.stellaris.view;

import io.github.ititus.pdx.stellaris.user.save.Bypass;
import io.github.ititus.pdx.stellaris.user.save.GalacticObject;
import io.github.ititus.pdx.stellaris.user.save.NaturalWormhole;
import io.github.ititus.pdx.util.collection.CollectionUtil;
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

import java.util.stream.Stream;

public class GalacticObjectFX extends Group {

    private final GalaxyView galaxyView;
    private final IntObjectPair<GalacticObject> systemPair;
    private final Sphere galaxySphere;
    private final Tooltip galaxyTooltip;
    private final Group hyperlaneGroup;

    public GalacticObjectFX(GalaxyView galaxyView, IntObjectPair<GalacticObject> systemPair) {
        this.galaxyView = galaxyView;
        this.systemPair = systemPair;

        this.galaxySphere = new Sphere(2);
        this.galaxySphere.getTransforms().add(new Translate(systemPair.getTwo().getCoordinate().getX(), systemPair.getTwo().getCoordinate().getY(), 0));
        this.galaxyTooltip = new Tooltip(systemPair.getTwo().getName() + " (#" + systemPair.getOne() + ")");
        this.galaxySphere.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            galaxyView.onClickInGalaxyView(systemPair);
            event.consume();
        });

        this.hyperlaneGroup = new Group();

        ImmutableIntObjectMap<NaturalWormhole> wormholes = galaxyView.getSave().getGameState().getNaturalWormholes().getNaturalWormholes();
        ImmutableIntObjectMap<Bypass> bypasses = galaxyView.getSave().getGameState().getBypasses().getBypasses();
        ImmutableList<VisualHyperlane> visualHyperlanes = Stream.concat(
                systemPair.getTwo().getHyperlanes().stream()
                        .map(hyperlane -> new VisualHyperlane(systemPair.getOne(), hyperlane.getTo(), hyperlane.isBridge() ? VisualHyperlane.Type.BRIDGE : VisualHyperlane.Type.NORMAL)),
                CollectionUtil.stream(systemPair.getTwo().getNaturalWormholes())
                        .mapToObj(wormholes::get)
                        .mapToInt(NaturalWormhole::getBypass)
                        .mapToObj(bypasses::get)
                        .mapToInt(Bypass::getLinkedTo)
                        .mapToObj(bypassId -> {
                            Bypass bypassFrom = bypasses.get(bypassId);
                            int to = wormholes.stream().filter(wormhole -> wormhole.getBypass() == bypassId).findAny().get().getCoordinate().getOrigin();
                            return new VisualHyperlane(systemPair.getOne(), to, bypassFrom.isActive() ? VisualHyperlane.Type.WORMHOLE_ACTIVE : VisualHyperlane.Type.WORMHOLE_INACTIVE);
                        })
        ).collect(Collectors2.toImmutableList());


        Platform.runLater(() -> {
            IntObjectPair<GalacticObject> pair = systemPair;
            Tooltip.install(this.galaxySphere, this.galaxyTooltip);
            this.getChildren().add(this.galaxySphere);
            for (VisualHyperlane visualHyperlane : visualHyperlanes) {
                if (!galaxyView.containsHyperlane(visualHyperlane)) {
                    hyperlaneGroup.getChildren().add(new HyperlaneFX(galaxyView, visualHyperlane));
                }
            }
            this.getChildren().add(this.hyperlaneGroup);
        });
    }
}
