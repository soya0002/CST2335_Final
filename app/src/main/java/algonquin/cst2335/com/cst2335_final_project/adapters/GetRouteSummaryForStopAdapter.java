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
import algonquin.cst2335.com.cst2335_final_project.transferobject.Route;

/**
 * Created by badal on 2018-04-17.
 */

public class GetRouteSummaryForStopAdapter extends ArrayAdapter<Route> {
    private ArrayList<Route> arrayList;
    private Activity activity;
    public GetRouteSummaryForStopAdapter(@NonNull Activity activity, ArrayList<Route> arrayList) {
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
    public Route getItem(int position) {
        return arrayList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View result = inflater.inflate(R.layout.row_route_summary_for_stop,null);
        TextView tvRouteNo = result.findViewById(R.id.tvRouteNo);
        TextView tvDirectionID = result.findViewById(R.id.tvDirectionID);
        TextView tvDirection = result.findViewById(R.id.tvDirection);
        TextView tvRouteHeading = result.findViewById(R.id.tvRouteHeading);

        Route route = arrayList.get(position);
        tvRouteNo.setText(route.getRouteNo());
        tvDirectionID.setText(route.getDirectionId());
        tvDirection.setText(route.getDirection());
        tvRouteHeading.setText(route.getRouteHeading());
        return result;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}