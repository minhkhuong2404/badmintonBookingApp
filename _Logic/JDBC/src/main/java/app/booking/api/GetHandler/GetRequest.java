package app.booking.api.GetHandler;

import lombok.Value;

@Value
class StaffRequest {
    String cityid;
    String centerid;
}

@Value
class CityCenterRequest{
    String cityid;
}

@Value
class CityCenterCourtsRequest{
    String cityid;
    String centerid;
}

@Value
class CenterBookingRequest{
    String centerid;
}

@Value
class PlayerBookingRequest{
    String playerid;
}
