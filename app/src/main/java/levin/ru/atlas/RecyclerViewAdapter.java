package levin.ru.atlas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Record> records;

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

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        //на рекорд флажок клек не клик
        Record record = records.get(i);
        int iconResourceId =  R.drawable.level;
        //иф на две картинки кликабельна кнопка или нет
        boolean isPaintLevel = false;
        for (DbRecord rec : a.levels) {
            if(rec.level == record.getNumber()){
                isPaintLevel = true;
                break;
            }
        }
        if(isPaintLevel) viewHolder.icon.setImageResource(iconResourceId);
        else viewHolder.icon.setImageResource(R.drawable.gameover);
        viewHolder.icon.setOnClickListener(view->{
            Intent intent = new Intent(a, GameActivity.class);
            a.startActivity(intent);
        });
        viewHolder.name.setText(record.getName());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    private void copy(Record record) {
        int position = records.indexOf(record);
        Record copy = record.copy();
        records.add(position + 1, copy);
        notifyItemInserted(position + 1);
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

    private class CopyButtonListener implements View.OnClickListener {

        private Record record;

        @Override
        public void onClick(View v) {
            copy(record);
        }

        public void setRecord(Record record) {
            this.record = record;
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
