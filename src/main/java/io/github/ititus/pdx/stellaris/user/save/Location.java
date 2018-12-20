package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;

import java.util.Objects;

public class Location {

    private static final Deduplicator<Location> DEDUPLICATOR = new Deduplicator<>();

    private final int id;
    private final String type, area;

    private Location(PdxScriptObject o) {
        this.type = o.getString("type");
        this.area = o.getString("area");
        this.id = o.getUnsignedInt("id");
    }

    private Location(int id, String type, String area) {
        this.id = id;
        this.type = type;
        this.area = area;
    }

    public static Location of(PdxScriptObject o) {
        return DEDUPLICATOR.deduplicate(new Location(o));
    }

    public static Location of(int id, String type, String area) {
        return DEDUPLICATOR.deduplicate(new Location(id, type, area));
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getArea() {
        return area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        Location location = (Location) o;
        return id == location.id && Objects.equals(type, location.type) && Objects.equals(area, location.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, area);
    }
}
