package levin.ru.atlas.utility;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

import levin.ru.atlas.CountryItem;

public class CountryGenerator {

    private ArrayList<CountryItem> list;
    private Random r;
    private TreeSet<CountryItem> set;
    private CountryItem tmp;

    public CountryGenerator(ArrayList<CountryItem> list) {
        this.list = list;
        r = new Random();
        set = new TreeSet<>((o1, o2) -> o1.getName().compareTo(o2.getName()));
        set.addAll(list);
        int cnt = 0;
        for (CountryItem countryItem : set) {
            countryItem.setPos(cnt);
            cnt++;
        }
    }

    public void setList(ArrayList<CountryItem> list) {
        this.list = list;
        set.clear();
        set.addAll(list);
    }

    public ArrayList<CountryItem> getList() {
        return list;
    }

    public int next(){
        tmp = set.pollFirst();
        return tmp.getPos();
    }

    static int cnt = 0;

    public int nextWithoutBlocking(){
        ArrayList<CountryItem> list = new ArrayList<>(set);
        int p1 = r.nextInt(list.size()), p2 = r.nextInt(list.size());
        if(p1 != p2){
            if(cnt++ % 2 == 0) return p1;
            else{
                return p2;
            }
        }
        return 0;
    }
}
