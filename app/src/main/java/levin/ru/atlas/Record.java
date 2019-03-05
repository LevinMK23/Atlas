package levin.ru.atlas;

public class Record {

    static int cnt = 1;

    public Record() {
        name = "Уровень " + cnt++;
    }

    public enum Type {RED, GREEN, YELLOW}

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
