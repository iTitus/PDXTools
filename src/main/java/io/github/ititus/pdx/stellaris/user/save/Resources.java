package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Resources {

    public final double energy;
    public final double minerals;
    public final double food;
    public final double physicsResearch;
    public final double societyResearch;
    public final double engineeringResearch;
    public final double influence;
    public final double unity;
    public final double alloys;
    public final double consumerGoods;
    public final double volatileMotes;
    public final double exoticGases;
    public final double rareCrystals;
    public final double livingMetal;
    public final double zro;
    public final double darkMatter;
    public final double nanites;
    public final double minorArtifacts;

    public Resources(PdxScriptObject o) {
        this.energy = o.getDouble("energy", 0);
        this.minerals = o.getDouble("minerals", 0);
        this.food = o.getDouble("food", 0);
        this.physicsResearch = o.getDouble("physics_research", 0);
        this.societyResearch = o.getDouble("society_research", 0);
        this.engineeringResearch = o.getDouble("engineering_research", 0);
        this.influence = o.getDouble("influence", 0);
        this.unity = o.getDouble("unity", 0);
        this.alloys = o.getDouble("alloys", 0);
        this.consumerGoods = o.getDouble("consumer_goods", 0);
        this.volatileMotes = o.getDouble("volatile_motes", 0);
        this.exoticGases = o.getDouble("exotic_gases", 0);
        this.rareCrystals = o.getDouble("rare_crystals", 0);
        this.livingMetal = o.getDouble("sr_living_metal", 0);
        this.zro = o.getDouble("sr_zro", 0);
        this.darkMatter = o.getDouble("sr_dark_matter", 0);
        this.nanites = o.getDouble("nanites", 0);
        this.minorArtifacts = o.getDouble("minor_artifacts", 0);
    }
}
