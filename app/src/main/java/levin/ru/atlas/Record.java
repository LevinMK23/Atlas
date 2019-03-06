package levin.ru.atlas;

public class Record {

    static int cnt = 1;

    private int number;


    public Record() {
        number = cnt;
        name = "Уровень " + cnt++;
    }

    public enum Type {RED, GREEN, YELLOW}

    public int getNumber() {
        return number;
    }

    private String name;
    private Type type;

    public String getName() {
        return name;
    }

    public void setName(String name) {

    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Record copy(){
        Record copy = new Record();
        copy.setType(type);
        copy.setName(name);
        return copy;
    }
}
