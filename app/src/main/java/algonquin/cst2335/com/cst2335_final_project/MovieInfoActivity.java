package algonquin.cst2335.com.cst2335_final_project;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MovieInfoActivity extends AppCompatActivity {
     public static ArrayList<Movie> movieList ;
     private ListView listView;
     private ProgressBar progressBar;
     static MovieAdapter movieAdapter ;
     boolean frameExist;
     //public static MovieDatabaseHelper obj;
     public static SQLiteDatabase dataBase;
     public static Cursor cursor;
     public final static String ACTIVITY_NAME= "MovieInfoActivity";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        setContentView(R.layout.activity_movie_info);
        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressBar);
        movieList= new ArrayList<>();
        //obj = new MovieDatabaseHelper(this);
        //dataBase = obj.getWritableDatabase();
        //cursor = dataBase.rawQuery("select * from "+obj.TABLE_NAME,null);
        new MovieQuery().execute();
//        if(cursor.getCount() >movieList.size()){
//            movieList.clear();
//            cursor.moveToFirst();
//            while(!cursor.isAfterLast() ) {
//                Log.i(ACTIVITY_NAME, "SQL MESSAGE:"
//                        +cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_TITLE)) );
//                Log.i(ACTIVITY_NAME,"COLUMN NAME: "+ cursor.getColumnName(cursor.getColumnIndex(MovieDatabaseHelper.KEY_TITLE)));
//
//                Movie movieDb= new Movie();
//                movieDb.setTitle(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_TITLE)));
//                movieDb.setLength(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_LENGTH)));
//                movieDb.setActors(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_ACTOR)));
//                movieDb.setRating(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_RATING)));
//                movieDb.setUrl(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_URL)));
//                movieDb.setDescreption(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_DESCRIPTION)));
//                movieDb.setGenre(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_GENRE)));
//                cursor.moveToNext();
//            }
//        }





        movieAdapter= new MovieAdapter(this);
        listView.setAdapter(movieAdapter);

        FrameLayout frameLayout= findViewById(R.id.landFrame);
        frameExist = (frameLayout!=null);
        Button createBtn = findViewById(R.id.btnCreate);
        createBtn.setOnClickListener((View v)->
        {
            Intent intent = new Intent(getApplicationContext(),MovieForm.class);
            startActivity(intent);
        });





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie movieBundle=movieList.get(i);
                Bundle bundle= new Bundle();
                bundle.putString("Rating",movieBundle.getRating());
                bundle.putString("Length",movieBundle.getLength());
                bundle.putString("Actors",movieBundle.getActors());
                bundle.putString("Genre",movieBundle.getGenre());
                bundle.putString("Desc",movieBundle.getDescreption());
                bundle.putString("Url",movieBundle.getUrl());
                bundle.putInt("Id",i);
                bundle.putBoolean("IsTablet",frameExist);

                if(frameExist){

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    MovieFragment mf = new MovieFragment();
                    mf.setArguments(bundle);
                    ft.replace(R.id.landFrame,mf);
                    ft.commit();

                }else{
                    Intent intent = new Intent(getApplicationContext(),MovieDetails.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,1);
                }


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==10){

            Snackbar.make(listView, "Data has been deleted!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();


        }
        else if(resultCode == 11){
            Snackbar.make(listView, "Data has been updated!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class MovieAdapter extends ArrayAdapter<Movie>{
        public MovieAdapter( Context context) {
            super(context, 0);
        }

        @Override
        public int getCount() {
            return movieList.size();
        }


        @Override
        public Movie getItem(int position) {
            return movieList.get(position);
        }

        @Override
        public int getPosition(Movie item) {
            return movieList.indexOf(item);
        }


        @Override
        public View getView(int position, View convertView,  ViewGroup parent) {
            LayoutInflater inflater = MovieInfoActivity.this.getLayoutInflater();
            View view =  inflater.inflate(R.layout.row_layout,null);
            TextView textView = (TextView) view.findViewById(R.id.textView);
            Movie movie = movieList.get(position) ;
            textView.setText(movie.getTitle());
            ImageView imageView = view.findViewById(R.id.imageView);
            Glide.with(getApplicationContext()).load(movie.getUrl()).into(imageView) ;
            return view;
        }
//        @Override
//        public long getItemId(int position) {
//            cursor = dataBase.rawQuery("select * from "+obj.TABLE_NAME,null);
//            cursor.moveToPosition(position);
//            Long id = cursor.getLong(cursor.getColumnIndex(MovieDatabaseHelper.KEY_ID));
//
//            return id;
//
//        }
    }

    public class MovieQuery extends AsyncTask<String,Integer,String>{
        HttpURLConnection conn = null;
        InputStream inputStream =null;
        XmlPullParser parser = null;
        protected String urlString = " http://torunski.ca/CST2335/MovieInfo.xml";
        @Override
        protected String doInBackground(String... strings) {
            try {
                establishConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            movieAdapter.notifyDataSetChanged();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        protected void establishConnection ()throws Exception{

                URL url = new URL(urlString);
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(10000) ;
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                Log.d("Temp",conn.getResponseMessage());
                inputStream = conn.getInputStream();
                XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                xmlPullParserFactory.setNamespaceAware(false);
                parser = xmlPullParserFactory.newPullParser();
                parser.setInput(inputStream, "UTF-8");
                readXml();
        }

        protected void readXml()throws Exception{
            
              int eventType = parser.getEventType();
              Movie movie =null;
              while(eventType != XmlPullParser.END_DOCUMENT){
                  String atrName = null;

                  switch(eventType){
                      case XmlPullParser.START_TAG:
                          atrName = parser.getName();
                          if(atrName.equals("Movie")){
                            movie= new Movie();


                          } else if (movie!= null){
                             if(atrName.equals("Title"))
                                 movie.setTitle(parser.nextText());
                             else if(atrName.equals("Actors"))
                                movie.setActors(parser.nextText());
                             else if(atrName.equals("Length"))
                                movie.setLength(parser.nextText());
                             else if(atrName.equals("Description"))
                                movie.setDescreption(parser.nextText());
                             else if(atrName.equals("Rating"))
                                movie.setRating(parser.nextText());
                             else if(atrName.equals("Genre"))
                                movie.setGenre(parser.nextText());
                             else if(atrName.equals("URL")) {
                                 movie.setUrl(parser.getAttributeValue(null, "value"));
                                  movieList.add(movie);
                             }
                          }
                          break;
                  }


                  eventType= parser.next();
              }


        }

    }


}
