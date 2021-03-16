package io.github.ititus.pdx.stellaris.user.save;

import io.github.ititus.pdx.pdxscript.IPdxScript;
import io.github.ititus.pdx.pdxscript.PdxScriptObject;
import org.eclipse.collections.api.list.ImmutableList;

import java.time.LocalDate;

public class Message {

    public final String type;
    public final String localization;
    public final ImmutableList<Entry> variables;
    public final int receiver;
    public final int targetSystem;
    public final String technology;
    public final Coordinate coordinate;
    public final LocalDate end;
    public final LocalDate date;
    public final int notification;
    public final boolean gameText;
    public final DiplomaticResponse diplomaticAction3rdParty;
    public final DiplomaticResponse diplomaticResponse;
    public final String messageType;

    public Message(IPdxScript s) {
        PdxScriptObject o = s.expectObject();
        this.type = o.getString("type");
        this.localization = o.getString("localization");
        this.variables = o.getListAsEmptyOrList("variables", Entry::new);
        this.receiver = o.getInt("receiver");
        this.targetSystem = o.getInt("target_system", -1);
        this.technology = o.getString("technology", null);
        this.coordinate = o.getObjectAs("coordinate", Coordinate::new);
        this.end = o.getDate("end");
        this.date = o.getDate("date");
        this.notification = o.getInt("notification");
        this.gameText = o.getBoolean("game_text", false);
        this.diplomaticAction3rdParty = o.getObjectAsNullOr("diplomatic_action_3rd_party", DiplomaticResponse::new);
        this.diplomaticResponse = o.getObjectAsNullOr("diplomatic_response", DiplomaticResponse::new);
        this.messageType = o.getNullOrString("message_type");
    }
}
