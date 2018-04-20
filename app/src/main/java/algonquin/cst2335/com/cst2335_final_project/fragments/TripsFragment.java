package algonquin.cst2335.com.cst2335_final_project.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import algonquin.cst2335.com.cst2335_final_project.R;
import algonquin.cst2335.com.cst2335_final_project.adapters.GetNextTripsForStopAdapter;
import algonquin.cst2335.com.cst2335_final_project.async.GetNextTripsForStopPostAsync;
import algonquin.cst2335.com.cst2335_final_project.listener.DataListener;
import algonquin.cst2335.com.cst2335_final_project.transferobject.RouteDirection;
import algonquin.cst2335.com.cst2335_final_project.transferobject.Trip;

/**
 * Created by badal on 2018-04-18.
 */

public class TripsFragment extends Fragment{

    private ListView lvOcTranspoTrips;
    private GetNextTripsForStopAdapter getNextTripsForStopAdapter;
    private ArrayList<Trip> tripArrayList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trips,container,false);
        lvOcTranspoTrips = view.findViewById(R.id.lvOcTranspoTrips);
        tripArrayList = new ArrayList<>();

        Bundle bundle = getArguments();
        String stopNo = bundle.getString("stopNo");
        String routeNo = bundle.getString("routeNo");

        getNextTripsForStopAdapter = new GetNextTripsForStopAdapter(getActivity(),tripArrayList);
        lvOcTranspoTrips.setAdapter(getNextTripsForStopAdapter);
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("loading");
        pd.show();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new GetNextTripsForStopPostAsync(stopNo, routeNo, new DataListener<RouteDirection>() {
            @Override
            public void onDataReceived(ArrayList<RouteDirection> data) {
                pd.hide();
            }

            @Override
            public void onDataReceived(RouteDirection data) {
                Log.e("Trips",data.getTrips().size()+"");
                tripArrayList.clear();
                for(int i=0;i<data.getTrips().size();i++){
                    tripArrayList.add(data.getTrips().get(i));
                }
                getNextTripsForStopAdapter.notifyDataSetChanged();
                pd.hide();
            }

            @Override
            public void onError(String message) {
                pd.hide();
            }
        }).execute();
        return view;
    }
}
