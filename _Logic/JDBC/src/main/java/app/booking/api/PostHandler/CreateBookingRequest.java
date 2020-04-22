package app.booking.api.PostHandler;

import lombok.Value;

@Value
class CreateBookingRequest {
    String pbookingid;
    String pdate;
    String pstarttime;
    String pendtime;
    String pcityid;
    String pcenterid;
    String pcourtid;
    String pplayerid;

}
