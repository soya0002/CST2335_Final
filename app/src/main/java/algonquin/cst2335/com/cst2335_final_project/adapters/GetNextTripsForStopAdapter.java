package algonquin.cst2335.com.cst2335_final_project.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import algonquin.cst2335.com.cst2335_final_project.R;
import algonquin.cst2335.com.cst2335_final_project.transferobject.Trip;

/**
 * Created by badal on 2018-04-17.
 */

public class GetNextTripsForStopAdapter extends ArrayAdapter<Trip> {
    private ArrayList<Trip> arrayList;
    private Activity activity;
    public GetNextTripsForStopAdapter(@NonNull Activity activity, ArrayList<Trip> arrayList) {
        super(activity, 0);
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Nullable
    @Override
    public Trip getItem(int position) {
        return arrayList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View result = inflater.inflate(R.layout.row_next_trips_for_stop,null);

        TextView tvTripDestination = result.findViewById(R.id.tvTripDestination);
        TextView tvLatLong = result.findViewById(R.id.tvLatLong);
        TextView tvGpsSpeed = result.findViewById(R.id.tvGpsSpeed);
        TextView tvTripStartTime = result.findViewById(R.id.tvTripStartTime);
        TextView tvAdjustedScheduleTime = result.findViewById(R.id.tvAdjustedScheduleTime);

        Trip route = arrayList.get(position);
        tvTripDestination.setText("Destination : " + route.getTripDestination());
        String gps = ((route.getGpsSpped()==null)? "Not Available" : route.getGpsSpped());
        tvLatLong.setText(( "Latitude : " + route.getLatitude() + " Longitude : " + route.getLongitude()));
        try {
            tvGpsSpeed.setText("Gps : " + gps);
        }catch (Exception e){

        }
        tvTripStartTime.setText("Trip Start Time : " + route.getTripStartTime());
        tvAdjustedScheduleTime.setText("Adjusted Schedule Time : " +route.getAdjustedScheduleTime());

        return result;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}