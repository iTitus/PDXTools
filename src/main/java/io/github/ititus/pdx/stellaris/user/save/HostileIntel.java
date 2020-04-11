package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.impl.factory.Lists;

public class HostileIntel {

    private final boolean hasBoss;
    private final int owner;
    private final double militaryPower;
    private final String name;
    private final ImmutableList<String> size;
    private final Coordinate coordinate;

    public HostileIntel(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.name = o.getString("name");
        this.coordinate = o.getObject("coordinate").getAs(Coordinate::of);
        this.militaryPower = o.getDouble("military_power");
        this.hasBoss = o.getBoolean("has_boss");
        this.owner = o.getInt("owner");
        PdxScriptList l = o.getList("size");
        this.size = l != null ? l.getAsStringList() : Lists.immutable.empty();
    }

    public HostileIntel(boolean hasBoss, int owner, double militaryPower, String name, ImmutableList<String> size,
                        Coordinate coordinate) {
        this.hasBoss = hasBoss;
        this.owner = owner;
        this.militaryPower = militaryPower;
        this.name = name;
        this.size = size;
        this.coordinate = coordinate;
    }

    public boolean isHasBoss() {
        return hasBoss;
    }

    public int getOwner() {
        return owner;
    }

    public double getMilitaryPower() {
        return militaryPower;
    }

    public String getName() {
        return name;
    }

    public ImmutableList<String> getSize() {
        return size;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
