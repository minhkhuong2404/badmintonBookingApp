package app.booking.db;

import java.sql.Timestamp;

public class Card {
    private int id;
    private String playerId;
    private int remainBooking;
    private Timestamp time_bought;
    private Timestamp expire_date;

    public Card(int id, String playerId, int remainBooking, Timestamp time_bought, Timestamp expire_date) {
        this.id = id;
        this.playerId = playerId;
        this.remainBooking = remainBooking;
        this.time_bought = time_bought;
        this.expire_date = expire_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getRemainBooking() {
        return remainBooking;
    }

    public void setRemainBooking(int remainBooking) {
        this.remainBooking = remainBooking;
    }

    public Timestamp getTime_bought() {
        return time_bought;
    }

    public void setTime_bought(Timestamp time_bought) {
        this.time_bought = time_bought;
    }

    public Timestamp getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(Timestamp expire_date) {
        this.expire_date = expire_date;
    }
}
