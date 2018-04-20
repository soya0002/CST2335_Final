package algonquin.cst2335.com.cst2335_final_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shivankdesai on 2018-04-20.
 */

public class MovieDatabaseHelper extends SQLiteOpenHelper {




    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ACTOR = "actor";
    public static final String KEY_GENRE = "genre";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_LENGTH = "length";
    public static final String KEY_RATING = "rating";
    public static final String KEY_URL= "url";

    public static final String DATABASE_NAME = "movie.db";
    public static final int VERSION_NUM = 1;
    public static final String TABLE_NAME = "Movie_Table";
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " + KEY_ID
            + " integer primary key autoincrement, " + KEY_TITLE+" text not null,"
            +KEY_ACTOR+" text not null,"+KEY_GENRE+" text not null,"+KEY_DESCRIPTION+" text not null,"
            +KEY_LENGTH+" text not null,"+KEY_RATING+" text not null,"+KEY_URL
            + " text not null);";

    public MovieDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE);
        Log.i("ChatDatabaseHelper", "Calling onCreate");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i("ChatDatabaseHelper",
                "Calling onUpgrade, oldVersion=" + i + " newVersion=" + i1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override

    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        Log.i("ChatDatabaseHelper", "Calling onDowngrade, old Version=" + oldVer + " newVersion=" + newVer);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
