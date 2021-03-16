package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

public class Resources {

    // FIXME: new resource types
    public final double energy;
    public final double minerals;
    public final double food;
    public final double physicsResearch;
    public final double societyResearch;
    public final double engineeringResearch;
    public final double influence;
    public final double unity;
    public final double aldar;
    public final double darkMatter;
    public final double engos;
    public final double garanthium;
    public final double livingMetal;
    public final double lythuric;
    public final double orillium;
    public final double pitharan;
    public final double satramene;
    public final double teldar;
    public final double terraformGases;
    public final double terraformLiquids;
    public final double yurantic;
    public final double zro;
    public final double alienPets;
    public final double betharian;

    public Resources(PdxScriptObject o) {
        this.energy = o.getDouble("energy");
        this.minerals = o.getDouble("minerals");
        this.food = o.getDouble("food");
        this.physicsResearch = o.getDouble("physics_research");
        this.societyResearch = o.getDouble("society_research");
        this.engineeringResearch = o.getDouble("engineering_research");
        this.influence = o.getDouble("influence");
        this.unity = o.getDouble("unity");
        this.aldar = o.getDouble("sr_aldar");
        this.darkMatter = o.getDouble("sr_dark_matter");
        this.engos = o.getDouble("sr_engos");
        this.garanthium = o.getDouble("sr_garanthium");
        this.livingMetal = o.getDouble("sr_living_metal");
        this.lythuric = o.getDouble("sr_lythuric");
        this.orillium = o.getDouble("sr_orillium");
        this.pitharan = o.getDouble("sr_pitharan");
        this.satramene = o.getDouble("sr_satramene");
        this.teldar = o.getDouble("sr_teldar");
        this.terraformGases = o.getDouble("sr_terraform_gases");
        this.terraformLiquids = o.getDouble("sr_terraform_liquids");
        this.yurantic = o.getDouble("sr_yurantic");
        this.zro = o.getDouble("sr_zro");
        this.alienPets = o.getDouble("sr_alien_pets");
        this.betharian = o.getDouble("sr_betharian");
    }
}
