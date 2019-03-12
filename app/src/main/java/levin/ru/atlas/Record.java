package levin.ru.atlas;

public class Record {

    public static int cnt = 1;

    private int number;
    int points;

    public Record(int points) {
        number = cnt;
        name = "Уровень " + cnt++;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public int getNumber() {
        return number;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {

    }

}
