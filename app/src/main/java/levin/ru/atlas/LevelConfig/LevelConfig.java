package levin.ru.atlas.LevelConfig;

import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import levin.ru.atlas.Activities.StartActivity;
import levin.ru.atlas.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class LevelConfig {

    Level [] levels;
    Continent [] continents;
    Level clickedLevel;

    public void updateConfig(File data){
        try (PrintWriter writer = new PrintWriter(data)){
            for (Continent continent : getContinents()){
                writer.println(continent.getContinent() + " " + continent.isPaint());
                Log.d("FILE_UPDATE", continent.getContinent() + " " + continent.isPaint());
            }
            for (Level level : getLevels()){
                writer.println(level.getId() + " " + level.getPoints() + " " + level.getContinent() + " " + level.getType());
                Log.d("FILE_UPDATE",level.getId() + " " + level.getPoints() + " " + level.getContinent() + " " + level.getType());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initConfig(File file) throws FileNotFoundException {
        Scanner in = new Scanner(file);
        while (in.hasNextLine()){
            Log.d("FILE", in.nextLine());
        }
        in = new Scanner(file);
        for (int i = 0; i < 4; i++) {
            String continent = in.next();
            boolean isPaint = in.nextBoolean();
            continents[i].continent = continent;
            continents[i].isPaint = isPaint;
        }
        for (int i = 0; i < 40; i++) {
            int id = in.nextInt(), points = in.nextInt();
            String continent = in.next();
            int type = in.nextInt();
            levels[i].id = id;
            levels[i].points = points;
            levels[i].continent = continent;
            levels[i].type = type;
        }
    }

    public Level[] getLevels() {
        return levels;
    }

    public Continent[] getContinents() {
        return continents;
    }

    public Continent getContinent(int id){
        return continents[id];
    }

    public void setClickedLevel(Level clickedLevel) {
        this.clickedLevel = clickedLevel;
    }

    public Level getClickedLevel() {
        return clickedLevel;
    }

    public LevelConfig() {
        clickedLevel = null;
        levels = new Level[40];
        continents = new Continent[4];
        continents[0] = new Continent("Африка");
        continents[1] = new Continent("Евразия");
        continents[2] = new Continent("СевернаяАмерика");
        continents[3] = new Continent("ЮжнаяАмерика");
        for (int i = 0; i < 40; i++) {
            levels[i] = new Level(i % 10 + 1,
                    -1, continents[i / 10].getContinent());
            levels[i].type = 0;
        }
    }

    public void setPoints(int id, String continent, int points){
        for(Level level : levels){
            if(level.id == id && level.continent.equals(continent)){
                level.points = points;
                return;
            }
        }
    }

    public int getPoints(int id, String continent){
        for(Level level : levels){
            if(level.id == id && level.continent.equals(continent)){
                return level.points;
            }
        }
        return 0;
    }

    public void paint(int index){
        continents[index].isPaint = true;
    }

    public boolean isPaint(int index){
        return continents[index].isPaint;
    }
}