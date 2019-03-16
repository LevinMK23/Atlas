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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import levin.ru.atlas.DButility.*;

import levin.ru.atlas.LevelConfig.Level;
import levin.ru.atlas.LevelConfig.LevelConfig;
import levin.ru.atlas.R;
import levin.ru.atlas.utility.RecyclerViewAdapter;


public class RecyclerViewActivity extends Activity {

    LevelConfig config;
    File inputData;
    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        String [] continents = {"Африка", "Евразия", "СевернаяАмерика", "ЮжнаяАмерика"};
        String continent = continents[StartActivity.state];
        config = StartActivity.config;
        inputData = StartActivity.inputData;
        //здесь создаем уровни
        ArrayList<Level> needLevels = new ArrayList<>();
        for (Level level : config.getLevels()){
            //Log.d("NEEDLIST", Arrays.toString(config.getLevels()));
            if (level.getContinent().equals(continent)){
                needLevels.add(level);
            }
        }
        Log.d("NEEDLIST", String.valueOf(needLevels));
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, needLevels, config);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }


    private void loadData(File data, LevelConfig config) throws FileNotFoundException {
        boolean isDone = false;
        if(!data.exists()){
            try {
                isDone = data.createNewFile();
                config.updateConfig(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config.initConfig(data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //config.updateConfig(inputData);
    }

    @Override
    protected void onPause() {
        super.onPause();
        config.updateConfig(inputData);
    }

    @Override
    protected void onStop() {
        super.onStop();
        config.updateConfig(inputData);
    }

    public void goToStartActivity(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
