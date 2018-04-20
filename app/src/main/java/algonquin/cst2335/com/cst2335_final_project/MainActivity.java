package algonquin.cst2335.com.cst2335_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private Button btnShivank;
    private Button btnBadal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBadal = (Button) findViewById(R.id.btnBadal);
        btnBadal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,OCTranspoActivity.class));
            }
        });

        btnShivank = (Button) findViewById(R.id.btnShivank);
        btnShivank.setOnClickListener(e -> {
          Intent intent = new Intent(getApplicationContext(),MovieInfoActivity.class);
          startActivity(intent);

      });
    }

}
