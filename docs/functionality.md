---
permalink: "/functionality"
layout: default
---

# Functionality

## History

First commit in June 2018, but that was a prototype that already did something. Local work must have started a few weeks before I think.

What can PDXTools do right now?

## dds/ddsfx project

- In another repository
- Read `dds` images (DirectDrawSurface), an image format made by Microsoft for DirectX.
  - Targets: Java `BufferedImage` and JavaFX `Image`


## stellaris-galaxy-<jfx|lwjgl>-viewer subproject

- Render the galaxy from a save file
  - also supports a system view
- render dds images from the game files
- javafx frontend
- lwjgl is just a small project to learn OpenGL rendering - no functionality yet

![my galaxy view]({{ '/img/my_galaxy_view.png' | relative_url}})
![my system view]({{ '/img/my_system_view.png' | relative_url}})


## pdx-tools subproject

- Parse PDXScript: both game files (that includes content but also graphical assets like model metadata and UI) and save games
  - Target: a JSON like format of maps, lists and values
- Save files from Stellaris are translated into Java Objects for easier access and validation
- A subset of Stellaris game files can be interpreted
  - Uses the JSON-like format as base
- Basic analysis/printing to show that the parsing/interpreting worked
- Use a library to generate `.patch` files for game files, because of errors that need to be fixed

### Example patches

```patch
--- a/Stellaris/gfx/models/ships/caravaneer_01/_caravaneer_01_ships_entities.asset
+++ b/Stellaris/gfx/models/ships/caravaneer_01/_caravaneer_01_ships_entities.asset
@@ -240,8 +240,8 @@
     game_data = {
         trail_locators = {
             "engine_large_01" =         { width = @large_trail_W     lenght = @large_trail_L       }
-            "engine_medium_01" =         { width = @mnedium_trail_W     lenght = @medium_trail_L       }
-            "engine_medium_02" =         { width = @mnedium_trail_W     lenght = @medium_trail_L       }
+            "engine_medium_01" =         { width = @medium_trail_W     lenght = @medium_trail_L       }
+            "engine_medium_02" =         { width = @medium_trail_W     lenght = @medium_trail_L       }
         }
     }
     attach = { "root" = "caravaneer_cruiser_01_lights_entity" }
```
```patch
--- a/Stellaris/common/technology/00_soc_tech.txt
+++ b/Stellaris/common/technology/00_soc_tech.txt
@@ -4235,7 +4235,6 @@
     is_dangerous = yes
     prerequisites = { "tech_precognition_interface" }
     weight = @tier5weight2
-    is_dangerous = yes
     is_reverse_engineerable = no
 
     feature_flags = {
```
```patch
--- a/Stellaris/dlc_metadata/dlc_info.txt
+++ b/Stellaris/dlc_metadata/dlc_info.txt
@@ -34,7 +34,7 @@
         steam_id = "447680"
         microsoft_store_id = "9MT1H9QPRPZG"
         gog_store_id = ""
-        paradoxplaza_store_url ""
+        paradoxplaza_store_url = ""
         category = "content_pack"
         gui = "arachnoid"
         icon = "GFX_arachnoid"
```
```patch
--- a/Stellaris/gfx/models/ships/titans/molluscoid_01/_molluscoid_titan_meshes.gfx
+++ b/Stellaris/gfx/models/ships/titans/molluscoid_01/_molluscoid_titan_meshes.gfx
@@ -65,4 +65,3 @@
     }
 }
 
-}
```


### Technology definition from the game files

`common/scripted_variables/00_scripted_variables.txt`:
```
@tier5cost2 = 40000
@tier4weight2 = 40
@ap_technological_ascendancy_rare_tech = 1.5
@federation_perk_factor = 2
```

`common/technology/00_soc_tech.txt`:
```
tech_ascension_theory = {
    area = society
    cost = @tier5cost2
    tier = 5
    category = { statecraft }
    ai_update_type = all
    is_rare = yes
    weight = @tier5weight2

    modifier = {
        ascension_perks_add = 1
    }

    feature_flags = {
        unity_ambitions
    }

    weight_modifier = {
        factor = 0.5
        modifier = {
            factor = 0.20
            NOR = {
                has_modifier = "curator_insight"
                research_leader = {
                    area = society
                    has_trait = "leader_trait_curator"
                }
                research_leader = {
                    area = society
                    has_trait = "leader_trait_expertise_statecraft"
                }
            }
        }
        modifier = {
            factor = @ap_technological_ascendancy_rare_tech
            has_ascension_perk = ap_technological_ascendancy
        }
        modifier = {
            factor = @federation_perk_factor
            has_federation = yes
            federation = {
                has_federation_perk = rare_tech_boost
                any_member = { has_technology = tech_ascension_theory }
            }
        }
        modifier = {
            factor = 1.25
            research_leader = {
                area = society
                has_trait = "leader_trait_expertise_statecraft"
            }
        }
    }

    ai_weight = {
        weight = 9999
    }
}
```

