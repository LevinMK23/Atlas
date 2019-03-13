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
import java.util.TreeMap;

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
    TreeMap<String, ArrayList<CountryItem>> map;
    Random rnd;
    int a, b, c, position, progress, min, sec;
    View.OnClickListener listener;
    CountryGenerator gen;
    static int state;
    final int AFRICA = 0, EURASIA = 1, SAMERICA = 2, NAMERICA = 3;
    String [] data;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //timer = findViewById(R.id.timer);
        points = findViewById(R.id.points);
        btn = new Button[3];
        data = new String[4];
        data[AFRICA] = "Африка";
        data[EURASIA] = "Евразия";
        data[SAMERICA] = "Южная Америка";
        data[NAMERICA] = "Северная Америка";
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
                    if(btn[i].getText().toString().isEmpty())
                        btn[i].setText(map.get(data[state]).get(gen.nextWithoutBlocking()).getName());

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
                builder.setMessage("Вы набрали "+ points.getText() + " очков.\nДля возврата в главное меню нажмите ОК");
                builder.setPositiveButton("OK", (dialog, id) -> {
                    Intent intent = new Intent(GameActivity.this, RecyclerViewActivity.class);
                    startActivity(intent);
                });
                builder.show();
            }
        };
        countries = new ArrayList<>();
        rnd = new Random();
        //читаем файлик с странами
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
    }

}
