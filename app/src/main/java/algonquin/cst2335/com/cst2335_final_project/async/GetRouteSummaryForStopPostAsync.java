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
import algonquin.cst2335.com.cst2335_final_project.transferobject.Route;

/**
 * Created by badal on 2018-04-17.
 */

public class GetRouteSummaryForStopPostAsync extends AsyncTask<String,Integer,ArrayList<Route>>{

    private DataListener dataListener;
    private ArrayList<Route> routeArrayList;
    private Route route;
    private String searchStop;
    public GetRouteSummaryForStopPostAsync(String searchStop,DataListener dataListener){
        this.searchStop = searchStop;
        this.dataListener = dataListener;
        routeArrayList = new ArrayList<>();
    }

    @Override
    protected ArrayList<Route> doInBackground(String... strings) {
        try {
            URL url = new URL("https://api.octranspo1.com/v1.2/GetRouteSummaryForStop");
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("appID", "223eb5c3");
            params.put("apiKey", "ab27db5b435b8c8819ffb8095328e775");
            params.put("stopNo", searchStop);

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
            String tag="";
            while(parser.next() != XmlPullParser.END_DOCUMENT){
                if(parser.getName()!=null && parser.getEventType() != XmlPullParser.END_TAG){
                    tag = parser.getName();
                }
                if(parser.getEventType() == XmlPullParser.TEXT){
                    if(isRouteNotAdded){
                        route = new Route();
                        isRouteNotAdded = false;
                    }
                    if(tag.equalsIgnoreCase("StopNo"))
                        Route.stop.setStopNo(parser.getText());
                    else if(tag.equalsIgnoreCase("StopDescription"))
                        Route.stop.setStopDescription(parser.getText());
                    else if(tag.equalsIgnoreCase("RouteNo"))
                        route.setRouteNo(parser.getText());
                    else if(tag.equalsIgnoreCase("DirectionID"))
                        route.setDirectionId(parser.getText());
                    else if(tag.equalsIgnoreCase("Direction"))
                        route.setDirection(parser.getText());
                    else if(tag.equalsIgnoreCase("RouteHeading")) {
                        route.setRouteHeading(parser.getText());
                        isRouteNotAdded = true;
                        routeArrayList.add(route);
                    }
                }
            }

        }catch (Exception e){
            dataListener.onError(e.getMessage());
        }
        return routeArrayList;

    }

    @Override
    protected void onPostExecute(ArrayList<Route> routeArrayList) {
        dataListener.onDataReceived(routeArrayList);
    }
}
