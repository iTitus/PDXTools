---
permalink: "/script"
layout: default
---

# The Clausewitz Engine Scripting Language

Examples taken from save games and game files.

## Literals

### String
```
"Lem v3.1.2"
```

### Keyword / String without quotes
```
standard
GFX_planet_type_desert
```

### Integer
```
81477
```

### Decimal
```
123.16
```

### Date
```
"2200.06.21"
```

### Color

Not used in save files.

```
hsv { 0.50 0.2 0.8 }
rgb { 142 188 241 }
```

### Boolean
```
yes
no
```

### None
```
none
```

### Parameters

Not used in save files.

Enclose keywords in `$` to make them parameters. Only supported in some specific contexts.

```
# definition
example_scripted_effect = {
    shift_ethic = ethic_$ETHIC$
}

# calling
example_scripted_effect = { ETHIC = materialist }
```

They also support default values, separated with a `|`.

```
$COUNT|1$
```

### Parameter conditions

Not used in save files.

Use square brackets to make code only execute when a certain parameter was passed and not set to `no`. In this example the parameter is `homeworld`.

```
last_created_species = {
    save_global_event_target_as = vaadwaurSpecies
    [[homeworld]set_species_homeworld = event_target:tempHomeworld]
}
```

### Colon

Not used in save files.

In some contexts you can dynamically access variables using `:`.

```
create_species = {
    name = random
    class = random_non_machine
    portrait = random
    traits = random
    homeworld = event_target:planet_option_1
    effect = {
        save_event_target_as = omnicodex_species
    }
}
event_target:planet_option_1 = {
    while = {
        count = 3
        create_pop = {
            species = event_target:omnicodex_species
        }
    }
}
```


### Scripted variables

Not used in save files.

Keywords prefixed with `@` are scripted variables.
They are defined somewhere in the script and then referenced later - sometimes in other files.

```
# declaration
@my_cool_variable=42

# usage
value=@my_cool_variable
```

### Inline math

Not used in save files.

You must escape the first bracket (`\[`) in most contexts.
It is possible to reference scripted variables (leave out the `@`) and parameters (with the `$`).

```
@\[ <math expression> ]
```

### Dynamic keywords

Not used in save files.

In some contexts a keyword can be dynamically built with a scope.

```
has_country_flag = shoulders_origin_@from.planet
```

This will get executed in the scope of a country while in an event context. `@from.planet` will evaluate to the id of the planet where the event was fired.

If that id is 1337 we would have:

```
has_country_flag = shoulders_origin_1337
```


## Lists

Whitespace or new-line separated values enclosed in brackets.
Values may be other objects, lists or literals.

### One line
```
{
    diplo_stance_expansionist unrestricted_wars orbital_bombardment_indiscriminate
}
{
    3 0 17 7 5 2 
}
{
    no no no no no no no no no no no no 
}
```

### Multiple Lines
```
required_dlcs={
    "Ancient Relics Story Pack"
    "Anniversary Portraits"
    # ...
}
jobs_cache={
    {
        num_employed=0
        max_employed=0
        max_without_prio=0
    }
    {
        num_employed=0
        max_employed=0
        max_without_prio=0
    }
    {
        num_employed=0
        max_employed=0
        max_without_prio=0
    }
#...
```

### Implicit Lists

In an object context a key is given a value multiple times.

```
nebula={
    coordinate={
        # decimal
        x=123.16
        y=77.73
        # u32, is -1 as signed i32
        origin=4294967295
        # boolean
        randomized=yes
    }
    name="Abbanis Nebula"
    radius=30
    galactic_object=124
    galactic_object=190
    galactic_object=277
    galactic_object=335
    galactic_object=344
    galactic_object=362
    galactic_object=447
    galactic_object=454
    galactic_object=510
}
nebula={
    coordinate={
        x=57.67
        y=-189.74
        origin=4294967295
        randomized=yes
    }
    name="Twin Fangs Nebula"
    radius=30
    galactic_object=102
    galactic_object=127
    galactic_object=179
    galactic_object=307
}
# ...
```

#### Order matters!
```
tech_status={
    technology="tech_space_exploration"
    level=1
    technology="tech_corvettes"
    level=1
    technology="tech_starbase_1"
    level=1
    technology="tech_starbase_2"
    level=1
    technology="tech_assault_armies"
    level=1
    # ...
}
```


