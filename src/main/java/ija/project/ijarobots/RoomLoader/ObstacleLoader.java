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

/**
 * Class used to import obstacles from setup files and export obstacles positions to new setup files
 *
 * @author xvelic05 (xvelic05@stud.fit.vutbr.cz)
 */
public class ObstacleLoader {

    /**
     * Constructor with no parameters to create object of class RobotLoader
     */
    public ObstacleLoader(){

    }

    /**
     * Method that reads content of *.csv file with records of robots and obstacles positions,
     * loads obstacles with their parameters to created list of obstacles
     * and returns list of loaded obstacles TODO fix
     *
     * @param filePath  Path to file with input configuration.
     * @return          list of obstacles loaded from input configuration file.
     */
    public ArrayList<Obstacle> loadObstacles(String filePath){
        List<List<String>> Atributes = new ArrayList<>();
        ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = buffer.readLine()) != null) {
                String[] values = line.split(",");
                Atributes.add(Arrays.asList(values));
            }
            Logger.getLogger().log(System.Logger.Level.INFO, "BEGINNING OBSTACLE IMPORT");
            for (List<String> object : Atributes) {
                String type = object.get(0);
                if (type.equals("S")) {
                    Logger.getLogger().log(System.Logger.Level.INFO, object);
                    int cordX = Integer.parseInt(object.get(1));
                    int cordY = Integer.parseInt(object.get(2));
                    int size = Integer.parseInt(object.get(3));
                    obstacles.add(new Square(cordX, cordY, size));
                }
            }
        } catch (Exception e){
            Logger.getLogger().log(System.Logger.Level.ERROR, e.getMessage());
        }
        finally {
            Logger.getLogger().log(System.Logger.Level.INFO, "OBSTACLE IMPORT ENDED");
        }
        return obstacles;
    }

    /**
     * Method that takes actual Obstacles positions and sizes
     * and stores this information to new *.csv file with name entered in according text field.
     *
     * @param filePath  Path to file in with new configuration will be stored.
     * @param room      Room that holds list of obstacless.
     */
    public void exportObstacles(String filePath, Room room){
        List<Obstacle> obstacles = room.getObstacles();
        try {
            FileWriter writer = new FileWriter(filePath, true);
            BufferedWriter buffer = new BufferedWriter(writer);
            Logger.getLogger().log(System.Logger.Level.INFO, "BEGINNING OBSTACLE EXPORT");
            for (Obstacle obj : obstacles) {
                Logger.getLogger().log(System.Logger.Level.INFO, obj.getParams());
                buffer.write(obj.getParams());
                buffer.newLine();
            }
            buffer.close();
        } catch (Exception e){
            Logger.getLogger().log(System.Logger.Level.ERROR, e.getMessage());
        }
        finally {
            Logger.getLogger().log(System.Logger.Level.INFO, "OBSTACLE EXPORT ENDED");
        }
    }
}
