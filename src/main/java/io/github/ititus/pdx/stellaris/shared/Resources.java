package io.github.ititus.pdx.stellaris.shared;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.set.ImmutableSet;

import java.util.function.Predicate;

import static java.util.function.Predicate.not;

public record Resources(
        double energy,
        double minerals,
        double food,
        double physicsResearch,
        double societyResearch,
        double engineeringResearch,
        double influence,
        double unity,
        double alloys,
        double consumerGoods,
        double volatileMotes,
        double exoticGases,
        double rareCrystals,
        double livingMetal,
        double zro,
        double darkMatter,
        double nanites,
        double minorArtifacts
) {

    private static final ImmutableSet<String> RESOURCES = Sets.immutable.of(
            "energy",
            "minerals",
            "food",
            "physics_research",
            "society_research",
            "engineering_research",
            "influence",
            "unity",
            "alloys",
            "consumer_goods",
            "volatile_motes",
            "exotic_gases",
            "rare_crystals",
            "sr_living_metal",
            "sr_zro",
            "sr_dark_matter",
            "nanites",
            "minor_artifacts"
    );

    public static final Predicate<String> FILTER = not(RESOURCES::contains);

    public Resources() {
        this(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public Resources(IPdxScript s) {
        this(s.expectObject());
    }

    public Resources(PdxScriptObject o) {
        this(
                o.getDouble("energy", 0),
                o.getDouble("minerals", 0),
                o.getDouble("food", 0),
                o.getDouble("physics_research", 0),
                o.getDouble("society_research", 0),
                o.getDouble("engineering_research", 0),
                o.getDouble("influence", 0),
                o.getDouble("unity", 0),
                o.getDouble("alloys", 0),
                o.getDouble("consumer_goods", 0),
                o.getDouble("volatile_motes", 0),
                o.getDouble("exotic_gases", 0),
                o.getDouble("rare_crystals", 0),
                o.getDouble("sr_living_metal", 0),
                o.getDouble("sr_zro", 0),
                o.getDouble("sr_dark_matter", 0),
                o.getDouble("nanites", 0),
                o.getDouble("minor_artifacts", 0)
        );
    }

    public double get(String type) {
        return switch (type) {
            case "energy" -> energy;
            case "minerals" -> minerals;
            case "food" -> food;
            case "physics_research" -> physicsResearch;
            case "society_research" -> societyResearch;
            case "engineering_research" -> engineeringResearch;
            case "influence" -> influence;
            case "unity" -> unity;
            case "alloys" -> alloys;
            case "consumer_goods" -> consumerGoods;
            case "volatile_motes" -> volatileMotes;
            case "exotic_gases" -> exoticGases;
            case "rare_crystals" -> rareCrystals;
            case "sr_living_metal" -> livingMetal;
            case "sr_zro" -> zro;
            case "sr_dark_matter" -> darkMatter;
            case "nanites" -> nanites;
            case "minor_artifacts" -> minorArtifacts;
            default -> throw new IllegalArgumentException("unknown resource type " + type);
        };
    }

    public Resources add(Resources other) {
        return new Resources(
                energy + other.energy,
                minerals + other.minerals,
                food + other.food,
                physicsResearch + other.physicsResearch,
                societyResearch + other.societyResearch,
                engineeringResearch + other.engineeringResearch,
                influence + other.influence,
                unity + other.unity,
                alloys + other.alloys,
                consumerGoods + other.consumerGoods,
                volatileMotes + other.volatileMotes,
                exoticGases + other.exoticGases,
                rareCrystals + other.rareCrystals,
                livingMetal + other.livingMetal,
                zro + other.zro,
                darkMatter + other.darkMatter,
                nanites + other.nanites,
                minorArtifacts + other.minorArtifacts
        );
    }
}
