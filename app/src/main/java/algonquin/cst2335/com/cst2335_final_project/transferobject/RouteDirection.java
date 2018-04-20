package algonquin.cst2335.com.cst2335_final_project.transferobject;

import java.util.ArrayList;

/**
 * Created by badal on 2018-04-17.
 */

public class RouteDirection {
    private String routeNo,routeLabel,direction;
    private ArrayList<Trip> trips;

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public String getRouteLabel() {
        return routeLabel;
    }

    public void setRouteLabel(String routeLabel) {
        this.routeLabel = routeLabel;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(ArrayList<Trip> trips) {
        this.trips = trips;
    }
}
