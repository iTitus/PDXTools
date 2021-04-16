package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

public class Galaxy {

    public final boolean advancedStartsNearPlayer;
    public final boolean caravaneersEnabled;
    public final boolean xenoCompatibilityEnabled;
    public final boolean scaling;
    public final boolean clustered;
    public final boolean randomEmpires;
    public final boolean randomFallenEmpires;
    public final boolean randomMarauderEmpires;
    public final boolean randomAdvancedEmpires;
    public final boolean ironman;
    public final int numEmpires;
    public final int numAdvancedEmpires;
    public final int numFallenEmpires;
    public final int numMarauderEmpires;
    public final int midGameStart;
    public final int endGameStart;
    public final int victoryYear;
    public final int numGuaranteedColonies;
    public final double habitability;
    public final double primitive;
    public final double crises;
    public final double technology;
    public final double coreRadius;
    public final double numGateways;
    public final double numWormholePairs;
    public final double numHyperlanes;
    public final String template;
    public final String shape;
    public final String playerLocations;
    public final String difficulty;
    public final String aggressiveness;
    public final String crisisType;
    public final String name;
    public final ImmutableList<EmpireDesign> designs;

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
        this.caravaneersEnabled = o.getBoolean("caravaneers_enabled");
        this.xenoCompatibilityEnabled = o.getBoolean("xeno_compatibility_enabled");
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
        this.crisisType = o.getString("crisis_type");
        this.name = o.getString("name");
        this.ironman = o.getBoolean("ironman");
        this.numGateways = o.getDouble("num_gateways");
        this.numWormholePairs = o.getDouble("num_wormhole_pairs");
        this.numHyperlanes = o.getDouble("num_hyperlanes");
        this.midGameStart = o.getInt("mid_game_start");
        this.endGameStart = o.getInt("end_game_start");
        this.victoryYear = o.getInt("victory_year");
        this.numGuaranteedColonies = o.getInt("num_guaranteed_colonies");
        this.designs = o.getImplicitListAsList("design", EmpireDesign::new);
    }
}
