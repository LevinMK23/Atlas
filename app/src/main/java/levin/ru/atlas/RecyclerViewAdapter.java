package levin.ru.atlas;

import android.annotation.SuppressLint;
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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Record> records;
    public static int num;

    RecyclerViewActivity a;

    public RecyclerViewAdapter(RecyclerViewActivity a, List<Record> records) {
        this.a = a;
        this.records = records;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        return new ViewHolder(a, v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        //на рекорд флажок клек не клик
        Record record = records.get(i);
//        if (record.getNumber() > 9){
//            RecyclerViewActivity.recordCounter += 5;
//        }
        //иф на две картинки кликабельна кнопка или нет
        boolean isPaintLevel = a.levels.containsKey(record.getNumber());
        if(isPaintLevel) {
            int points = record.points;
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
            viewHolder.name.setText(record.getName() + " завершен");
        }
        else {
            viewHolder.icon.setImageResource(R.drawable.newlevel);
            viewHolder.name.setText(record.getName());
        }
        viewHolder.icon.setOnClickListener(view->{
            num = record.getNumber();
            Intent intent = new Intent(a, GameActivity.class);
            a.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    private void delete(Record record) {
        int position = records.indexOf(record);
        records.remove(position);
        notifyItemRemoved(position);
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


    private class DeleteButtonListener implements View.OnClickListener {
        private Record record;

        @Override
        public void onClick(View v) {
            delete(record);
        }

        public void setRecord(Record record) {
            this.record = record;
        }
    }
}
