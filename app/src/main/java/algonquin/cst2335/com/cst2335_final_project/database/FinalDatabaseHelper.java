package algonquin.cst2335.com.cst2335_final_project.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by badal on 2018-04-17.
 */

public class FinalDatabaseHelper extends SQLiteOpenHelper{

    static String DATABASE_NAME = "final_project.db";
    static int VERSION_NUM = 1;
    public static final String KEY_STOP_NO = "stop_no", KEY_STOP_DESCRIPTION = "stop_desc";
    public static final String STOP_TABLE_NAME = "tbl_stop";

    public FinalDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("FinalDatabaseHelper", "Calling onCreate");
        db.execSQL("create table " + STOP_TABLE_NAME + " ( " + KEY_STOP_NO + " integer primary key , " + KEY_STOP_DESCRIPTION + " text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i("FinalDatabaseHelper", "Calling onUpgrade, oldVersion=" + i + " newVersion=" + i1);
        db.execSQL("drop table if exists " + STOP_TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int i, int i1) {
        Log.i("FinalDatabaseHelper", "Calling onDowngrade, oldVersion=" + i + " newVersion=" + i1);
        db.execSQL("drop table if exists " + STOP_TABLE_NAME);
        onCreate(db);
    }

}
