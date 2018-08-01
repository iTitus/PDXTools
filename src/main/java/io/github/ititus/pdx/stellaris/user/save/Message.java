package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptList;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.impl.factory.Lists;

import java.util.Date;

public class Message {

    private final boolean gameText;
    private final int receiver, notification;
    private final String type, localization, technology;
    private final Date end, date;
    private final ImmutableList<VariablePair> variables;
    private final Coordinate coordinate;
    private final DiplomaticResponse diplomaticAction3rdParty, diplomaticResponse;

    public Message(IPdxScript s) {
        if (!(s instanceof PdxScriptObject)) {
            throw new IllegalArgumentException(String.valueOf(s));
        }
        PdxScriptObject o = (PdxScriptObject) s;

        this.type = o.getString("type");
        this.localization = o.getString("localization");
        PdxScriptList l = o.getList("variables");
        this.variables = l != null ? l.getAsList(VariablePair::new) : Lists.immutable.empty();
        this.receiver = o.getInt("receiver");
        this.technology = o.getString("technology");
        this.coordinate = o.getObject("coordinate").getAs(Coordinate::new);
        this.end = o.getDate("end");
        this.date = o.getDate("date");
        this.notification = o.getInt("notification");
        this.gameText = o.getBoolean("game_text");
        PdxScriptObject o1 = o.getObject("diplomatic_action_3rd_party");
        this.diplomaticAction3rdParty = o1 != null ? o1.getAs(DiplomaticResponse::new) : null;
        o1 = o.getObject("diplomatic_response");
        this.diplomaticResponse = o1 != null ? o1.getAs(DiplomaticResponse::new) : null;
        Object o2 = o.getValue("message_type");
        if (o2 != null) {
            throw new RuntimeException("Unexpected value '" + o2 + "' for message_type");
        }
    }

    public Message(boolean gameText, int receiver, int notification, String type, String localization, String technology, Date end, Date date, ImmutableList<VariablePair> variables, Coordinate coordinate, DiplomaticResponse diplomaticAction3rdParty, DiplomaticResponse diplomaticResponse) {
        this.gameText = gameText;
        this.receiver = receiver;
        this.notification = notification;
        this.type = type;
        this.localization = localization;
        this.technology = technology;
        this.end = end;
        this.date = date;
        this.variables = variables;
        this.coordinate = coordinate;
        this.diplomaticAction3rdParty = diplomaticAction3rdParty;
        this.diplomaticResponse = diplomaticResponse;
    }

    public boolean isGameText() {
        return gameText;
    }

    public int getReceiver() {
        return receiver;
    }

    public int getNotification() {
        return notification;
    }

    public String getType() {
        return type;
    }

    public String getLocalization() {
        return localization;
    }

    public String getTechnology() {
        return technology;
    }

    public Date getEnd() {
        return end;
    }

    public Date getDate() {
        return date;
    }

    public ImmutableList<VariablePair> getVariables() {
        return variables;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public DiplomaticResponse getDiplomaticAction3rdParty() {
        return diplomaticAction3rdParty;
    }

    public DiplomaticResponse getDiplomaticResponse() {
        return diplomaticResponse;
    }
}
