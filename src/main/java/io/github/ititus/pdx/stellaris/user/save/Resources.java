package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.*;
import io.github.ititus.pdx.util.Deduplicator;
import io.github.ititus.pdx.util.Tuple3D;

import java.util.Objects;

public class Resources {

    private static final Deduplicator<Resources> DEDUPLICATOR = new Deduplicator<>();

    private final Tuple3D energy, minerals, food, physicsResearch, societyResearch, engineeringResearch, influence, unity, aldar, darkMatter, engos, garanthium, livingMetal, lythuric, orillium, pitharan, satramene, teldar, terraformGases, terraformLiquids, yurantic, zro, alienPets, betharian;

    private Resources(PdxScriptObject o) {
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

    private Resources(double... array) {
        this(Tuple3D.of(array[0]), Tuple3D.of(array[1]), Tuple3D.of(array[2]), Tuple3D.of(array[3]), Tuple3D.of(array[4]), Tuple3D.of(array[5]), Tuple3D.of(array[6]), Tuple3D.of(array[7]), Tuple3D.of(array[8]), Tuple3D.of(array[9]), Tuple3D.of(array[10]), Tuple3D.of(array[11]), Tuple3D.of(array[12]), Tuple3D.of(array[13]), Tuple3D.of(array[14]), Tuple3D.of(array[15]), Tuple3D.of(array[16]), Tuple3D.of(array[17]), Tuple3D.of(array[18]), Tuple3D.of(array[19]), Tuple3D.of(array[20]), Tuple3D.of(array[21]), Tuple3D.of(array[22]), Tuple3D.of(array[23]));
    }

    private Resources(Tuple3D energy, Tuple3D minerals, Tuple3D food, Tuple3D physicsResearch, Tuple3D societyResearch, Tuple3D engineeringResearch, Tuple3D influence, Tuple3D unity, Tuple3D aldar, Tuple3D darkMatter, Tuple3D engos, Tuple3D garanthium, Tuple3D livingMetal, Tuple3D lythuric, Tuple3D orillium, Tuple3D pitharan, Tuple3D satramene, Tuple3D teldar, Tuple3D terraformGases, Tuple3D terraformLiquids, Tuple3D yurantic, Tuple3D zro, Tuple3D alienPets, Tuple3D betharian) {
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

    private static Tuple3D get(PdxScriptObject o, String resource) {
        IPdxScript s = o.get(resource);
        if (s instanceof PdxScriptList) {
            o.use(resource, PdxConstants.LIST);
            return Tuple3D.of(((PdxScriptList) s).getAsDoubleArray());
        } else if (s == null) {
            return Tuple3D.of();
        }
        o.use(resource, PdxConstants.DOUBLE);
        return Tuple3D.of(((Number) ((PdxScriptValue) s).getValue()).doubleValue());
    }

    public static Resources of(PdxScriptObject o) {
        return DEDUPLICATOR.deduplicate(new Resources(o));
    }

    public static Resources of(double... array) {
        return DEDUPLICATOR.deduplicate(new Resources(array));
    }

    public static Resources of(Tuple3D energy, Tuple3D minerals, Tuple3D food, Tuple3D physicsResearch, Tuple3D societyResearch, Tuple3D engineeringResearch, Tuple3D influence, Tuple3D unity, Tuple3D aldar, Tuple3D darkMatter, Tuple3D engos, Tuple3D garanthium, Tuple3D livingMetal, Tuple3D lythuric, Tuple3D orillium, Tuple3D pitharan, Tuple3D satramene, Tuple3D teldar, Tuple3D terraformGases, Tuple3D terraformLiquids, Tuple3D yurantic, Tuple3D zro, Tuple3D alienPets, Tuple3D betharian) {
        return DEDUPLICATOR.deduplicate(new Resources(energy, minerals, food, physicsResearch, societyResearch, engineeringResearch, influence, unity, aldar, darkMatter, engos, garanthium, livingMetal, lythuric, orillium, pitharan, satramene, teldar, terraformGases, terraformLiquids, yurantic, zro, alienPets, betharian));
    }

    public Tuple3D getEnergy() {
        return energy;
    }

    public Tuple3D getMinerals() {
        return minerals;
    }

    public Tuple3D getFood() {
        return food;
    }

    public Tuple3D getPhysicsResearch() {
        return physicsResearch;
    }

    public Tuple3D getSocietyResearch() {
        return societyResearch;
    }

    public Tuple3D getEngineeringResearch() {
        return engineeringResearch;
    }

    public Tuple3D getInfluence() {
        return influence;
    }

    public Tuple3D getUnity() {
        return unity;
    }

    public Tuple3D getAldar() {
        return aldar;
    }

    public Tuple3D getDarkMatter() {
        return darkMatter;
    }

    public Tuple3D getEngos() {
        return engos;
    }

    public Tuple3D getGaranthium() {
        return garanthium;
    }

    public Tuple3D getLivingMetal() {
        return livingMetal;
    }

    public Tuple3D getLythuric() {
        return lythuric;
    }

    public Tuple3D getOrillium() {
        return orillium;
    }

    public Tuple3D getPitharan() {
        return pitharan;
    }

    public Tuple3D getSatramene() {
        return satramene;
    }

    public Tuple3D getTeldar() {
        return teldar;
    }

    public Tuple3D getTerraformGases() {
        return terraformGases;
    }

    public Tuple3D getTerraformLiquids() {
        return terraformLiquids;
    }

    public Tuple3D getYurantic() {
        return yurantic;
    }

    public Tuple3D getZro() {
        return zro;
    }

    public Tuple3D getAlienPets() {
        return alienPets;
    }

    public Tuple3D getBetharian() {
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
        return Objects.equals(energy, resources.energy) &&
                Objects.equals(minerals, resources.minerals) &&
                Objects.equals(food, resources.food) &&
                Objects.equals(physicsResearch, resources.physicsResearch) &&
                Objects.equals(societyResearch, resources.societyResearch) &&
                Objects.equals(engineeringResearch, resources.engineeringResearch) &&
                Objects.equals(influence, resources.influence) &&
                Objects.equals(unity, resources.unity) &&
                Objects.equals(aldar, resources.aldar) &&
                Objects.equals(darkMatter, resources.darkMatter) &&
                Objects.equals(engos, resources.engos) &&
                Objects.equals(garanthium, resources.garanthium) &&
                Objects.equals(livingMetal, resources.livingMetal) &&
                Objects.equals(lythuric, resources.lythuric) &&
                Objects.equals(orillium, resources.orillium) &&
                Objects.equals(pitharan, resources.pitharan) &&
                Objects.equals(satramene, resources.satramene) &&
                Objects.equals(teldar, resources.teldar) &&
                Objects.equals(terraformGases, resources.terraformGases) &&
                Objects.equals(terraformLiquids, resources.terraformLiquids) &&
                Objects.equals(yurantic, resources.yurantic) &&
                Objects.equals(zro, resources.zro) &&
                Objects.equals(alienPets, resources.alienPets) &&
                Objects.equals(betharian, resources.betharian);
    }

    @Override
    public int hashCode() {
        return Objects.hash(energy, minerals, food, physicsResearch, societyResearch, engineeringResearch, influence, unity, aldar, darkMatter, engos, garanthium, livingMetal, lythuric, orillium, pitharan, satramene, teldar, terraformGases, terraformLiquids, yurantic, zro, alienPets, betharian);
    }
}
