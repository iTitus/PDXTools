package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.util.Date;

public class FleetCombat {

    private final Date startDate;
    private final Coordinate coordinate, startCoordinate;
    private final FormationPos formationPos;
    private final Formation formation;

    public FleetCombat(PdxScriptObject o) {
        this.coordinate = o.getObject("coordinate").getAs(Coordinate::new);
        this.formationPos = o.getObject("formation_pos").getAs(FormationPos::new);
        this.formation = o.getObject("formation").getAs(Formation::new);
        this.startCoordinate = o.getObject("start_coordinate").getAs(Coordinate::new);
        this.startDate = o.getDate("start_date");
    }

    public FleetCombat(Date startDate, Coordinate coordinate, Coordinate startCoordinate, FormationPos formationPos, Formation formation) {
        this.startDate = startDate;
        this.coordinate = coordinate;
        this.startCoordinate = startCoordinate;
        this.formationPos = formationPos;
        this.formation = formation;
    }

    public Date getStartDate() {
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
}
