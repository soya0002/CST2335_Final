package algonquin.cst2335.com.cst2335_final_project.transferobject;

/**
 * Created by badal on 2018-04-17.
 */

public class Route {

    private String routeNo,directionId,direction,routeHeading;
    public static Stop stop = null;
    public Route(){
        if(stop==null)
            stop = new Stop();
    }
    public Route(String routeNo, String directionId, String direction, String routeHeading) {
        this.routeNo = routeNo;
        this.directionId = directionId;
        this.direction = direction;
        this.routeHeading = routeHeading;
        if(stop==null)
            stop = new Stop();
    }

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public String getDirectionId() {
        return directionId;
    }

    public void setDirectionId(String directionId) {
        this.directionId = directionId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getRouteHeading() {
        return routeHeading;
    }

    public void setRouteHeading(String routeHeading) {
        this.routeHeading = routeHeading;
    }

}