![Ascension Theory]({{ '/img/ascension_theory.jpg' | relative_url}})

### Technology class

```java
public record Technology(
        StellarisGame game,
        String name,
        int cost,
        Area area,
        int tier,
        String category,
        int levels,
        int costPerLevel,
        ImmutableList<String> prerequisites,
        double weight,
        String gateway,
        String aiUpdateType,
        boolean isRare,
        boolean isDangerous,
        boolean isReverseEngineerable,
        boolean startTech,
        Modifier modifier,
        ImmutableList<String> featureFlags,
        TechnologyPreReqForDesc prereqforDesc,
        ImmutableList<Trigger> potential,
        TechnologyWeightModifiers weightModifier,
        TechnologyWeightModifiers aiWeight,
        ImmutableList<String> weightGroups,
        ImmutableObjectDoubleMap<String> modWeightIfGroupPicked
) implements Localisable {

    public static Technology of(StellarisGame game, String name, IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        int cost = o.getInt("cost", 0);
        Area area = o.getEnum("area", Area::of);
        int tier = o.getInt("tier");
        String category = o.getListAsStringList("category").getOnly();
        int levels = o.getInt("levels", 1);
        int costPerLevel = o.getInt("cost_per_level", 0);
        ImmutableList<String> prerequisites = o.getListAsEmptyOrStringList("prerequisites");
        double weight = o.getDouble("weight", 0);
        String gateway = o.getString("gateway", null);
        String aiUpdateType = o.getString("ai_update_type", null);
        boolean isRare = o.getBoolean("is_rare", false);
        boolean isDangerous = o.getBoolean("is_dangerous", false);
        boolean isReverseEngineerable = o.getBoolean("is_reverse_engineerable", true);
        boolean startTech = o.getBoolean("start_tech", false);
        Modifier modifier = o.getObjectAsNullOr("modifier", o_ -> new Modifier(game, o_));
        ImmutableList<String> featureFlags = o.getListAsEmptyOrStringList("feature_flags");
        TechnologyPreReqForDesc prereqforDesc = o.getObjectAsNullOr("prereqfor_desc", TechnologyPreReqForDesc::new);
        ImmutableList<Trigger> potential = o.getObjectAs("potential", game.triggers::create, Lists.immutable.empty());
        TechnologyWeightModifiers weightModifier = o.getObjectAsNullOr("weight_modifier", o_ -> TechnologyWeightModifiers.of(game, o_));
        TechnologyWeightModifiers aiWeight = o.getObjectAsNullOr("ai_weight", o_ -> TechnologyWeightModifiers.of(game, o_));
        ImmutableList<String> weightGroups = o.getListAsEmptyOrStringList("weight_groups");
        ImmutableObjectDoubleMap<String> modWeightIfGroupPicked = o.getObjectAsEmptyOrStringDoubleMap("mod_weight_if_group_picked");
        return new Technology(game, name, cost, area, tier, category, levels, costPerLevel, prerequisites, weight, gateway, aiUpdateType, isRare, isDangerous, isReverseEngineerable, startTech, modifier, featureFlags, prereqforDesc, potential, weightModifier, aiWeight, weightGroups, modWeightIfGroupPicked);
    }
  
    // ...
}
```

## Triggers and scopes

In `StellarisGame` class:
```java
triggers.addEngineTrigger("research_leader", ResearchLeaderTrigger::new);
```

```java
public class ResearchLeaderTrigger extends TriggerBasedTrigger {

    public final Technology.Area area;

    public ResearchLeaderTrigger(Triggers triggers, IPdxScript s) {
        super(triggers, s, not("area"::equals));
        this.area = s.expectObject().getEnum("area", Technology.Area::of);
    }

    @Override
    public boolean evaluate(Scope scope) {
        CountryScope cs = CountryScope.expect(scope);
        LeaderScope ls = cs.getResearchLeader(area);
        return ls != null && evaluateAnd(ls, children);
    }

    // ...
}
```

```java
public class CountryScope extends StellarisScope {

    private final Country country;
    
    public LeaderScope getResearchLeader(Technology.Area area) {
        int leaderId = country.techStatus.leaders.getLeader(area);
        return leaderId != -1 ? new LeaderScope(game, save, leaderId) : null;
    }
    
    // ...
}
```

