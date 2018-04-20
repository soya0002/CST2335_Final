package algonquin.cst2335.com.cst2335_final_project.transferobject;

/**
 * Created by badal on 2018-04-18.
 */

public class Trip {
    private String tripDestination,tripStartTime,adjustedScheduleTime,gpsSpped, latitude,longitude;

    public Trip(){

    }

    public String getTripDestination() {
        return tripDestination;
    }

    public void setTripDestination(String tripDestination) {
        this.tripDestination = tripDestination;
    }

    public String getTripStartTime() {
        return tripStartTime;
    }

    public void setTripStartTime(String tripStartTime) {
        this.tripStartTime = tripStartTime;
    }

    public String getAdjustedScheduleTime() {
        return adjustedScheduleTime;
    }

    public void setAdjustedScheduleTime(String adjustedScheduleTime) {
        this.adjustedScheduleTime = adjustedScheduleTime;
    }

    public String getGpsSpped() {
        return gpsSpped;
    }

    public void setGpsSpped(String gpsSpped) {
        this.gpsSpped = gpsSpped;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
