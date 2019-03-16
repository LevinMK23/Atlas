package levin.ru.atlas.DButility;

import java.util.List;

public interface IDatabaseHandler {
    public void addLevel(DBLevel DBLevel);
    public DBLevel getLevel(int id);
    public List<DBLevel> getAllLevels();
    public int getLevelsCount();
    public int updateLevel(DBLevel DBLevel);
    public void deleteLevel(DBLevel DBLevel);
    public void deleteAll();
}