```java
public class LeaderScope extends StellarisScope {

    private final Leader leader;

    @Override
    public boolean evaluateValueTrigger(String name, PdxScriptValue v) {
        return switch (name) {
            case "has_level" -> ScopeHelper.compare(leader.level, v);
            case "has_trait" -> leader.hasTrait(v.expectString());
            default -> super.evaluateValueTrigger(name, v);
        };
    }

    // ...
}
```


### Example output

```
Loading common/scripted_variables
...done (250.470000 ms)
Creating triggers
...done (23.133300 ms)
Loading common/scripted_triggers
...done (40.848400 ms)
Creating effects
...done (519.500 µs)
Loading common
  Loading deposits
  ...done (115.700900 ms)
  Loading planet_classes
  ...done (48.987000 ms)
  Loading species_classes
  ...done (4.987700 ms)
  Loading technology
  ...done (105.465200 ms)
  Loading technology/category
  ...done (3.984300 ms)
  Loading technology/tier
  ...done (19.665100 ms)
  Loading Raw Common Data
  ...done (5.600 µs)
  Done
...done (301.256300 ms)
Loading gfx
...done (4.307600 ms)
Loading dlc
  Loading dlc001_symbols_of_domination
  ...done (4.160000 ms)
  Loading dlc003_signup_bonus
  ...done (767.300 µs)
  Loading dlc004_plantoid
  ...done (2.142600 ms)
  Loading dlc010_creatures_of_the_void
  ...done (730.400 µs)
  Loading dlc012_leviathans
  ...done (552.000 µs)
  Loading dlc013_horizon_signal
  ...done (589.000 µs)
  Loading dlc014_utopia
  ...done (517.800 µs)
  Loading dlc015_anniversary
  ...done (529.000 µs)
  Loading dlc016_synthetic_dawn
  ...done (539.000 µs)
  Loading dlc017_apocalypse
  ...done (562.400 µs)
  Loading dlc018_humanoids
  ...done (583.600 µs)
  Loading dlc019_distant_stars
  ...done (580.500 µs)
  Loading dlc020_megacorp
  ...done (579.800 µs)
  Loading dlc021_ancient_relics
  ...done (516.500 µs)
  Loading dlc022_lithoids
  ...done (534.000 µs)
  Loading dlc023_federations
  ...done (545.500 µs)
  Loading dlc024_necroids
  ...done (540.500 µs)
  Loading dlc025_nemesis
  ...done (596.000 µs)
  Done
...done (20.498400 ms)
Loading Localisation
...done (2.043088400 s)
Loading Raw Game Data
...done (7.700 µs)
Loading Assets
...done (10.673882300 s)
Done
Game Data Load Time: 13.624825900 s
Test Save Load Time: 5.022360200 s
Total Loading Time: 18.650443300 s

------ Output a technology ------

Technology Ascension Theory (tech_ascension_theory)
Description: Our history is a repeating cycle of dramatic technological, political, and cultural leaps; these leaps are often preceded by periods of upheaval and followed by times of relative stability. By identifying the core, recurring premises of these leaps, we may be able to effectively short-circuit the cycle for our own benefit.
Cost: 40000
Area: Society
Tier: 5
Category: statecraft
Weight: 25.0 (Modified: 12.5)
AI Update Type: all
Properties: rare, not reverse engineerable
Modifiers:
 - mod_ascension_perks_add: 1.0
Feature Flags: unity_ambitions
Weight Modifiers:
 - Factor: 0.5
 - Modifier:
     - Factor: 0.2
     - Triggers:
         - nor:
             - has_modifier="curator_insight"
             - research_leader in society:
                 - has_trait="leader_trait_curator"
             - research_leader in society:
                 - has_trait="leader_trait_expertise_statecraft"
 - Modifier:
     - Factor: 1.5
     - Triggers:
         - has_ascension_perk="ap_technological_ascendancy"
 - Modifier:
     - Factor: 2.0
     - Triggers:
         - has_federation=yes
         - in scope federation:
             - has_federation_perk="rare_tech_boost"
             - any_member:
                 - has_technology="tech_ascension_theory"
 - Modifier:
     - Factor: 1.25
     - Triggers:
         - research_leader in society:
             - has_trait="leader_trait_expertise_statecraft"
AI Weight Modifiers:


------ Tech info from a save game ------
=> calculating the weights requires interpreting the scripts


Currently researching (physics): Flash Coolant (tech_repeatable_weapon_type_energy_fire_rate, physics, tier=5, level=17/-1) 0/130000
Currently researching (society): Aggressive Conditioning (tech_repeatable_improved_army_damage, society, tier=5, level=11/-1) 0/100000
Currently researching (engineering): Heat Recyclers (tech_repeatable_weapon_type_strike_craft_fire_rate, engineering, tier=5, level=7/-1) 0/80000

Tech Alternatives: 4
Always available: 

Tech Weights for Thorquell Alliance in Physics:
Focusing Arrays (tech_repeatable_weapon_type_energy_damage, tier=5, cost=135000, level=18/-1) W: 12.5 (33.3%)
Shield Harmonics (tech_repeatable_improved_shield_output, tier=5, cost=130000, level=17/-1) W: 12.5 (33.3%)
Applied Superconductivity (tech_repeatable_improved_tile_energy_output, tier=5, cost=130000, level=17/-1) W: 12.5 (33.3%)

------ Tech weights from the same game ------

Tech Weights for Thorquell Alliance in Society:
Transgenic Crops (tech_repeatable_improved_tile_food_output, tier=5, cost=100000, level=11/-1) W: 12.5 (20.0%)
Administrative Efficiency (tech_repeatable_improved_core_system_cap, tier=5, cost=105000, level=12/-1) W: 12.5 (20.0%)
Nerve Dampeners (tech_repeatable_improved_army_health, tier=5, cost=100000, level=11/-1) W: 12.5 (20.0%)
Proclamation Broadcasts (tech_repeatable_improved_edict_length, tier=5, cost=105000, level=12/-1) W: 12.5 (20.0%)
Cell Revitalization (tech_repeatable_improved_leader_life_span, tier=5, cost=100000, level=11/-1) W: 12.5 (20.0%)


Tech Weights for Thorquell Alliance in Engineering:
Thermodynamic Yield Control (tech_repeatable_weapon_type_explosive_damage, tier=5, cost=85000, level=8/-1) W: 12.5 (11.1%)
Fortified Core Layers (tech_repeatable_improved_military_station_health, tier=5, cost=85000, level=8/-1) W: 12.5 (11.1%)
Matter Compression (tech_repeatable_improved_armor_output, tier=5, cost=85000, level=8/-1) W: 12.5 (11.1%)
High-Density Munitions (tech_repeatable_weapon_type_kinetic_damage, tier=5, cost=85000, level=8/-1) W: 12.5 (11.1%)
Miniaturized Pre-Igniters (tech_repeatable_weapon_type_explosive_fire_rate, tier=5, cost=85000, level=8/-1) W: 12.5 (11.1%)
Synchronized Firing Patterns (tech_repeatable_improved_military_station_damage, tier=5, cost=85000, level=8/-1) W: 12.5 (11.1%)
Extraction Patterns (tech_repeatable_improved_tile_mineral_output, tier=5, cost=85000, level=8/-1) W: 12.5 (11.1%)
Synapse Interceptors (tech_repeatable_weapon_type_strike_craft_fire_damage, tier=5, cost=85000, level=8/-1) W: 12.5 (11.1%)
Loader Efficiency (tech_repeatable_weapon_type_kinetic_fire_rate, tier=5, cost=85000, level=8/-1) W: 12.5 (11.1%)

Blocked Techs:

Blocked Tech Weights for Thorquell Alliance in Physics:


Blocked Tech Weights for Thorquell Alliance in Society:
Thought Enforcement (tech_thought_enforcement, tier=4, cost=20000) missing_prereqs=[tech_telepathy] weight=30.0
Morphogenetic Field Mastery (tech_morphogenetic_field_mastery, tier=4, cost=16000) missing_prereqs=[tech_epigenetic_triggers] weight=25.3
Psi Jump Drive (tech_psi_jump_drive_1, tier=5, cost=40000) missing_prereqs=[tech_precognition_interface] weight=0.0
Precognition Interface (tech_precognition_interface, tier=4, cost=20000) missing_prereqs=[tech_telepathy] weight=0.0
Thrall-Worlds (tech_slave_colonies, tier=2, cost=6000) missing_prereqs=[tech_neural_implants] weight=0.0
Agrarian Utopias (tech_housing_agrarian_idyll, tier=3, cost=8000) weight=0.0
Capacity Boosters (tech_capacity_boosters, tier=4, cost=24000) weight=0.0
Gene Seed Purification (tech_gene_seed_purification, tier=4, cost=16000) weight=0.0
Neural Implants (tech_neural_implants, tier=1, cost=3000) weight=0.0
Telepathy (tech_telepathy, tier=3, cost=12000) weight=0.0
Epigenetic Triggers (tech_epigenetic_triggers, tier=2, cost=5000) weight=0.0


Blocked Tech Weights for Thorquell Alliance in Engineering:
Nanite Transmutation (tech_nanite_transmutation, tier=2, cost=6000) weight=0.0
Betharian Refining (tech_mine_betharian, tier=2, cost=2500) weight=0.0
```
