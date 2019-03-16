package levin.ru.atlas.Activities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

import levin.ru.atlas.DButility.DbHelper;
import levin.ru.atlas.DButility.DBLevel;
import levin.ru.atlas.LevelConfig.Level;
import levin.ru.atlas.LevelConfig.LevelConfig;
import levin.ru.atlas.utility.CountryGenerator;
import levin.ru.atlas.CountryItem;
import levin.ru.atlas.R;
import levin.ru.atlas.utility.TintIcons;

public class GameActivity extends AppCompatActivity {

    ImageView container;
    Button [] btn;
    TextView points, result;
    ProgressBar progressBar;
    ArrayList<CountryItem> countries;
    TreeMap<String, ArrayList<CountryItem>> map;
    Random rnd;
    int position, progress, min, sec;
    View.OnClickListener listener;
    CountryGenerator gen;
    static int state;
    final int AFRICA = 0, EURASIA = 1, SAMERICA = 3, NAMERICA = 2;
    String [] data;
    DbHelper helper;
    LevelConfig config;
    File inputData;
    public static Level clickedDBLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        config = StartActivity.config;
        inputData = StartActivity.inputData;
        points = findViewById(R.id.points);
        result = findViewById(R.id.result);
        btn = new Button[3];
        data = new String[4];
        Log.d("CLICKED LEVEL", String.valueOf(config.getClickedLevel()));
        data[AFRICA] = "Африка";
        data[EURASIA] = "Евразия";
        data[SAMERICA] = "ЮжнаяАмерика";
        data[NAMERICA] = "СевернаяАмерика";
        btn[0] = findViewById(R.id.first);
        btn[1] = findViewById(R.id.second);
        btn[2] = findViewById(R.id.third);
        container = findViewById(R.id.container);
        progressBar = findViewById(R.id.progressBar);
        progress = 0;
        progressBar.setMax(10);
        min = 0; sec = 0;
        state = StartActivity.state;
        Log.d("Continent STATE", String.valueOf(state));
        //таймер работает нормально
        //c обработчиком пока что жопа
        listener = (event) -> {
            Button button = (Button) event;
            progress++;
            progressBar.setProgress(progress);
            if (button.getText().equals(map.get(data[state]).get(position).getName())) {
                points.setText(String.valueOf(Integer.parseInt(points.getText().toString()) + 1));
            }
            for (int i = 0; i < 3; i++) btn[i].setText("");
            int ok = rnd.nextInt(3);
            position = gen.next();
            if(position == 1000){
                container.setImageResource(R.drawable.gameover);
                TintIcons.tintImageView(container, R.drawable.color_list);
            }
            else {
                container.setImageResource(map.get(data[state]).get(position).getId());
                TintIcons.tintImageView(container, R.drawable.color_list);
                btn[ok].setText(map.get(data[state]).get(position).getName());
                for (int i = 0; i < 3; i++) {
                    int pos2 = gen.nextWithoutBlocking();
                    if(pos2 < 1000) {
                        if (btn[i].getText().toString().isEmpty())
                            btn[i].setText(map.get(data[state]).get(pos2).getName());
                    }
                    else{
                        container.setImageResource(R.drawable.gameover);
                    }

                }
            }
            //возврат на предыдущую через диалог
            if (progress >= 10){
                config.getClickedLevel().setPoints(Integer.parseInt(points.getText().toString()));
                for (int i = 0; i < 3; i++) {
                    btn[i].setClickable(false);
                }
                result.setText("Уровень завершен, вы набрали "+ points.getText().toString() + " очков, для возврата в главное меню используйте кнопку назад");
            }
        };
        countries = new ArrayList<>();
        rnd = new Random();
        //читаем файлик с странами
        readCountryData();
        //генерит индекс и блочит итем с картинки для выдачи в следующие разы
        position = gen.next();
        CountryItem tmp = map.get(data[state]).get(position);
        container.setImageResource(tmp.getId());
        //утилитка для перекрашивания картинок
        TintIcons.tintImageView(container, R.drawable.color_list);
        for (int i = 0; i < 3; i++){
            btn[i].setOnClickListener(listener);
            btn[i].setText("");
        }
        int ok = rnd.nextInt(3);
        btn[ok].setText(tmp.getName());
        for (int i = 0; i < 3; i++) {
            if(btn[i].getText().toString().isEmpty()){
                btn[i].setText(map.get(data[state]).get(gen.nextWithoutBlocking()).getName());
            }
        }
        //helper.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // config.updateConfig(inputData);
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

    private void readCountryData() {
        InputStream inputStream = getResources().openRawResource(R.raw.levels);
        Scanner in = new Scanner(inputStream);
        int cnt = 1;
        while (in.hasNextLine()) {
            String [] line = in.nextLine().split("#");
            for (int i = 0; i < line.length; i++) {
                line[i] = line[i].trim();
            }
            if(line.length == 3)
                countries.add(new CountryItem(getResources()
                        .getIdentifier("r" + String.valueOf(cnt),
                                "drawable",
                                "levin.ru.atlas"), line[0], line[1], line[2]));
            cnt++;
        }
        map = new TreeMap<>();
        for (CountryItem item : countries){
            if(map.containsKey(item.getContinent())){
                map.get(item.getContinent()).add(item);
            }
            else{
                ArrayList<CountryItem> items = new ArrayList<>();
                items.add(item);
                map.put(item.getContinent(), items);
            }
        }
        switch (state){
            case AFRICA:
                gen = new CountryGenerator(map.get(data[AFRICA]));
                Log.d("AFRICA", String.valueOf(map.get(data[AFRICA])));
                break;
            case EURASIA:
                gen = new CountryGenerator(map.get(data[EURASIA]));
                break;
            case NAMERICA:
                gen = new CountryGenerator(map.get(data[NAMERICA]));
                break;
            case SAMERICA:
                gen = new CountryGenerator(map.get(data[SAMERICA]));
                break;
        }
    }

    public void goToPrevPage(View view) {
        Intent intent = new Intent(this, RecyclerViewActivity.class);
        startActivity(intent);
    }
}
