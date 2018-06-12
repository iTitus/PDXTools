package io.github.ititus.stellaris.analyser.save;

import io.github.ititus.stellaris.analyser.pdxscript.IPdxScript;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptList;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptObject;
import io.github.ititus.stellaris.analyser.pdxscript.PdxScriptValue;
import io.github.ititus.stellaris.analyser.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Resources {

    private final List<Double> energy, minerals, food, physicsResearch, societyResearch, engineeringResearch, influence, unity;

    public Resources(PdxScriptObject o) {
        IPdxScript s = o.get("energy");
        if (s instanceof PdxScriptList) {
            this.energy = ((PdxScriptList) s).getAsDoubleList();
        } else if (s instanceof PdxScriptValue) {
            this.energy = CollectionUtil.listOf(o.getDouble("energy"), 0.0D, 0.0D);
        } else {
            this.energy = CollectionUtil.listOf(0.0D, 0.0D, 0.0D);
        }

        s = o.get("minerals");
        if (s instanceof PdxScriptList) {
            this.minerals = ((PdxScriptList) s).getAsDoubleList();
        } else if (s instanceof PdxScriptValue) {
            this.minerals = CollectionUtil.listOf(o.getDouble("minerals"), 0.0D, 0.0D);
        } else {
            this.minerals = CollectionUtil.listOf(0.0D, 0.0D, 0.0D);
        }

        s = o.get("food");
        if (s instanceof PdxScriptList) {
            this.food = ((PdxScriptList) s).getAsDoubleList();
        } else if (s instanceof PdxScriptValue) {
            this.food = CollectionUtil.listOf(o.getDouble("food"), 0.0D, 0.0D);
        } else {
            this.food = CollectionUtil.listOf(0.0D, 0.0D, 0.0D);
        }

        s = o.get("physics_research");
        if (s instanceof PdxScriptList) {
            this.physicsResearch = ((PdxScriptList) s).getAsDoubleList();
        } else if (s instanceof PdxScriptValue) {
            this.physicsResearch = CollectionUtil.listOf(o.getDouble("physics_research"), 0.0D, 0.0D);
        } else {
            this.physicsResearch = CollectionUtil.listOf(0.0D, 0.0D, 0.0D);
        }

        s = o.get("society_research");
        if (s instanceof PdxScriptList) {
            this.societyResearch = ((PdxScriptList) s).getAsDoubleList();
        } else if (s instanceof PdxScriptValue) {
            this.societyResearch = CollectionUtil.listOf(o.getDouble("society_research"), 0.0D, 0.0D);
        } else {
            this.societyResearch = CollectionUtil.listOf(0.0D, 0.0D, 0.0D);
        }

        s = o.get("engineering_research");
        if (s instanceof PdxScriptList) {
            this.engineeringResearch = ((PdxScriptList) s).getAsDoubleList();
        } else if (s instanceof PdxScriptValue) {
            this.engineeringResearch = CollectionUtil.listOf(o.getDouble("engineering_research"), 0.0D, 0.0D);
        } else {
            this.engineeringResearch = CollectionUtil.listOf(0.0D, 0.0D, 0.0D);
        }

        s = o.get("influence");
        if (s instanceof PdxScriptList) {
            this.influence = ((PdxScriptList) s).getAsDoubleList();
        } else if (s instanceof PdxScriptValue) {
            this.influence = CollectionUtil.listOf(o.getDouble("influence"), 0.0D, 0.0D);
        } else {
            this.influence = CollectionUtil.listOf(0.0D, 0.0D, 0.0D);
        }

        s = o.get("unity");
        if (s instanceof PdxScriptList) {
            this.unity = ((PdxScriptList) s).getAsDoubleList();
        } else if (s instanceof PdxScriptValue) {
            this.unity = CollectionUtil.listOf(o.getDouble("unity"), 0.0D, 0.0D);
        } else {
            this.unity = CollectionUtil.listOf(0.0D, 0.0D, 0.0D);
        }
    }


    public Resources() {
        this(CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf());
    }

    public Resources(Collection<Double> energy, Collection<Double> minerals, Collection<Double> food, Collection<Double> physicsResearch, Collection<Double> societyResearch, Collection<Double> engineeringResearch, Collection<Double> influence, Collection<Double> unity) {
        this.energy = new ArrayList<>(energy);
        this.minerals = new ArrayList<>(minerals);
        this.food = new ArrayList<>(food);
        this.physicsResearch = new ArrayList<>(physicsResearch);
        this.societyResearch = new ArrayList<>(societyResearch);
        this.engineeringResearch = new ArrayList<>(engineeringResearch);
        this.influence = new ArrayList<>(influence);
        this.unity = new ArrayList<>(unity);
    }

    public List<Double> getEnergy() {
        return energy;
    }

    public List<Double> getMinerals() {
        return minerals;
    }

    public List<Double> getFood() {
        return food;
    }

    public List<Double> getPhysicsResearch() {
        return physicsResearch;
    }

    public List<Double> getSocietyResearch() {
        return societyResearch;
    }

    public List<Double> getEngineeringResearch() {
        return engineeringResearch;
    }

    public List<Double> getInfluence() {
        return influence;
    }

    public List<Double> getUnity() {
        return unity;
    }
}
