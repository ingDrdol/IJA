package ija.project.ijarobots.RoomLoader;

import ija.project.ijarobots.common.Area;
import ija.project.ijarobots.common.Obstacle;
import ija.project.ijarobots.obstacles.Square;
import ija.project.ijarobots.room.Room;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObstacleLoader {
    public ObstacleLoader(){

    }

    public List<Obstacle> loadObstacles(String filePath){
        List<List<String>> Atributes = new ArrayList<>();
        List<Obstacle> obstacles = new ArrayList<Obstacle>();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = buffer.readLine()) != null){
                String[] values = line.split(",");
                Atributes.add(Arrays.asList(values));
        }
        } catch (Exception e){
        }
        for (List<String> object : Atributes) {
            String type = object.get(0);
            if (type.equals("S")) {
                int cordX = Integer.parseInt(object.get(1));
                int cordY = Integer.parseInt(object.get(2));
                int size = Integer.parseInt(object.get(3));
                obstacles.add(new Square(cordX, cordY, size));
            }
        }
        return obstacles;
    }

    public void exportObstacles(String filePath, Area area){
        List<Obstacle> obstacles = area.getObstacles();
        try {
            FileWriter writer = new FileWriter(filePath, true);
            BufferedWriter buffer = new BufferedWriter(writer);
            for (Obstacle object : obstacles) {
                buffer.write(object.getParams());
                buffer.newLine();
            }
            buffer.close();
        } catch (Exception e){

        }
    }
}