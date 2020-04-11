package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;
import io.github.ititus.pdx.util.Util;

public class Resources {

    private static final Deduplicator<Resources> DEDUPLICATOR = new Deduplicator<>();

    private final double energy, minerals, food, physicsResearch, societyResearch, engineeringResearch, influence,
            unity, aldar, darkMatter, engos, garanthium, livingMetal, lythuric, orillium, pitharan, satramene, teldar
            , terraformGases, terraformLiquids, yurantic, zro, alienPets, betharian;

    private Resources(PdxScriptObject o) {
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

    private Resources(double energy, double minerals, double food, double physicsResearch, double societyResearch,
                      double engineeringResearch, double influence, double unity, double aldar, double darkMatter,
                      double engos, double garanthium, double livingMetal, double lythuric, double orillium,
                      double pitharan, double satramene, double teldar, double terraformGases,
                      double terraformLiquids, double yurantic, double zro, double alienPets, double betharian) {
        this.energy = energy;
        this.minerals = minerals;
        this.food = food;
        this.physicsResearch = physicsResearch;
        this.societyResearch = societyResearch;
        this.engineeringResearch = engineeringResearch;
        this.influence = influence;
        this.unity = unity;
        this.aldar = aldar;
        this.darkMatter = darkMatter;
        this.engos = engos;
        this.garanthium = garanthium;
        this.livingMetal = livingMetal;
        this.lythuric = lythuric;
        this.orillium = orillium;
        this.pitharan = pitharan;
        this.satramene = satramene;
        this.teldar = teldar;
        this.terraformGases = terraformGases;
        this.terraformLiquids = terraformLiquids;
        this.yurantic = yurantic;
        this.zro = zro;
        this.alienPets = alienPets;
        this.betharian = betharian;
    }

    public static Resources of(PdxScriptObject o) {
        return DEDUPLICATOR.deduplicate(new Resources(o));
    }

    public static Resources of(double energy, double minerals, double food, double physicsResearch,
                               double societyResearch, double engineeringResearch, double influence, double unity,
                               double aldar, double darkMatter, double engos, double garanthium, double livingMetal,
                               double lythuric, double orillium, double pitharan, double satramene, double teldar,
                               double terraformGases, double terraformLiquids, double yurantic, double zro,
                               double alienPets, double betharian) {
        return DEDUPLICATOR.deduplicate(new Resources(energy, minerals, food, physicsResearch, societyResearch,
                engineeringResearch, influence, unity, aldar, darkMatter, engos, garanthium, livingMetal, lythuric,
                orillium, pitharan, satramene, teldar, terraformGases, terraformLiquids, yurantic, zro, alienPets,
                betharian));
    }

    public double getEnergy() {
        return energy;
    }

    public double getMinerals() {
        return minerals;
    }

    public double getFood() {
        return food;
    }

    public double getPhysicsResearch() {
        return physicsResearch;
    }

    public double getSocietyResearch() {
        return societyResearch;
    }

    public double getEngineeringResearch() {
        return engineeringResearch;
    }

    public double getInfluence() {
        return influence;
    }

    public double getUnity() {
        return unity;
    }

    public double getAldar() {
        return aldar;
    }

    public double getDarkMatter() {
        return darkMatter;
    }

    public double getEngos() {
        return engos;
    }

    public double getGaranthium() {
        return garanthium;
    }

    public double getLivingMetal() {
        return livingMetal;
    }

    public double getLythuric() {
        return lythuric;
    }

    public double getOrillium() {
        return orillium;
    }

    public double getPitharan() {
        return pitharan;
    }

    public double getSatramene() {
        return satramene;
    }

    public double getTeldar() {
        return teldar;
    }

    public double getTerraformGases() {
        return terraformGases;
    }

    public double getTerraformLiquids() {
        return terraformLiquids;
    }

    public double getYurantic() {
        return yurantic;
    }

    public double getZro() {
        return zro;
    }

    public double getAlienPets() {
        return alienPets;
    }

    public double getBetharian() {
        return betharian;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resources)) {
            return false;
        }
        Resources resources = (Resources) o;
        return Double.compare(energy, resources.energy) == 0 &&
                Double.compare(minerals, resources.minerals) == 0 &&
                Double.compare(food, resources.food) == 0 &&
                Double.compare(physicsResearch, resources.physicsResearch) == 0 &&
                Double.compare(societyResearch, resources.societyResearch) == 0 &&
                Double.compare(engineeringResearch, resources.engineeringResearch) == 0 &&
                Double.compare(influence, resources.influence) == 0 &&
                Double.compare(unity, resources.unity) == 0 &&
                Double.compare(aldar, resources.aldar) == 0 &&
                Double.compare(darkMatter, resources.darkMatter) == 0 &&
                Double.compare(engos, resources.engos) == 0 &&
                Double.compare(garanthium, resources.garanthium) == 0 &&
                Double.compare(livingMetal, resources.livingMetal) == 0 &&
                Double.compare(lythuric, resources.lythuric) == 0 &&
                Double.compare(orillium, resources.orillium) == 0 &&
                Double.compare(pitharan, resources.pitharan) == 0 &&
                Double.compare(satramene, resources.satramene) == 0 &&
                Double.compare(teldar, resources.teldar) == 0 &&
                Double.compare(terraformGases, resources.terraformGases) == 0 &&
                Double.compare(terraformLiquids, resources.terraformLiquids) == 0 &&
                Double.compare(yurantic, resources.yurantic) == 0 &&
                Double.compare(zro, resources.zro) == 0 &&
                Double.compare(alienPets, resources.alienPets) == 0 &&
                Double.compare(betharian, resources.betharian) == 0;
    }

    @Override
    public int hashCode() {
        return Util.hash(energy, minerals, food, physicsResearch, societyResearch, engineeringResearch, influence,
                unity, aldar, darkMatter, engos, garanthium, livingMetal, lythuric, orillium, pitharan, satramene,
                teldar, terraformGases, terraformLiquids, yurantic, zro, alienPets, betharian);
    }
}
