package levin.ru.atlas.DButility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        // конструктор суперкласса
        super(context, "Game", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("SQL tag", "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table Game ("
                + "level integer,"
                + "points integer" + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}