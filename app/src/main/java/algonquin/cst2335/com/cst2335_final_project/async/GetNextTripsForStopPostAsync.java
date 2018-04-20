package algonquin.cst2335.com.cst2335_final_project.async;

import android.os.AsyncTask;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import algonquin.cst2335.com.cst2335_final_project.listener.DataListener;
import algonquin.cst2335.com.cst2335_final_project.transferobject.RouteDirection;
import algonquin.cst2335.com.cst2335_final_project.transferobject.Trip;

/**
 * Created by badal on 2018-04-18.
 */

public class GetNextTripsForStopPostAsync extends AsyncTask<String,Integer,RouteDirection> {

    private DataListener dataListener;
    private ArrayList<Trip> routeArrayList;
    private RouteDirection routeDirection;
    private String searchStop,searchRoute;
    private Trip trip;
    public GetNextTripsForStopPostAsync(String searchStop,String searchRoute, DataListener dataListener){
        this.searchStop = searchStop;
        this.searchRoute = searchRoute;
        this.dataListener = dataListener;
        routeArrayList = new ArrayList<>();
    }

    @Override
    protected RouteDirection doInBackground(String... strings) {
        try {
            URL url = new URL("https://api.octranspo1.com/v1.2/GetNextTripsForStop");
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("appID", "223eb5c3");
            params.put("apiKey", "ab27db5b435b8c8819ffb8095328e775");
            params.put("stopNo", searchStop);
            params.put("routeNo",searchRoute);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
            InputStream stream = conn.getInputStream();
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            parser.nextTag();
            parser.require(XmlPullParser.START_TAG, null, "soap:Envelope");
            boolean isRouteNotAdded = true;
            boolean isTripNotAdded = true;
            String tag="";
            while(parser.next() != XmlPullParser.END_DOCUMENT){
                if(parser.getName()!=null && parser.getEventType() != XmlPullParser.END_TAG){
                    tag = parser.getName();
                    if(tag.equalsIgnoreCase("Trip")){
                        if (isTripNotAdded) {
                            trip = new Trip();
                            isTripNotAdded = false;
                        }
                    }
                }
                if(parser.getEventType() == XmlPullParser.TEXT){
                    if(isRouteNotAdded){
                        routeDirection = new RouteDirection();
                        isRouteNotAdded = false;
                    }
                    if(tag.equalsIgnoreCase("RouteNo"))
                        routeDirection.setRouteNo(parser.getText());
                    else if(tag.equalsIgnoreCase("RouteLabel"))
                        routeDirection.setRouteLabel(parser.getText());
                    else if(tag.equalsIgnoreCase("Direction"))
                        routeDirection.setDirection(parser.getText());
                    else if(tag.equalsIgnoreCase("TripDestination"))
                        trip.setTripDestination(parser.getText());
                    else if(tag.equalsIgnoreCase("TripStartTime"))
                        trip.setTripStartTime(parser.getText());
                    else if(tag.equalsIgnoreCase("AdjustedScheduleTime"))
                        trip.setAdjustedScheduleTime(parser.getText());
                    else if(tag.equalsIgnoreCase("Latitude"))
                        trip.setLatitude(parser.getText());
                    else if(tag.equalsIgnoreCase("Longitude"))
                        trip.setLongitude(parser.getText());
                    else if(tag.equalsIgnoreCase("GPSSpeed"))
                        trip.setGpsSpped(parser.getText());
                }
                if(parser.getName()!=null && parser.getEventType() == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("Trip")){
                    isTripNotAdded = true;
                    routeArrayList.add(trip);
                }
            }

        }catch (Exception e){
            dataListener.onError(e.getMessage());
        }
        routeDirection.setTrips(routeArrayList);
        return routeDirection;

    }

    @Override
    protected void onPostExecute(RouteDirection routeDirection) {
        dataListener.onDataReceived(routeDirection);
    }
}
