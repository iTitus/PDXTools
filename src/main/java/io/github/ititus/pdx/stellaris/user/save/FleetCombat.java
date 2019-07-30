package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import io.github.ititus.pdx.util.Deduplicator;

import java.time.LocalDate;
import java.util.Objects;

public class FleetCombat {

    private static final Deduplicator<FleetCombat> DEDUPLICATOR = new Deduplicator<>(c -> c.getFormation().getRoot() == -1);

    private final LocalDate startDate;
    private final Coordinate coordinate, startCoordinate;
    private final FormationPos formationPos;
    private final Formation formation;

    private FleetCombat(PdxScriptObject o) {
        this.coordinate = o.getObject("coordinate").getAs(Coordinate::of);
        this.formationPos = o.getObject("formation_pos").getAs(FormationPos::of);
        this.formation = o.getObject("formation").getAs(Formation::of);
        this.startCoordinate = o.getObject("start_coordinate").getAs(Coordinate::of);
        this.startDate = o.getDate("start_date");
    }

    private FleetCombat(LocalDate startDate, Coordinate coordinate, Coordinate startCoordinate, FormationPos formationPos, Formation formation) {
        this.startDate = startDate;
        this.coordinate = coordinate;
        this.startCoordinate = startCoordinate;
        this.formationPos = formationPos;
        this.formation = formation;
    }

    public static FleetCombat of(PdxScriptObject o) {
        return DEDUPLICATOR.deduplicate(new FleetCombat(o));
    }

    public static FleetCombat of(LocalDate startDate, Coordinate coordinate, Coordinate startCoordinate, FormationPos formationPos, Formation formation) {
        return DEDUPLICATOR.deduplicate(new FleetCombat(startDate, coordinate, startCoordinate, formationPos, formation));
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Coordinate getStartCoordinate() {
        return startCoordinate;
    }

    public FormationPos getFormationPos() {
        return formationPos;
    }

    public Formation getFormation() {
        return formation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FleetCombat)) {
            return false;
        }
        FleetCombat that = (FleetCombat) o;
        return Objects.equals(startDate, that.startDate) && Objects.equals(coordinate, that.coordinate) && Objects.equals(startCoordinate, that.startCoordinate) && Objects.equals(formationPos, that.formationPos) && Objects.equals(formation, that.formation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, coordinate, startCoordinate, formationPos, formation);
    }
}
