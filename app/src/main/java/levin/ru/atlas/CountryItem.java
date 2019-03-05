package levin.ru.atlas;

import android.graphics.drawable.Drawable;

public class CountryItem {
    private Drawable image;
    private int id;
    private boolean visit;
    private String name;

    public CountryItem(int id) {
        this.id = id;
        visit = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Drawable getImage() {
        return image;
    }

    public boolean isVisit() {
        return visit;
    }

    public void setVisit(boolean visit) {
        this.visit = visit;
    }
}
