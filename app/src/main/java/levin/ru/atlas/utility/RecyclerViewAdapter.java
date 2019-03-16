package levin.ru.atlas.utility;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import levin.ru.atlas.Activities.GameActivity;
import levin.ru.atlas.Activities.RecyclerViewActivity;
import levin.ru.atlas.DButility.DBLevel;
import levin.ru.atlas.LevelConfig.Level;
import levin.ru.atlas.LevelConfig.LevelConfig;
import levin.ru.atlas.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Level> records;
    public static int num;
    public static Level clickedDBLevel;
    LevelConfig config;
    RecyclerViewActivity a;

    public RecyclerViewAdapter(RecyclerViewActivity a, List<Level> records, LevelConfig config) {
        this.a = a;
        this.records = records;
        this.config = config;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        return new ViewHolder(a, v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Level level = records.get(i);
        if(level.getPoints() > -1) {
            int points = level.getPoints();
            if(points < 3){
                viewHolder.icon.setImageResource(R.drawable.state0);
            }
            else if(points < 6){
                viewHolder.icon.setImageResource(R.drawable.state1);
            }
            else if(points < 9){
                viewHolder.icon.setImageResource(R.drawable.state2);
            }
            else{
                viewHolder.icon.setImageResource(R.drawable.state3);
            }
            viewHolder.name.setText("Level " + level.getId() + " finished");
        }
        else {
            viewHolder.icon.setImageResource(R.drawable.newlevel);
            viewHolder.name.setText("Level " + level.getId());
        }
        viewHolder.icon.setOnClickListener(view->{
            config.setClickedLevel(level);
            Intent intent = new Intent(a, GameActivity.class);
            a.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView icon;

        public ViewHolder(RecyclerViewActivity a, View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recyclerViewItemName);
            icon = itemView.findViewById(R.id.imageView);
        }
    }

}
