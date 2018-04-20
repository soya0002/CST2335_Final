package algonquin.cst2335.com.cst2335_final_project;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import algonquin.cst2335.com.cst2335_final_project.database.FinalDatabaseHelper;

import static algonquin.cst2335.com.cst2335_final_project.database.FinalDatabaseHelper.KEY_STOP_DESCRIPTION;
import static algonquin.cst2335.com.cst2335_final_project.database.FinalDatabaseHelper.KEY_STOP_NO;
import static algonquin.cst2335.com.cst2335_final_project.database.FinalDatabaseHelper.STOP_TABLE_NAME;

public class HistoryActivity extends AppCompatActivity {

    FinalDatabaseHelper finalDatabaseHelper;
    SQLiteDatabase db;
    ArrayList<String> stringArrayList;
    ArrayAdapter<String> stringArrayAdapter;
    ListView lvHistory;
    Button btnAdd,btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        lvHistory = (ListView) findViewById(R.id.lvHistory);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        stringArrayList = new ArrayList<>();
        stringArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stringArrayList);
        finalDatabaseHelper = new FinalDatabaseHelper(this);
        lvHistory.setAdapter(stringArrayAdapter);
        db = finalDatabaseHelper.getWritableDatabase();
        refresh();
        final Dialog dialogTextAdd = new Dialog(this);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogTextAdd.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogTextAdd.setCancelable(false);
                dialogTextAdd.setContentView(R.layout.dialog_add);
                final EditText edtMessage = (EditText) dialogTextAdd.findViewById(R.id.edtMessage);

                Button btnOk = (Button) dialogTextAdd.findViewById(R.id.btnOK);
                Button btnCancel = (Button) dialogTextAdd.findViewById(R.id.btnCancel);


                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogTextAdd.dismiss();
                    }
                });

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ContentValues values = new ContentValues();
                        values.put(KEY_STOP_NO,edtMessage.getText().toString());
                        values.put(KEY_STOP_DESCRIPTION,edtMessage.getText().toString() + " Searched");
                        db.insert(STOP_TABLE_NAME,null,values);
                        Snackbar.make(lvHistory, edtMessage.getText().toString() + " inserted!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        refresh();
                    }
                });

                dialogTextAdd.show();
            }
        });
        final Dialog dialogText = new Dialog(this);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogText.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogText.setCancelable(false);
                dialogText.setContentView(R.layout.dialog_delete);
                final EditText edtMessage = (EditText) dialogText.findViewById(R.id.edtMessage);

                Button btnOk = (Button) dialogText.findViewById(R.id.btnOK);
                Button btnCancel = (Button) dialogText.findViewById(R.id.btnCancel);


                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogText.dismiss();
                    }
                });

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.execSQL("delete from " + STOP_TABLE_NAME + " where " + KEY_STOP_NO + "=" + edtMessage.getText().toString());
                        Snackbar.make(lvHistory, edtMessage.getText().toString() + " deleted!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        refresh();
                    }
                });

                dialogText.show();
            }
        });

    }
    public void refresh(){
        stringArrayList.clear();
        final Cursor cursor = db.rawQuery("select * from " + STOP_TABLE_NAME,null);
        if(cursor.getCount() > 0){
            //cursor.moveToFirst();
            if(cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    int index = cursor.getColumnIndex(KEY_STOP_NO);
                    String msg = cursor.getString(index);
                    stringArrayList.add(msg);
                    cursor.moveToNext();
                }
            }
        }
        stringArrayAdapter.notifyDataSetChanged();
    }
}
