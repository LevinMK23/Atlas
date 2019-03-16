package levin.ru.atlas.LevelConfig;

public class Continent {
    String continent;
    boolean isPaint;

    public Continent(String continent) {
        this.continent = continent;
        isPaint = false;
    }

    public String getContinent() {
        return continent;
    }

    public boolean isPaint() {
        return isPaint;
    }

    public void setPaint(boolean paint) {
        isPaint = paint;
    }

    @Override
    public String toString() {
        return "Continent{" +
                "continent='" + continent + '\'' +
                ", isPaint=" + isPaint +
                '}';
    }
}
