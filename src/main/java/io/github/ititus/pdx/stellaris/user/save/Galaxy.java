package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class Galaxy {

    private final boolean advancedStartsNearPlayer, scaling, clustered, randomEmpires, randomFallenEmpires, randomMarauderEmpires, randomAdvancedEmpires, ironman;
    private final int numEmpires, numAdvancedEmpires, numFallenEmpires, numMarauderEmpires, midGameStart, endGameStart, victoryYear, numGuaranteedColonies;
    private final double habitability, primitive, crises, technology, coreRadius, numGateways, numWormholePairs, numHyperlanes;
    private final String template, shape, playerLocations, difficulty, aggressiveness, name;
    private final ImmutableList<EmpireDesign> designs;

    public Galaxy(PdxScriptObject o) {
        this.template = o.getString("template");
        this.shape = o.getString("shape");
        this.numEmpires = o.getInt("num_empires");
        this.numAdvancedEmpires = o.getInt("num_advanced_empires");
        this.numFallenEmpires = o.getInt("num_fallen_empires");
        this.numMarauderEmpires = o.getInt("num_marauder_empires");
        this.habitability = o.getDouble("habitability");
        this.primitive = o.getDouble("primitive");
        this.advancedStartsNearPlayer = o.getBoolean("advanced_starts_near_player");
        this.scaling = o.getBoolean("scaling");
        this.crises = o.getDouble("crises");
        this.technology = o.getDouble("technology");
        this.clustered = o.getBoolean("clustered");
        this.randomEmpires = o.getBoolean("random_empires");
        this.randomFallenEmpires = o.getBoolean("random_fallen_empires");
        this.randomMarauderEmpires = o.getBoolean("random_marauder_empires");
        this.randomAdvancedEmpires = o.getBoolean("random_advanced_empires");
        this.coreRadius = o.getDouble("core_radius");
        this.playerLocations = o.getString("player_locations");
        this.difficulty = o.getString("difficulty");
        this.aggressiveness = o.getString("aggressiveness");
        this.name = o.getString("name");
        this.ironman = o.getBoolean("ironman");
        this.numGateways = o.getDouble("num_gateways");
        this.numWormholePairs = o.getDouble("num_wormhole_pairs");
        this.numHyperlanes = o.getDouble("num_hyperlanes");
        this.midGameStart = o.getInt("mid_game_start");
        this.endGameStart = o.getInt("end_game_start");
        this.victoryYear = o.getInt("victory_year");
        this.numGuaranteedColonies = o.getInt("num_guaranteed_colonies");
        this.designs = o.getImplicitList("design").getAsList(EmpireDesign::new);
    }

    public Galaxy(boolean advancedStartsNearPlayer, boolean scaling, boolean clustered, boolean randomEmpires, boolean randomFallenEmpires, boolean randomMarauderEmpires, boolean randomAdvancedEmpires, boolean ironman, int numEmpires, int numAdvancedEmpires, int numFallenEmpires, int numMarauderEmpires, int midGameStart, int endGameStart, int victoryYear, int numGuaranteedColonies, double habitability, double primitive, double crises, double technology, double coreRadius, double numGateways, double numWormholePairs, double numHyperlanes, String template, String shape, String playerLocations, String difficulty, String aggressiveness, String name, ImmutableList<EmpireDesign> designs) {
        this.advancedStartsNearPlayer = advancedStartsNearPlayer;
        this.scaling = scaling;
        this.clustered = clustered;
        this.randomEmpires = randomEmpires;
        this.randomFallenEmpires = randomFallenEmpires;
        this.randomMarauderEmpires = randomMarauderEmpires;
        this.randomAdvancedEmpires = randomAdvancedEmpires;
        this.ironman = ironman;
        this.numEmpires = numEmpires;
        this.numAdvancedEmpires = numAdvancedEmpires;
        this.numFallenEmpires = numFallenEmpires;
        this.numMarauderEmpires = numMarauderEmpires;
        this.midGameStart = midGameStart;
        this.endGameStart = endGameStart;
        this.victoryYear = victoryYear;
        this.numGuaranteedColonies = numGuaranteedColonies;
        this.habitability = habitability;
        this.primitive = primitive;
        this.crises = crises;
        this.technology = technology;
        this.coreRadius = coreRadius;
        this.numGateways = numGateways;
        this.numWormholePairs = numWormholePairs;
        this.numHyperlanes = numHyperlanes;
        this.template = template;
        this.shape = shape;
        this.playerLocations = playerLocations;
        this.difficulty = difficulty;
        this.aggressiveness = aggressiveness;
        this.name = name;
        this.designs = designs;
    }

    public boolean isAdvancedStartsNearPlayer() {
        return advancedStartsNearPlayer;
    }

    public boolean isScaling() {
        return scaling;
    }

    public boolean isClustered() {
        return clustered;
    }

    public boolean isRandomEmpires() {
        return randomEmpires;
    }

    public boolean isRandomFallenEmpires() {
        return randomFallenEmpires;
    }

    public boolean isRandomMarauderEmpires() {
        return randomMarauderEmpires;
    }

    public boolean isRandomAdvancedEmpires() {
        return randomAdvancedEmpires;
    }

    public boolean isIronman() {
        return ironman;
    }

    public int getNumEmpires() {
        return numEmpires;
    }

    public int getNumAdvancedEmpires() {
        return numAdvancedEmpires;
    }

    public int getNumFallenEmpires() {
        return numFallenEmpires;
    }

    public int getNumMarauderEmpires() {
        return numMarauderEmpires;
    }

    public int getMidGameStart() {
        return midGameStart;
    }

    public int getEndGameStart() {
        return endGameStart;
    }

    public int getVictoryYear() {
        return victoryYear;
    }

    public int getNumGuaranteedColonies() {
        return numGuaranteedColonies;
    }

    public double getHabitability() {
        return habitability;
    }

    public double getPrimitive() {
        return primitive;
    }

    public double getCrises() {
        return crises;
    }

    public double getTechnology() {
        return technology;
    }

    public double getCoreRadius() {
        return coreRadius;
    }

    public double getNumGateways() {
        return numGateways;
    }

    public double getNumWormholePairs() {
        return numWormholePairs;
    }

    public double getNumHyperlanes() {
        return numHyperlanes;
    }

    public String getTemplate() {
        return template;
    }

    public String getShape() {
        return shape;
    }

    public String getPlayerLocations() {
        return playerLocations;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getAggressiveness() {
        return aggressiveness;
    }

    public String getName() {
        return name;
    }

    public ImmutableList<EmpireDesign> getDesigns() {
        return designs;
    }
}
