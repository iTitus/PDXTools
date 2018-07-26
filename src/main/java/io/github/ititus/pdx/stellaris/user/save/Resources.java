package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.*;
import io.github.ititus.pdx.util.collection.CollectionUtil;
import io.github.ititus.pdx.util.collection.ViewableList;
import io.github.ititus.pdx.util.collection.ViewableUnmodifiableArrayList;

import java.util.Collection;
import java.util.List;

public class Resources {

    private final ViewableList<Double> energy, minerals, food, physicsResearch, societyResearch, engineeringResearch, influence, unity, aldar, darkMatter, engos, garanthium, livingMetal, lythuric, orillium, pitharan, satramene, teldar, terraformGases, terraformLiquids, yurantic, zro, alienPets, betharian;

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
        this(CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf(), CollectionUtil.viewableListOf());
    }

    public Resources(Collection<Double> energy, Collection<Double> minerals, Collection<Double> food, Collection<Double> physicsResearch, Collection<Double> societyResearch, Collection<Double> engineeringResearch, Collection<Double> influence, Collection<Double> unity, Collection<Double> aldar, Collection<Double> darkMatter, Collection<Double> engos, Collection<Double> garanthium, Collection<Double> livingMetal, Collection<Double> lythuric, Collection<Double> orillium, Collection<Double> pitharan, Collection<Double> satramene, Collection<Double> teldar, Collection<Double> terraformGases, Collection<Double> terraformLiquids, Collection<Double> yurantic, Collection<Double> zro, Collection<Double> alienPets, Collection<Double> betharian) {
        this.energy = new ViewableUnmodifiableArrayList<>(energy);
        this.minerals = new ViewableUnmodifiableArrayList<>(minerals);
        this.food = new ViewableUnmodifiableArrayList<>(food);
        this.physicsResearch = new ViewableUnmodifiableArrayList<>(physicsResearch);
        this.societyResearch = new ViewableUnmodifiableArrayList<>(societyResearch);
        this.engineeringResearch = new ViewableUnmodifiableArrayList<>(engineeringResearch);
        this.influence = new ViewableUnmodifiableArrayList<>(influence);
        this.unity = new ViewableUnmodifiableArrayList<>(unity);
        this.aldar = new ViewableUnmodifiableArrayList<>(aldar);
        this.darkMatter = new ViewableUnmodifiableArrayList<>(darkMatter);
        this.engos = new ViewableUnmodifiableArrayList<>(engos);
        this.garanthium = new ViewableUnmodifiableArrayList<>(garanthium);
        this.livingMetal = new ViewableUnmodifiableArrayList<>(livingMetal);
        this.lythuric = new ViewableUnmodifiableArrayList<>(lythuric);
        this.orillium = new ViewableUnmodifiableArrayList<>(orillium);
        this.pitharan = new ViewableUnmodifiableArrayList<>(pitharan);
        this.satramene = new ViewableUnmodifiableArrayList<>(satramene);
        this.teldar = new ViewableUnmodifiableArrayList<>(teldar);
        this.terraformGases = new ViewableUnmodifiableArrayList<>(terraformGases);
        this.terraformLiquids = new ViewableUnmodifiableArrayList<>(terraformLiquids);
        this.yurantic = new ViewableUnmodifiableArrayList<>(yurantic);
        this.zro = new ViewableUnmodifiableArrayList<>(zro);
        this.alienPets = new ViewableUnmodifiableArrayList<>(alienPets);
        this.betharian = new ViewableUnmodifiableArrayList<>(betharian);
    }

    private static ViewableList<Double> get(PdxScriptObject o, String resource) {
        IPdxScript s = o.get(resource);
        if (s instanceof PdxScriptList) {
            o.use(resource, PdxConstants.LIST);
            return ((PdxScriptList) s).getAsDoubleList();
        } else if (s == null) {
            return CollectionUtil.viewableListOf(0.0D, 0.0D, 0.0D);
        }
        o.use(resource, PdxConstants.DOUBLE);
        return CollectionUtil.viewableListOf((Double) ((PdxScriptValue) s).getValue(), 0.0D, 0.0D);
    }

    public List<Double> getEnergy() {
        return energy.getView();
    }

    public List<Double> getMinerals() {
        return minerals.getView();
    }

    public List<Double> getFood() {
        return food.getView();
    }

    public List<Double> getPhysicsResearch() {
        return physicsResearch.getView();
    }

    public List<Double> getSocietyResearch() {
        return societyResearch.getView();
    }

    public List<Double> getEngineeringResearch() {
        return engineeringResearch.getView();
    }

    public List<Double> getInfluence() {
        return influence.getView();
    }

    public List<Double> getUnity() {
        return unity.getView();
    }

    public List<Double> getAldar() {
        return aldar.getView();
    }

    public List<Double> getDarkMatter() {
        return darkMatter.getView();
    }

    public List<Double> getEngos() {
        return engos.getView();
    }

    public List<Double> getGaranthium() {
        return garanthium.getView();
    }

    public List<Double> getLivingMetal() {
        return livingMetal.getView();
    }

    public List<Double> getLythuric() {
        return lythuric.getView();
    }

    public List<Double> getOrillium() {
        return orillium.getView();
    }

    public List<Double> getPitharan() {
        return pitharan.getView();
    }

    public List<Double> getSatramene() {
        return satramene.getView();
    }

    public List<Double> getTeldar() {
        return teldar.getView();
    }

    public List<Double> getTerraformGases() {
        return terraformGases.getView();
    }

    public List<Double> getTerraformLiquids() {
        return terraformLiquids.getView();
    }

    public List<Double> getYurantic() {
        return yurantic.getView();
    }

    public List<Double> getZro() {
        return zro.getView();
    }

    public List<Double> getAlienPets() {
        return alienPets.getView();
    }

    public List<Double> getBetharian() {
        return betharian.getView();
    }
}
