package levin.ru.atlas;

import android.graphics.drawable.Drawable;

public class CountryItem {
    private Drawable image;
    private int id;
    private boolean visit;
    private String country, capital, continent;

    public CountryItem(String country, String capital, String continent) {
        this.country = country;
        this.capital = capital;
        this.continent = continent;
        id = -1;
    }

    public CountryItem(int id, String name) {
        this.id = id;
        visit = false;
        country = name;
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

    @Override
    public String toString() {
        return String.format("%s %s %s %d", country, capital, continent, id);
    }
}