## Objects

`key=value` pairs.

Keys are literals.
Values may be other objects, lists or literals.

There are other relational operators used instead of `=` in the game files: for example `>` or `<=`.

### Integer keys
```
species_db={
    0={
        traits={
        }
    }
    1={
        name_list="HUMAN1"
        name="Human"
        plural="Humans"
        adjective="Human"
        class="HUM"
        portrait="human"
        traits={
            trait="trait_adaptive"
            trait="trait_nomadic"
            trait="trait_wasteful"
            trait="trait_pc_continental_preference"
        }
        home_planet=3
    }
    # ...
}
```

### String keys
```
ship_names={
    "Sojourner"=1
    "Bengal"=1
    "Iguana"=1
    "Costaguana"=1
    "Yuenü"=1
}
```


## Examples from the game files

These are probably hand-written and may contain errors!

### file: `common/technology/00_soc_tech.txt`
```
tech_planetary_unification = {
    cost = @tier1cost1 # this is a variable defined in another file
    area = society
    tier = 1
    category = { statecraft }  # this is a list of just one keyword
    prerequisites = { "tech_planetary_government" } # this is a list of just one string
    weight = @tier1weight1

    gateway = capital

    feature_flags = { # another list, this time multi-line
        campaign_edicts
    }

    # also unlocks planet-happiness edict # <- a comment from the original file

    modifier = { # all modifiers that this technology unlocks
        country_base_unity_produces_add = 2
    }

    weight_modifier = {
        factor = 4
        modifier = {
            factor = 0.75
            NOT = {
                OR = {
                    has_ethic = ethic_pacifist
                    has_ethic = ethic_fanatic_pacifist
                }
            }
        }
    }

    ai_weight = {
        factor = 2
        modifier = {
            factor = 0.75
            NOT = {
                OR = {
                    has_ethic = ethic_pacifist
                    has_ethic = ethic_fanatic_pacifist
                }
            }
        }
        modifier = {
            factor = 1.25
            has_ethic = ethic_authoritarian
        }
        modifier = {
            factor = 1.5
            has_ethic = ethic_fanatic_authoritarian
        }
        modifier = {
            factor = 1.25
            research_leader = {
                area = society
                has_trait = "leader_trait_expertise_statecraft"
            }
        }
    }
}
```

### file: `common/planet_classes/00_planet_classes.txt`
```
pc_desert = {
    entity = "desert_planet"
    icon = GFX_planet_type_desert

    climate = "dry"
    initial = yes

    entity_scale = @planet_standard_scale

    atmosphere_color        = hsv { 0.50 0.2 0.8 } # color value!
    atmosphere_intensity    = 1.0
    atmosphere_width        = 0.5

    min_distance_from_sun = @habitable_min_distance
    max_distance_from_sun = @habitable_max_distance
    spawn_odds = @habitable_spawn_odds

    city_color_lut = "gfx/portraits/misc/colorcorrection_desert.dds"

    extra_orbit_size = 0
    extra_planet_count = 0

    chance_of_ring = 0.2

    planet_size = { min = @habitable_planet_min_size max = @habitable_planet_max_size }
    moon_size = { min = @habitable_moon_min_size max = @habitable_moon_max_size }

    production_spawn_chance = 0.4

    colonizable = yes
    district_set = standard
    uses_alternative_skies_for_moons = no

    carry_cap_per_free_district = @carry_cap_normal
}
```

