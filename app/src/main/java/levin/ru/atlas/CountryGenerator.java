package levin.ru.atlas;

import java.util.ArrayList;
import java.util.Random;

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

    int next(){
        int position = r.nextInt(list.size()), cnt = 0;
        while (list.get(position).isVisit()){
            cnt++;
            position = (position + 1) % list.size();
            if(cnt > list.size()) return 1000;
        }
        list.get(position).setVisit(true);
        return position;
    }
}
