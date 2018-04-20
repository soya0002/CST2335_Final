package algonquin.cst2335.com.cst2335_final_project;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;


import com.bumptech.glide.Glide;

/**
 * Created by shivankdesai on 2018-04-19.
 */

public class MovieFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie,container,false);

        Bundle bundle = getArguments();

        ImageView image= view.findViewById(R.id.detailsImage);
        Glide.with(this).load(bundle.getString("Url")).into(image);
        String actors= bundle.getString("Actors");
        actors.replaceAll(",","\n");
        EditText actTxt= view.findViewById(R.id.actorBox);
        actTxt.setText(actors);
        EditText genreTxt= view.findViewById(R.id.genreBox);
        genreTxt.setText(bundle.getString("Genre"));
        EditText descTxt= view.findViewById(R.id.desBox);
        descTxt.setText(bundle.getString("Desc"));
        RatingBar ratingBar= view.findViewById(R.id.ratingBar);
        ratingBar.setRating(Float.valueOf(bundle.getString("Rating")));
        EditText lengthTxt= view.findViewById(R.id.lengthBox);
        lengthTxt.setText(bundle.getString("Length"));

        Button deleteBtn = (Button) view.findViewById(R.id.btnDelete);
        Button updateBtn = (Button)view.findViewById(R.id.btnUpdate);
        Button editBtn =view.findViewById(R.id.btnEdit);
        deleteBtn.setOnClickListener((View v)->{
            MovieInfoActivity.movieList.remove(bundle.getInt("Id"));
            MovieInfoActivity.movieAdapter.notifyDataSetChanged();
            if(!bundle.getBoolean("IsTablet")) {
                getActivity().setResult(10);
                getActivity().finish();
            }
        });

        editBtn.setOnClickListener((View v)->{
            actTxt.setEnabled(true);
            genreTxt.setEnabled(true);
            descTxt.setEnabled(true);
            ratingBar.setEnabled(true);
            lengthTxt.setEnabled(true);
        });
        updateBtn.setOnClickListener((View v)->{
            int i= bundle.getInt("Id");
            Movie update= MovieInfoActivity.movieList.get(i);
            MovieInfoActivity.movieList.remove(i);
            update.setActors(actTxt.getText().toString());
            update.setGenre(genreTxt.getText().toString());
            update.setDescreption(descTxt.getText().toString());
            update.setRating(String.valueOf(ratingBar.getRating()));
            update.setLength(lengthTxt.getText().toString());
            MovieInfoActivity.movieList.add(update);
            MovieInfoActivity.movieAdapter.notifyDataSetChanged();
            if(!bundle.getBoolean("IsTablet")) {
                getActivity().setResult(11);
                getActivity().finish();
            }
        });
        return view;
    }
}
