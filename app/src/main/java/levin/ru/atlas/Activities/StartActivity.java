package levin.ru.atlas.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import levin.ru.atlas.LevelConfig.LevelConfig;
import levin.ru.atlas.R;
import levin.ru.atlas.utility.TintIcons;


public class StartActivity extends AppCompatActivity {

    ImageView [] img;
    View.OnClickListener click;
    final String [] data = {"Африка", "Евразия", "СевернаяАмерика", "ЮжнаяАмерика"};
    static public int state;
    Intent intent;
    public static LevelConfig config;
    public static File inputData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        config = new LevelConfig();
        img = new ImageView[4];
        img[0] = findViewById(R.id.africa);
        img[1] = findViewById(R.id.euroasia);
        img[3] = findViewById(R.id.samerica);
        img[2] = findViewById(R.id.namerica);
        File dir = getFilesDir();
        inputData = new File(dir, "input.txt");
        //inputData.delete();
        try{
            loadData(inputData, config);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        for (int i = 0; i < 4; i++) {
            if(config.getContinent(i).isPaint()){
                TintIcons.tintImageView(img[i], R.color.colorAccent);
            }
        }
        intent = new Intent(this, ChoiceActivity.class);
        click = v -> {
                switch (v.getId()){
                    case R.id.africa:
                        TintIcons.tintImageView((ImageView) v, R.color.colorAccent);
                        state = 0;
                        config.getContinent(state).setPaint(true);
                        startActivity(intent);
                        break;
                    case R.id.euroasia:
                        TintIcons.tintImageView((ImageView) v, R.color.colorAccent);
                        state = 1;
                        config.getContinent(state).setPaint(true);
                        startActivity(intent);
                        break;
                    case R.id.samerica:
                        TintIcons.tintImageView((ImageView) v, R.color.colorAccent);
                        state = 3;
                        config.getContinent(state).setPaint(true);
                        startActivity(intent);
                        break;
                    case R.id.namerica:
                        TintIcons.tintImageView((ImageView) v, R.color.colorAccent);
                        state = 2;
                        config.getContinent(state).setPaint(true);
                        startActivity(intent);
                        break;
                }
        };
        for (int i = 0; i < 4; i++) {
            img[i].setOnClickListener(click);
        }
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
}
