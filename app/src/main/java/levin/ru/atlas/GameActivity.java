package levin.ru.atlas;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    ImageView container;
    Button first, second, third;
    TextView timer, points;
    ProgressBar progressBar;
    ArrayList<CountryItem> countries;
    ArrayList<String> names;
    Random rnd;
    int a, b, c, position, progress, min, sec;
    View.OnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = findViewById(R.id.timer);
        points = findViewById(R.id.points);
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);
        container = findViewById(R.id.container);
        progressBar = findViewById(R.id.progressBar);
        progress = 0;

        //progressBar.setMin(0);
        progressBar.setMax(10);
        min = 0; sec = 0;
        //таймер работает нормально
        new Thread(()->{
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sec = (sec + 1) % 60;
                if(sec == 0) {
                    min = (min + 1) % 60;
                }
                timer.setText(String.format("%d%d:%d%d", min / 10, min % 10, sec/10, sec % 10));
            }
        }).start();
        //с обработчиком пока что жопа
        listener = (event) -> {
            Button button = (Button) event;
            progress++;
            progressBar.setProgress(progress);
            if (button.getText().equals(countries.get(position).getName())){
                points.setText(String.valueOf(Integer.parseInt(points.getText().toString()) + 1));
                int tmp = a;
                a = b;
                b = c;
                c = tmp;
                first.setText(names.get(a));
                second.setText(names.get(b));
                third.setText(names.get(c));
                int position = rnd.nextInt(countries.size());
                while (countries.get(position).isVisit()) {
                    int cnt = 0;
                    for (int i = 0; i < countries.size(); i++) {
                        if (!countries.get(i).isVisit()) cnt++;
                    }
                    if(cnt == 0) {
                        position = 100;
                        break;
                    }
                    position = rnd.nextInt(countries.size());
                }
                if(position == 100){
                    container.setImageResource(R.drawable.gameover);
                }
                else {
                    countries.get(position).setVisit(true);
                    container.setImageResource(countries.get(position).getId());
                }
            }
            else {
                int tmp = a;
                a = b;
                b = c;
                c = tmp;
                first.setText(names.get(a));
                second.setText(names.get(b));
                third.setText(names.get(c));
                int position = rnd.nextInt(countries.size());
                while (countries.get(position).isVisit()) {
                    int cnt = 0;
                    for (int i = 0; i < countries.size(); i++) {
                        if (!countries.get(i).isVisit()) cnt++;
                    }
                    if(cnt == 0) {
                        position = 100;
                        break;
                    }
                    position = rnd.nextInt(countries.size());
                }
                if(position == 100){
                    container.setImageResource(R.drawable.gameover);
                }
                else {
                    countries.get(position).setVisit(true);
                    container.setImageResource(countries.get(position).getId());
                }
            }
            //возврат на предыдущую через диалог
            if (progress >= 10){
                int num = RecyclerViewAdapter.num;
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("Game", MODE_PRIVATE, null);
                ContentValues values = new ContentValues();
                values.put("level", num);
                values.put("points", Integer.parseInt(points.getText().toString()));
                db.insert("levels", null, values);
                db.close();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Раунд завершен");
                builder.setMessage("Для возврата в главное меню нажмите ОК");
                builder.setPositiveButton("OK", (dialog, id) -> {
                    Intent intent = new Intent(GameActivity.this, RecyclerViewActivity.class);
                    startActivity(intent);
                });
                builder.show();
            }
        };
        countries = new ArrayList<>();
        names = new ArrayList<>();
        rnd = new Random();
        position = 0;

        for (int i = 1; i < 4; i++) {
            countries.add(new CountryItem(getResources()
                    .getIdentifier("r" + String.valueOf(i),
                            "drawable",
                            "levin.ru.atlas")));
        }
        countries.get(0).setName("France");
        names.addAll(Arrays.asList("France", "Russia", "USA"));
        countries.get(1).setName("Russia");
        countries.get(2).setName("USA");
        container.setImageResource(countries.get(position).getId());
        a = 0; b = 1; c = 2;

        first.setOnClickListener(listener);
        second.setOnClickListener(listener);
        third.setOnClickListener(listener);
        first.setText(names.get(a));
        second.setText(names.get(b));
        third.setText(names.get(c));
    }


}
