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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import levin.ru.atlas.utility.CountryGenerator;
import levin.ru.atlas.CountryItem;
import levin.ru.atlas.R;
import levin.ru.atlas.RecyclerViewAdapter;
import levin.ru.atlas.utility.TintIcons;

public class GameActivity extends AppCompatActivity {

    ImageView container;
    Button [] btn;
    int [] rndId = {1, 2, 3};
    TextView timer, points;
    ProgressBar progressBar;
    ArrayList<CountryItem> countries;
    ArrayList<String> names;
    Random rnd;
    int a, b, c, position, progress, min, sec;
    View.OnClickListener listener;
    CountryGenerator gen;
    static int state;
    final int AFRICA = 0, EURASIA = 1, SAMERICA = 2, NAMERICA = 3;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //timer = findViewById(R.id.timer);
        points = findViewById(R.id.points);
        btn = new Button[3];
        btn[0] = findViewById(R.id.first);
        btn[1] = findViewById(R.id.second);
        btn[2] = findViewById(R.id.third);
        container = findViewById(R.id.container);
        progressBar = findViewById(R.id.progressBar);
        progress = 0;
        //progressBar.setMin(0);
        progressBar.setMax(10);
        min = 0; sec = 0;
        state = StartActivity.state;
        Log.d("Continent STATE", String.valueOf(state));
        //таймер работает нормально
        //обработчик работает нормально
        listener = (event) -> {
            Button button = (Button) event;
            progress++;
            progressBar.setProgress(progress);
            if (button.getText().equals(countries.get(position).getName())) {
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
                container.setImageResource(countries.get(position).getId());
                TintIcons.tintImageView(container, R.drawable.color_list);
                btn[ok].setText(countries.get(position).getName());
                for (int i = 0; i < 3; i++) {
                    if(btn[i].getText().toString().isEmpty())
                        btn[i].setText(countries.get(gen.next()).getName());

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
        //читаем файлик с странами
        InputStream inputStream = getResources().openRawResource(R.raw.levels);
        InputStream africa = getResources().openRawResource(R.raw.africa);
        InputStream eurasia = getResources().openRawResource(R.raw.eurasia);
        InputStream samerica = getResources().openRawResource(R.raw.samerica);
        InputStream namerica = getResources().openRawResource(R.raw.namerica);
        Scanner in = new Scanner(inputStream);
        ArrayList<CountryItem> africaList = new ArrayList<>();
        ArrayList<CountryItem> eurasiaList = new ArrayList<>();
        ArrayList<CountryItem> namericaList = new ArrayList<>();
        ArrayList<CountryItem> samericaList = new ArrayList<>();
        while (in.hasNextLine()) names.add(in.nextLine());
        for (int i = 1; i < 200; i++) {
            //закидываем страны в список
            countries.add(new CountryItem(getResources()
                    .getIdentifier("r" + String.valueOf(i),
                            "drawable",
                            "levin.ru.atlas"), names.get(i-1)));
        }
        if (state == AFRICA){
            in = new Scanner(africa);
            while (in.hasNextLine()){
                String [] line = in.nextLine().split("#");
                if(line.length > 1)
                    africaList.add(new CountryItem(line[0].trim(), line[1].trim(), "Африка"));
            }

            for (CountryItem item : africaList) {
                for (CountryItem country : countries){
                    if(item.getName().equals(country.getName())){
                        item.setId(country.getId());
                    }
                }
            }
            for (int i = 0; i < africaList.size(); i++){
                CountryItem item = africaList.get(i);
                if (item.getId() <= 5)
                    africaList.remove(item);
            }
            countries = africaList;
            gen = new CountryGenerator(africaList);
            Log.d("Continent STATE", africaList.toString());
        }
        if(state == EURASIA){
            in = new Scanner(eurasia);
            while (in.hasNextLine()){
                String [] line = in.nextLine().split("#");
                if(line.length > 1)
                    eurasiaList.add(new CountryItem(line[0].trim(), line[1].trim(), "Евразия"));
            }
            for (CountryItem item : eurasiaList) {
                for (CountryItem country : countries){
                    if(item.getName().equals(country.getName())){
                        item.setId(country.getId());
                    }
                }
            }
            for (int i = 0; i < eurasiaList.size(); i++){
                CountryItem item = eurasiaList.get(i);
                if (item.getId() == -1) eurasiaList.remove(item);
            }
            Log.d("Continent STATE", eurasiaList.toString());
            countries = eurasiaList;
            gen = new CountryGenerator(eurasiaList);
        }
        if(state == NAMERICA){
            in = new Scanner(namerica);
            while (in.hasNextLine()){
                String [] line = in.nextLine().split("#");
                if(line.length > 1)
                    namericaList.add(new CountryItem(line[0].trim(), line[1].trim(), "Северная Америка"));
            }
            for (CountryItem item : namericaList) {
                for (CountryItem country : countries){
                    if(item.getName().equals(country.getName())){
                        item.setId(country.getId());
                    }
                }
            }
            for (int i = 0; i < namericaList.size(); i++){
                CountryItem item = namericaList.get(i);
                if (item.getId() == -1) namericaList.remove(item);
            }
            Log.d("Continent STATE", namericaList.toString());
            countries = namericaList;
            gen = new CountryGenerator(namericaList);
        }
        if(state == SAMERICA){
            in = new Scanner(samerica);
            while (in.hasNextLine()){
                String [] line = in.nextLine().split("#");
                if(line.length > 1)
                    samericaList.add(new CountryItem(line[0].trim(), line[1].trim(), "Южная Америка"));
            }
            for (CountryItem item : samericaList) {
                for (CountryItem country : countries){
                    if(item.getName().equals(country.getName())){
                        item.setId(country.getId());
                    }
                }
            }
            for (int i = 0; i < samericaList.size(); i++){
                CountryItem item = samericaList.get(i);
                if (item.getId() == -1) samericaList.remove(item);
            }
            Log.d("Continent STATE", samericaList.toString());
            countries = samericaList;
            gen = new CountryGenerator(samericaList);
        }
        //генерит индекс и блочит итем с картинки для выдачи в следующие разы
        position = gen.next();
        CountryItem tmp = countries.get(position);
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
                btn[i].setText(countries.get(gen.next()).getName());
            }
        }
    }

}
