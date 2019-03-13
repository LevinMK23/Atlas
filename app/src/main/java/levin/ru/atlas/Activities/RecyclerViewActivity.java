package levin.ru.atlas.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import levin.ru.atlas.DButility.*;

import levin.ru.atlas.R;
import levin.ru.atlas.Record;
import levin.ru.atlas.RecyclerViewAdapter;


public class RecyclerViewActivity extends Activity {

    public static DbHelper db;
    public static SQLiteDatabase base;
    public HashMap<Integer, DbRecord> levels;
    public static int recordCounter = 11;

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        Record.cnt = 1;
        List<Record> records = new ArrayList<>();
        levels = new HashMap<>();
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Game", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS levels (level INTEGER, points INTEGER)");
        @SuppressLint("Recycle")
        Cursor c = db.rawQuery("SELECT * FROM levels;", null);
        if(c.moveToFirst()) {
            do {
                levels.put(c.getInt(c.getColumnIndex("level")), new DbRecord(c.getInt(c.getColumnIndex("level")),
                        c.getInt(c.getColumnIndex("points"))));
            } while (c.moveToNext());
        }
        db.close();
        //здесь создаем уровни

        for (DbRecord record : levels.values()) {
            Log.d("LIST", record.level + " " + record.points);
        }

        populateRecords(records);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, records);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //db.close();
        //base.close();
    }

    private void populateRecords(List<Record> records){
        for (int i = 1; i < recordCounter; i++){
            Record record;
            if(levels.containsKey(i)){
                record = new Record(levels.get(i).points);
            }
            else{
                record = new Record(0);
            }
            record.setName("Уровень " + i);
            records.add(record);
        }
    }

    public void goToStartActivity(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
