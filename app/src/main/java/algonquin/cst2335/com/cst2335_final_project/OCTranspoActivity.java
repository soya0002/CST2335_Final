package algonquin.cst2335.com.cst2335_final_project;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import algonquin.cst2335.com.cst2335_final_project.adapters.GetRouteSummaryForStopAdapter;
import algonquin.cst2335.com.cst2335_final_project.async.GetRouteSummaryForStopPostAsync;
import algonquin.cst2335.com.cst2335_final_project.database.FinalDatabaseHelper;
import algonquin.cst2335.com.cst2335_final_project.listener.DataListener;
import algonquin.cst2335.com.cst2335_final_project.transferobject.Route;

import static algonquin.cst2335.com.cst2335_final_project.database.FinalDatabaseHelper.KEY_STOP_DESCRIPTION;
import static algonquin.cst2335.com.cst2335_final_project.database.FinalDatabaseHelper.KEY_STOP_NO;
import static algonquin.cst2335.com.cst2335_final_project.database.FinalDatabaseHelper.STOP_TABLE_NAME;

public class OCTranspoActivity extends AppCompatActivity {
    private ListView lvOcTranspo;
    private GetRouteSummaryForStopAdapter getRouteSummaryForStopAdapter;
    private ArrayList<Route> routeArrayList;
    private EditText edtSearchStop;
    private Button btnSearchStop;
    private TextView tvStopNo,tvStopDescription;
    private FinalDatabaseHelper finalDatabaseHelper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_octranspo);

        lvOcTranspo = (ListView) findViewById(R.id.lvOcTranspo);
        edtSearchStop = (EditText) findViewById(R.id.edtSearchStop);
        btnSearchStop = (Button) findViewById(R.id.btnSearchStop);
        tvStopNo = (TextView) findViewById(R.id.tvStopNo);
        tvStopDescription = (TextView) findViewById(R.id.tvStopDescription);

        routeArrayList = new ArrayList<>();
        getRouteSummaryForStopAdapter = new GetRouteSummaryForStopAdapter(this,routeArrayList);
        lvOcTranspo.setAdapter(getRouteSummaryForStopAdapter);
        finalDatabaseHelper = new FinalDatabaseHelper(this);
        db = finalDatabaseHelper.getWritableDatabase();
        lvOcTranspo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Route currentRoute = routeArrayList.get(i);


                Intent intent = new Intent(OCTranspoActivity.this,OCTranspoDetailActivity.class);
                intent.putExtra("stopNo", Route.stop.getStopNo());
                intent.putExtra("routeNo", currentRoute.getRouteNo());
                Log.e("Stop and Route " , Route.stop.getStopNo() + " " + currentRoute.getRouteNo());
                startActivity(intent);
            }
        });

        btnSearchStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtSearchStop.getText().length() > 0) {
                    ContentValues values = new ContentValues();
                    values.put(KEY_STOP_NO,edtSearchStop.getText().toString());
                    values.put(KEY_STOP_DESCRIPTION,edtSearchStop.getText().toString() + " Searched");
                    db.insert(STOP_TABLE_NAME,null,values);
                    final ProgressDialog pd = new ProgressDialog(OCTranspoActivity.this);
                    pd.setMessage("loading");
                    pd.show();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    new GetRouteSummaryForStopPostAsync(edtSearchStop.getText().toString(),new DataListener<Route>() {
                        @Override
                        public void onDataReceived(ArrayList<Route> list) {

                            tvStopNo.setText(Route.stop.getStopNo());
                            tvStopDescription.setText(Route.stop.getStopDescription());

                            routeArrayList.clear();
                            for (Route route : list) {
                                routeArrayList.add(route);
                            }
                            getRouteSummaryForStopAdapter.notifyDataSetChanged();
                            pd.hide();
                        }

                        @Override
                        public void onDataReceived(Route data) {
                            pd.hide();
                        }

                        @Override
                        public void onError(String message) {
                            Log.e("Message ", message);
                            pd.hide();
                        }
                    }).execute();
                }
            }
        });


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
            startActivity(new Intent(OCTranspoActivity.this, HistoryActivity.class));

        }
        else if(item.getItemId() == R.id.ch_Help){
            Snackbar.make(lvOcTranspo, " Search stops based on stop no", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
