package levin.ru.atlas.DButility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import levin.ru.atlas.LevelConfig.Continent;

public class DbHelper extends SQLiteOpenHelper implements IDatabaseHandler{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Game";
    private static final String TABLE_LEVELS = "levels";
    private static final String TABLE_CONTINENTS = "continents";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_POINTS = "points";
    private static final String KEY_CONTINENT = "continent";
    private static final String CONTINENT_STATUS = "status";
    private static final String AFRICA = "'Африка'";
    private static final String EURASIA = "'Евразия'";
    private static final String NAMERICA = "'Северная Америка'";
    private static final String SAMERICA = "'Южная Америка'";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void init(){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DROP TABLE " + TABLE_LEVELS);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CONTINENTS + "("
                + KEY_CONTINENT + " TEXT," + CONTINENT_STATUS + " INTEGER"+ ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_LEVELS + "("
                + KEY_LEVEL + " INTEGER," + KEY_POINTS + " TEXT,"
                + KEY_CONTINENT + " TEXT" + ")");
        db.close();
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor = db1.rawQuery("SELECT * FROM " + TABLE_CONTINENTS, null);
        int cnt = cursor.getCount();
        cursor.close();
        db1.close();
        if(cnt < 4){
            db = getWritableDatabase();
            String query = "INSERT INTO " + TABLE_CONTINENTS +
                    " VALUES(" +
                    AFRICA + "," + 0 + "), (" + EURASIA + "," + 0 + "), ("
                    + NAMERICA + "," + 0 + "), (" + SAMERICA + "," + 0 + ");";
            db.execSQL(query);
        }
        db1 = getReadableDatabase();
        cursor = db1.rawQuery("SELECT * FROM " + TABLE_LEVELS, null);
        cnt = cursor.getCount();
        cursor.close();
        db1.close();
        if(cnt < 10){
            for (int i = 0; i < 10; i++) {
                addLevel(new DBLevel(i+1, -1, "Африка"));
            }
            for (int i = 0; i < 10; i++) {
                addLevel(new DBLevel(i+1, -1, "Евразия"));
            }
            for (int i = 0; i < 10; i++) {
                addLevel(new DBLevel(i+1, -1, "Северная Америка"));
            }
            for (int i = 0; i < 10; i++) {
                addLevel(new DBLevel(i+1, -1, "Южная Америка"));
            }
        }
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CONTINENTS + "("
                + KEY_CONTINENT + " TEXT," + CONTINENT_STATUS + " INTEGER"+ ")");
        String LEVELS = "CREATE TABLE IF NOT EXISTS " + TABLE_LEVELS + "("
                + KEY_LEVEL + " INTEGER," + KEY_POINTS + " TEXT,"
                + KEY_CONTINENT + " TEXT" + ")";
        db.execSQL(LEVELS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEVELS);
        onCreate(db);
    }

    public void updateContinent(String continent){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CONTINENT, continent);
        values.put(CONTINENT_STATUS, 73);
        db.update(TABLE_CONTINENTS, values, KEY_CONTINENT + " = ?",
                new String[] {String.valueOf(continent)});
    }

    @Override
    public void addLevel(DBLevel DBLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LEVEL, DBLevel.getLevel());
        values.put(KEY_POINTS, DBLevel.getPoints());
        values.put(KEY_CONTINENT, DBLevel.getContinent());
        db.insert(TABLE_LEVELS, null, values);
        db.close();
    }
/*
    public int selectContinent(String country){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTINENTS;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Continent> list = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                if (cursor.getString(cursor.getColumnIndex(KEY_CONTINENT)).equals(country))
                    list.add(new Continent(cursor.getString(0),cursor.getInt(cursor.getColumnIndex(CONTINENT_STATUS))));
            }while (cursor.moveToNext());
        }
        Log.d("SQL", String.valueOf(list));
        return list.size() > 0 ? list.get(0).status : 0;
    }*/

    @Override
    public DBLevel getLevel(int id) {
        return null;
    }

    @Override
    public List<DBLevel> getAllLevels() {
        List<DBLevel> DBLevelList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_LEVELS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                DBLevel DBLevel = new DBLevel(0, 0, "");
                DBLevel.setLevel(cursor.getInt(cursor.getColumnIndex("DBLevel")));
                DBLevel.setPoints(cursor.getInt(cursor.getColumnIndex("points")));
                DBLevel.setContinent(cursor.getString(cursor.getColumnIndex("continent")));
                DBLevelList.add(DBLevel);
            } while (cursor.moveToNext());
        }
        //db.close();
        Log.d("LEVELS", String.valueOf(DBLevelList));
        return DBLevelList;
    }

    @Override
    public int getLevelsCount() {
        String countQuery = "SELECT * FROM " + TABLE_LEVELS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    @Override
    public int updateLevel(DBLevel DBLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_LEVEL, DBLevel.getLevel());
        cv.put(KEY_POINTS, DBLevel.getPoints());
        cv.put(KEY_CONTINENT, DBLevel.getContinent());
        return db.update(TABLE_LEVELS, cv, "DBLevel = ? AND continent = ?",
                new String[]{String.valueOf(DBLevel.getLevel()), DBLevel.getContinent()});
    }

    @Override
    public void deleteLevel(DBLevel DBLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LEVELS, KEY_LEVEL + " = ?", new String[] { String.valueOf(DBLevel.getLevel()) });
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LEVELS, null, null);
        db.close();
    }
}