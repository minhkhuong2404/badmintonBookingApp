package app.booking.api.Handler;

import lombok.Value;

@Value
class CreateBookingRequest {
    String pbookingid;
    String ptimestamp;
    String pdate;
    String pstarttime;
    String pendtime;
    String pcityid;
    String pcenterid;
    String pcourtid;
    String pplayerid;

}
