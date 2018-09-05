package io.github.ititus.pdx.stellaris.view;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

import java.util.Objects;

public class VisualHyperlane {

    private static final PhongMaterial MATERIAL_NORMAL = new PhongMaterial(Color.LIGHTBLUE);
    private static final PhongMaterial MATERIAL_BRIDGE = new PhongMaterial(Color.LIGHTCORAL);
    private static final PhongMaterial MATERIAL_WORMHOLE_ACTIVE = new PhongMaterial(Color.LIGHTGREEN);
    private static final PhongMaterial MATERIAL_WORMHOLE_INACTIVE = new PhongMaterial(Color.LIGHTGREEN.brighter().brighter());

    private final int from, to;
    private final Type type;

    public VisualHyperlane(int from, int to, Type type) {
        if (from <= to) {
            this.from = from;
            this.to = to;
        } else {
            this.from = to;
            this.to = from;
        }
        this.type = type;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public Type getType() {
        return type;
    }

    public boolean containsEndpoint(int endpoint) {
        return from == endpoint || to == endpoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VisualHyperlane)) {
            return false;
        }
        VisualHyperlane that = (VisualHyperlane) o;
        return from == that.from && to == that.to && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, type);
    }

    enum Type {

        NORMAL(MATERIAL_NORMAL), BRIDGE(MATERIAL_BRIDGE), WORMHOLE_ACTIVE(MATERIAL_WORMHOLE_ACTIVE), WORMHOLE_INACTIVE(MATERIAL_WORMHOLE_INACTIVE);

        private final PhongMaterial material;

        Type(PhongMaterial material) {
            this.material = material;
        }

        public PhongMaterial getMaterial() {
            return material;
        }
    }
}
