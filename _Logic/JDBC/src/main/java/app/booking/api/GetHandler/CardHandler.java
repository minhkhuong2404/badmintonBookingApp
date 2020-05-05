package app.booking.api.GetHandler;

import app.booking.api.Constants;
import app.booking.api.JsonConverter;
import app.booking.api.ResponseEntity;
import app.booking.api.StatusCode;
import app.booking.db.Booking;
import app.booking.db.Card;
import app.booking.db.SQLStatement;
import app.booking.errors.GlobalExceptionHandler;
import app.booking.slot.CitySlot;

import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardHandler extends GetHandler {
    public CardHandler(GlobalExceptionHandler exceptionHandler) {
        super(exceptionHandler);
    }

    @Override
    protected ResponseEntity doGet(InputStream is) throws Exception {
        Map<String, List<String>> params = this.getParameters();

        String PlayerId = params.get("id").get(0);
        Timestamp date = Timestamp.valueOf(params.get("date").get(0));
        // TODO: handle the case of missing/incorrect params

        // Get cards of the player
        ArrayList<Card> PlayerCard = SQLStatement.getPlayerCards(PlayerId);
        //Check for usable card
        for (Card c:PlayerCard
             ) {
            if (date.before(c.getExpire_date()))
                PlayerCard.remove(c);
            else if (c.getRemainBooking() == 0) PlayerCard.remove(c);
        }
        // Convert to json
        String response = JsonConverter.toJson(PlayerCard);

        return new ResponseEntity<>(response, getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }
}
