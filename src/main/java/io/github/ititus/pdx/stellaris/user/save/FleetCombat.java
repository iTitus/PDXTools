package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.PdxScriptObject;

import java.time.LocalDate;

public class FleetCombat {

    public final Coordinate coordinate;
    public final FormationPos formationPos;
    public final Formation formation;
    public final Coordinate startCoordinate;
    public final LocalDate startDate;

    public FleetCombat(PdxScriptObject o) {
        this.coordinate = o.getObjectAs("coordinate", Coordinate::new);
        this.formationPos = o.getObjectAs("formation_pos", FormationPos::new);
        this.formation = o.getObjectAs("formation", Formation::new);
        this.startCoordinate = o.getObjectAs("start_coordinate", Coordinate::new);
        this.startDate = o.getDate("start_date");
    }
}
