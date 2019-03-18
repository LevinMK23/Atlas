package levin.ru.atlas;

import android.graphics.drawable.Drawable;

public class CountryItem {
    private Drawable image;
    private int pos;
    private int id;
    private boolean visit;
    private int color;
    private String country, capital, continent;

//    public CountryItem(int id, String country, String capital, String continent) {
//        this.country = country;
//        this.capital = capital;
//        this.continent = continent;
//        this.id = id;
//        color = 0;
//    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public CountryItem(int id, int position, String name, String capital, String continent) {
        this.id = id;
        visit = false;
        country = name;
        this.pos = position;
        this.capital = capital;
        this.continent = continent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCapital() {
        return capital;
    }

    public String getContinent() {
        return continent;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getName() {
        return country;
    }

    public void setName(String name) {
        country = name;
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

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %d", country, capital, continent, id);
    }
}
