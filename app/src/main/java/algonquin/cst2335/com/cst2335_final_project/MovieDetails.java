package algonquin.cst2335.com.cst2335_final_project;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        MovieFragment mf= new MovieFragment();
        mf.setArguments(bundle);
        ft.add(R.id.emptyFrame,mf);
        ft.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu_movie, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_about){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View view = inflater.inflate(R.layout.cus_layout, null);

            builder1.setView(view)
                    // Add action buttons
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog dialog2 = builder1.create();
            dialog2.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
