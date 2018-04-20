package algonquin.cst2335.com.cst2335_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import algonquin.cst2335.com.cst2335_final_project.fragments.TripsFragment;

public class OCTranspoDetailActivity extends AppCompatActivity {

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    FrameLayout fm_trips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_octranspo_detail);
        fm_trips = (FrameLayout) findViewById(R.id.fm_trips);
        Intent intent = getIntent();
        String stopNo = intent.getStringExtra("stopNo");
        String routeNo = intent.getStringExtra("routeNo");

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        TripsFragment tripsFragment = new TripsFragment();

        Bundle bundle = new Bundle();
        bundle.putString("stopNo",stopNo);
        bundle.putString("routeNo",routeNo);

        tripsFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fm_trips,tripsFragment);
        fragmentTransaction.commit();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.ch_History){
            startActivity(new Intent(OCTranspoDetailActivity.this, HistoryActivity.class));

        }
        else if(item.getItemId() == R.id.ch_Help){
            Snackbar.make(fm_trips, " Shows results based on stop no", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
