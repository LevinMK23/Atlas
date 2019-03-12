package levin.ru.atlas.utility;

import java.util.ArrayList;
import java.util.Random;

import levin.ru.atlas.CountryItem;

public class CountryGenerator {

    private ArrayList<CountryItem> list;
    private Random r;


    public CountryGenerator(ArrayList<CountryItem> list) {
        this.list = list;
        r = new Random();
    }

    public void setList(ArrayList<CountryItem> list) {
        this.list = list;
    }

    public ArrayList<CountryItem> getList() {
        return list;
    }

    public int next(){
        int position = r.nextInt(list.size()), cnt = 0;
        while (list.get(position).isVisit()){
            cnt++;
            position = (position + 1) % list.size();
            if(cnt > list.size()) return 1000;
        }
        list.get(position).setVisit(true);
        if(list.get(position).getId() != -1)
            return position;
        else return next();
    }
}
