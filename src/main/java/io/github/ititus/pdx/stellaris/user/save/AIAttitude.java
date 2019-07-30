package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

public class AIAttitude {

    private final int country, priority;
    private final double weight;
    private final String attitude;
    private final LocalDate date;

    public AIAttitude(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.country = o.getUnsignedInt("country");
        this.attitude = o.getString("attitude");
        this.weight = o.getDouble("weight");
        this.priority = o.getInt("priority");
        this.date = o.getDate("date");
    }

    public AIAttitude(int country, int priority, double weight, String attitude, LocalDate date) {
        this.country = country;
        this.priority = priority;
        this.weight = weight;
        this.attitude = attitude;
        this.date = date;
    }

    public int getCountry() {
        return country;
    }

    public int getPriority() {
        return priority;
    }

    public double getWeight() {
        return weight;
    }

    public String getAttitude() {
        return attitude;
    }

    public LocalDate getDate() {
        return date;
    }
}
