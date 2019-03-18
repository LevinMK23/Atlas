package levin.ru.atlas.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import levin.ru.atlas.R;

public class ChoiceActivity extends AppCompatActivity {

    RadioGroup group;
    RadioButton e, m, h;
    ImageButton flag, guess, image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choise);
        group = findViewById(R.id.radioGroup);
        e = findViewById(R.id.easy);
        m = findViewById(R.id.medium);
        h = findViewById(R.id.hard);
        guess = findViewById(R.id.type1);
        guess.setOnClickListener(view->{
            Intent intent = new Intent(this, RecyclerViewActivity.class);
            startActivity(intent);
        });
    }
}
