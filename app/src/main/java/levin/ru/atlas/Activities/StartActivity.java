package levin.ru.atlas.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import levin.ru.atlas.Activities.RecyclerViewActivity;
import levin.ru.atlas.R;
import levin.ru.atlas.utility.TintIcons;


public class StartActivity extends AppCompatActivity {

    ImageView [] img;
    View.OnClickListener click;
    final int AFRICA = 0, EURASIA = 1, SAMERICA = 2, NAMERICA = 3;
    static int state;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        state = 0;
        img = new ImageView[4];
        img[0] = findViewById(R.id.africa);
        img[1] = findViewById(R.id.euroasia);
        img[2] = findViewById(R.id.samerica);
        img[3] = findViewById(R.id.namerica);
        intent = new Intent(this, RecyclerViewActivity.class);
        click = v -> {
                switch (v.getId()){
                    case R.id.africa:
                        TintIcons.tintImageView((ImageView) v, R.color.colorAccent);
                        state = AFRICA;
                        startActivity(intent);
                        break;
                    case R.id.euroasia:
                        TintIcons.tintImageView((ImageView) v, R.color.colorAccent);
                        state = EURASIA;
                        startActivity(intent);
                        break;
                    case R.id.samerica:
                        TintIcons.tintImageView((ImageView) v, R.color.colorAccent);
                        state = SAMERICA;
                        startActivity(intent);
                        break;
                    case R.id.namerica:
                        TintIcons.tintImageView((ImageView) v, R.color.colorAccent);
                        state = NAMERICA;
                        startActivity(intent);
                        break;
                }
        };
        for (int i = 0; i < 4; i++) {
            img[i].setOnClickListener(click);
        }
    }


}
