package levin.ru.atlas.DButility;

public class DBLevel {
    int level, points;
    String continent;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DBLevel(int level, int points, String continent) {
        this.level = level;
        this.points = points;
        this.continent = continent;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    @Override
    public String toString() {
        return "DBLevel{" +
                "level=" + level +
                ", points=" + points +
                ", continent='" + continent + '\'' +
                ", name='" + name + '\'' +
                '}' + '\n';
    }
}
