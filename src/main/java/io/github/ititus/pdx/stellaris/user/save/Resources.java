package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.*;
import io.github.ititus.pdx.util.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Resources {

    private final List<Double> energy, minerals, food, physicsResearch, societyResearch, engineeringResearch, influence, unity, aldar, darkMatter, engos, garanthium, livingMetal, lythuric, orillium, pitharan, satramene, teldar, terraformGases, terraformLiquids, yurantic, zro, alienPets, betharian;

    public Resources(PdxScriptObject o) {
        this.energy = get(o, "energy");
        this.minerals = get(o, "minerals");
        this.food = get(o, "food");
        this.physicsResearch = get(o, "physics_research");
        this.societyResearch = get(o, "society_research");
        this.engineeringResearch = get(o, "engineering_research");
        this.influence = get(o, "influence");
        this.unity = get(o, "unity");
        this.aldar = get(o, "sr_aldar");
        this.darkMatter = get(o, "sr_dark_matter");
        this.engos = get(o, "sr_engos");
        this.garanthium = get(o, "sr_garanthium");
        this.livingMetal = get(o, "sr_living_metal");
        this.lythuric = get(o, "sr_lythuric");
        this.orillium = get(o, "sr_orillium");
        this.pitharan = get(o, "sr_pitharan");
        this.satramene = get(o, "sr_satramene");
        this.teldar = get(o, "sr_teldar");
        this.terraformGases = get(o, "sr_terraform_gases");
        this.terraformLiquids = get(o, "sr_terraform_liquids");
        this.yurantic = get(o, "sr_yurantic");
        this.zro = get(o, "sr_zro");
        this.alienPets = get(o, "sr_alien_pets");
        this.betharian = get(o, "sr_betharian");
    }

    public Resources() {
        this(CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf(), CollectionUtil.listOf());
    }

    public Resources(Collection<Double> energy, Collection<Double> minerals, Collection<Double> food, Collection<Double> physicsResearch, Collection<Double> societyResearch, Collection<Double> engineeringResearch, Collection<Double> influence, Collection<Double> unity, Collection<Double> aldar, Collection<Double> darkMatter, Collection<Double> engos, Collection<Double> garanthium, Collection<Double> livingMetal, Collection<Double> lythuric, Collection<Double> orillium, Collection<Double> pitharan, Collection<Double> satramene, Collection<Double> teldar, Collection<Double> terraformGases, Collection<Double> terraformLiquids, Collection<Double> yurantic, Collection<Double> zro, Collection<Double> alienPets, Collection<Double> betharian) {
        this.energy = new ArrayList<>(energy);
        this.minerals = new ArrayList<>(minerals);
        this.food = new ArrayList<>(food);
        this.physicsResearch = new ArrayList<>(physicsResearch);
        this.societyResearch = new ArrayList<>(societyResearch);
        this.engineeringResearch = new ArrayList<>(engineeringResearch);
        this.influence = new ArrayList<>(influence);
        this.unity = new ArrayList<>(unity);
        this.aldar = new ArrayList<>(aldar);
        this.darkMatter = new ArrayList<>(darkMatter);
        this.engos = new ArrayList<>(engos);
        this.garanthium = new ArrayList<>(garanthium);
        this.livingMetal = new ArrayList<>(livingMetal);
        this.lythuric = new ArrayList<>(lythuric);
        this.orillium = new ArrayList<>(orillium);
        this.pitharan = new ArrayList<>(pitharan);
        this.satramene = new ArrayList<>(satramene);
        this.teldar = new ArrayList<>(teldar);
        this.terraformGases = new ArrayList<>(terraformGases);
        this.terraformLiquids = new ArrayList<>(terraformLiquids);
        this.yurantic = new ArrayList<>(yurantic);
        this.zro = new ArrayList<>(zro);
        this.alienPets = new ArrayList<>(alienPets);
        this.betharian = new ArrayList<>(betharian);
    }

    private static List<Double> get(PdxScriptObject o, String resource) {
        IPdxScript s = o.get(resource);
        if (s instanceof PdxScriptList) {
            o.use(resource, PdxConstants.LIST);
            return ((PdxScriptList) s).getAsDoubleList();
        } else if (s == null) {
            return CollectionUtil.listOf(0.0D, 0.0D, 0.0D);
        }
        o.use(resource, PdxConstants.DOUBLE);
        return CollectionUtil.listOf((Double) ((PdxScriptValue) s).getValue(), 0.0D, 0.0D);
    }

    public List<Double> getEnergy() {
        return Collections.unmodifiableList(energy);
    }

    public List<Double> getMinerals() {
        return Collections.unmodifiableList(minerals);
    }

    public List<Double> getFood() {
        return Collections.unmodifiableList(food);
    }

    public List<Double> getPhysicsResearch() {
        return Collections.unmodifiableList(physicsResearch);
    }

    public List<Double> getSocietyResearch() {
        return Collections.unmodifiableList(societyResearch);
    }

    public List<Double> getEngineeringResearch() {
        return Collections.unmodifiableList(engineeringResearch);
    }

    public List<Double> getInfluence() {
        return Collections.unmodifiableList(influence);
    }

    public List<Double> getUnity() {
        return Collections.unmodifiableList(unity);
    }

    public List<Double> getAldar() {
        return Collections.unmodifiableList(aldar);
    }

    public List<Double> getDarkMatter() {
        return Collections.unmodifiableList(darkMatter);
    }

    public List<Double> getEngos() {
        return Collections.unmodifiableList(engos);
    }

    public List<Double> getGaranthium() {
        return Collections.unmodifiableList(garanthium);
    }

    public List<Double> getLivingMetal() {
        return Collections.unmodifiableList(livingMetal);
    }

    public List<Double> getLythuric() {
        return Collections.unmodifiableList(lythuric);
    }

    public List<Double> getOrillium() {
        return Collections.unmodifiableList(orillium);
    }

    public List<Double> getPitharan() {
        return Collections.unmodifiableList(pitharan);
    }

    public List<Double> getSatramene() {
        return Collections.unmodifiableList(satramene);
    }

    public List<Double> getTeldar() {
        return Collections.unmodifiableList(teldar);
    }

    public List<Double> getTerraformGases() {
        return Collections.unmodifiableList(terraformGases);
    }

    public List<Double> getTerraformLiquids() {
        return Collections.unmodifiableList(terraformLiquids);
    }

    public List<Double> getYurantic() {
        return Collections.unmodifiableList(yurantic);
    }

    public List<Double> getZro() {
        return Collections.unmodifiableList(zro);
    }

    public List<Double> getAlienPets() {
        return Collections.unmodifiableList(alienPets);
    }

    public List<Double> getBetharian() {
        return Collections.unmodifiableList(betharian);
    }
}