### file: `events/colony_events_1.txt`
```
planet_event = {
    id = colony.1
    title = "colony.1.name"
    desc = "colony.1.desc"
    picture = GFX_evt_alien_nature
    show_sound = event_alien_nature
    location = ROOT
    trackable = yes

    pre_triggers = {
        has_owner = yes
        is_homeworld = no
        original_owner = yes
        is_ai = no
        has_ground_combat = no
        is_capital = no
        is_occupied_flag = no
    }

    trigger = {
        owner = {
            NOT = { has_authority = auth_machine_intelligence }
        }
        num_pops > 0 # not only '=', but also relation ops!
        OR = { # logical operators
            is_planet_class = pc_continental
            is_planet_class = pc_tropical
            is_planet_class = pc_arid
            is_planet_class = pc_tundra
            is_planet_class = pc_savannah
        }
        NOR = {
            has_global_flag = migrating_forests_global
            has_planet_flag = colony_event
        }
    }

    is_triggered_only = yes

    immediate = {
        set_global_flag = migrating_forests_global
        set_planet_flag = colony_event
        add_deposit = d_migrating_forests
        add_modifier = {
            modifier = "migrating_forests"
            days = -1
        }
    }

    option = {
        name = colony.1.a
        tooltip = {
            add_deposit = d_migrating_forests
        }
        begin_event_chain = {
            event_chain = "migrating_forests_chain"
            target = ROOT
        }
        enable_special_project = {
            name = "MIGRATING_FORESTS_1_PROJECT"
            location = this
            owner = root
        }
        enable_special_project = {
            name = "MIGRATING_FORESTS_2_PROJECT"
            location = this
            owner = root
        }
    }
}
```

## file: `common/scripted_effects/00_scripted_effects.txt`
```
death_cult_sacrifice_effect = {
    #First we need to get the variables that determine how big the boon is

    # Formula is ( initiates * random factor ) / (total pops), and then round that to the nearest 0.1 to avoid weird-looking results
    random_list = {
        #This is effectively ( base * random ), where base = 30
        #It is balanced so that sacrificing 5% of your pops gives a really good result

        1 = {
            set_variable = {
                which = sacrifice_random_mult
                value = 15
            }
        }
        4 = {
            set_variable = {
                which = sacrifice_random_mult
                value = 20
            }
        }
        1 = {
            set_variable = {
                which = sacrifice_random_mult
                value = 25
            }
        }
    }
    if = { #small empire penalty: losing 1 pop out of 30 is not the same as 30 out of 900 with logarithmic growth
        limit = { num_pops < 100 }
        multiply_variable = {
            which = sacrifice_random_mult
            value = 0.8
        }
        if = {
            limit = { num_pops < 50 }
            multiply_variable = {
                which = sacrifice_random_mult
                value = 0.8
            }
        }
    }
    export_trigger_value_to_variable = {
        trigger = num_assigned_jobs
        parameters = {
            job = mortal_initiate
        }
        variable = sacrifice_result_mult
    }
    multiply_variable = {
        which = sacrifice_result_mult
        value = sacrifice_random_mult
    }
    clear_variable = sacrifice_random_mult
    divide_variable = {
        which = sacrifice_result_mult
        value = trigger:num_pops
    }
    round_variable_to_closest = {
        which = sacrifice_result_mult
        value = 0.1
    }

    #We also need the edicts length multiplier for the modifiers
    export_modifier_to_variable = {
        modifier = edict_length_mult
        variable = edict_length_modifiers
    }
    change_variable = { # Needs to be 1 + mult
        which = edict_length_modifiers
        value = 1
    }

    #Now we do the sacrifice
    every_owned_pop = {
        limit = { has_job = mortal_initiate }
        kill_pop = yes
    }
}
```


## Common errors

- Encoding errors: Latin-1 vs UTF-8
- Bracket errors
- Typos in `@variable` names
- Double definition of value

All these errors do not cause the game to crash, they are ignored and thus might lead to issues while playing.


## Types of script

There can be two types of script: static and dynamic.

Static script just represents data - like in save files.

Dynamic scripts can also contain triggers and effects. Triggers check something in the game and evaluate to boolean values while effects have no return value and change something in the game. They are governed by Scopes. A scope can be thought of as `this` in an object-oriented programming language - the current context where this script is executed. This can for example be a planet, a system or a pop.

Each scope type has specific triggers and effects that can be executed.
You can change your current scope. For example when you are in the scope of a country you can use `research_leader = { area = ? ... }` to execute something in the scope of the leader entity that is the current research leader in the given tech area.


## References

- [Effects]({{ '/script_documentation/effects.log' | relative_url }})
- [Localizations]({{ '/script_documentation/localizations.log' | relative_url }})
- [Modifiers]({{ '/script_documentation/modifiers.log' | relative_url }})
- [Scopes]({{ '/script_documentation/scopes.log' | relative_url }})
- [Triggers]({{ '/script_documentation/triggers.log' | relative_url }})
