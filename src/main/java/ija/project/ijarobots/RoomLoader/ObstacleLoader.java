package ija.project.ijarobots.RoomLoader;

import ija.project.ijarobots.common.Area;
import ija.project.ijarobots.common.Logger;
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
import java.util.logging.Level;

public class ObstacleLoader {
    public ObstacleLoader(){

    }

    public ArrayList<Obstacle> loadObstacles(String filePath){
        Logger logger = Logger.getLogger();
        List<List<String>> Atributes = new ArrayList<>();
        ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = buffer.readLine()) != null) {
                String[] values = line.split(",");
                Atributes.add(Arrays.asList(values));
            }
            logger.log(System.Logger.Level.INFO, "BEGINNING OBSTACLE IMPORT");
            for (List<String> object : Atributes) {
                String type = object.get(0);
                if (type.equals("S")) {
                    logger.log(System.Logger.Level.INFO, object);
                    int cordX = Integer.parseInt(object.get(1));
                    int cordY = Integer.parseInt(object.get(2));
                    int size = Integer.parseInt(object.get(3));
                    obstacles.add(new Square(cordX, cordY, size));
                }
            }
        } catch (Exception e){
            logger.log(System.Logger.Level.ERROR, e.getMessage());
        }
        finally {
            logger.log(System.Logger.Level.INFO, "OBSTACLE IMPORT ENDED");
        }
        return obstacles;
    }

    public void exportObstacles(String filePath, Room room, Logger logger){
        List<Obstacle> obstacles = room.getObstacles();
        try {
            FileWriter writer = new FileWriter(filePath, true);
            BufferedWriter buffer = new BufferedWriter(writer);
            logger.log(System.Logger.Level.INFO, "BEGINNING OBSTACLE EXPORT");
            for (Obstacle obj : obstacles) {
                logger.log(System.Logger.Level.INFO, obj.getParams());
                buffer.write(obj.getParams());
                buffer.newLine();
            }
            buffer.close();
        } catch (Exception e){
            logger.log(System.Logger.Level.ERROR, e.getMessage());
        }
        finally {
            logger.log(System.Logger.Level.INFO, "OBSTACLE EXPORT ENDED");
        }
    }
}
