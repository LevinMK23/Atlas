package levin.ru.atlas;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RecyclerViewActivity extends Activity {

    static DbHelper db;
    static SQLiteDatabase base;
    HashMap<Integer, DbRecord> levels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        Record.cnt = 1;
        List<Record> records = new ArrayList<>();
        levels = new HashMap<>();
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Game", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS levels (level INTEGER, points INTEGER)");
        //db.execSQL("INSERT INTO levels(level, points) VALUES(1, 10);");
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
        db.close();
        base.close();
    }

    private void populateRecords(List<Record> records){
        for (int i = 0; i<10; i++){
            Record record = new Record();
            record.setName("Item №" + i);
            record.setType(Record.Type.values()[i%3]);
            records.add(record);
        }
    }

}
