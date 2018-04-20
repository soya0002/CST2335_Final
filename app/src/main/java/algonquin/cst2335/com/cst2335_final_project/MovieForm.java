package algonquin.cst2335.com.cst2335_final_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

public class MovieForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_form);
        EditText titleTxt= findViewById(R.id.titleTxt);
        EditText imageUrlTxt= findViewById(R.id.imgUrlTxt);
        EditText actTxt= findViewById(R.id.actorBox);
        EditText genreTxt= findViewById(R.id.genreBox);
        EditText lengthTxt= findViewById(R.id.lengthBox);
        EditText desTxt= findViewById(R.id.desBox);
        RatingBar ratingBar= findViewById(R.id.ratingBar);

        Button button= findViewById(R.id.btnSubmit);
        button.setOnClickListener((View v)->{
            Movie m= new Movie();
            m.setTitle(titleTxt.getText().toString());
            m.setUrl(imageUrlTxt.getText().toString());
            m.setActors(actTxt.getText().toString());
            m.setGenre(genreTxt.getText().toString());
            m.setDescreption(desTxt.getText().toString());
            m.setRating(String.valueOf(ratingBar.getRating()));
            m.setLength(lengthTxt.getText().toString());
            MovieInfoActivity.movieList.add(m);
            MovieInfoActivity.movieAdapter.notifyDataSetChanged();
            finish();
        });
    }
}
