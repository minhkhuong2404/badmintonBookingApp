package app.booking.api.PostHandler;

import lombok.Value;

@Value
class BookingCreateRequest {
    String pdate;
    String pstarttime;
    String pendtime;
    String pcityid;
    String pcenterid;
    String pcourtid;
    String pplayerid;
    String cardid;
}
@Value
class BookingCancelRequest {
    int id;
    String playerid;
}