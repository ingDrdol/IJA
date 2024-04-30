package ija.project.ijarobots.RoomLoader;

import ija.project.ijarobots.common.Area;
import ija.project.ijarobots.common.Logger;
import ija.project.ijarobots.common.Obstacle;
import ija.project.ijarobots.common.Robot;
import ija.project.ijarobots.room.Room;
import ija.project.ijarobots.obstacles.Square;
import ija.project.ijarobots.robots.BaseRobot;
import ija.project.ijarobots.robots.ControlledRobot;
import ija.project.ijarobots.robots.AutomatedRobot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class used to import robots from setup files and export robots positions to new setup files
 *
 * @author xvelic05 (xvelic05@stud.fit.vutbr.cz)
 */
public class RobotLoader {

    /**
     * Constructor with no parameters to create object of class RobotLoader
     */
    public RobotLoader(){

    }

    /**
     * Method that reads content of *.csv file with records of robots and obstacles positions,
     * loads robots with their parameters to according rooms list of robots
     * and returns list of robots in given room. TODO fix
     *
     * @param filePath  Path to file with input configuration.
     * @param room      Room that holds list of robots.
     * @return          list of robots loaded from input configuration file.
     */
    public ArrayList<Robot> loadRobots(String filePath, Room room){
        List<List<String>> Attributes = new ArrayList<>();
        ArrayList<Robot> robots = new ArrayList<>();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = buffer.readLine()) != null) {
                String[] values = line.split(",");
                Attributes.add(Arrays.asList(values));
            }
            Logger.getLogger().log(System.Logger.Level.INFO, "BEGINNING ROBOT IMPORT");
            for (List<String> robot : Attributes) {
                String type = robot.get(0);
                if (type.equals("P")) {
                    Logger.getLogger().log(System.Logger.Level.INFO, robot);
                    int cordX = Integer.parseInt(robot.get(1));
                    int cordY = Integer.parseInt(robot.get(2));
                    int size = Integer.parseInt(robot.get(3));
                    int angle = Integer.parseInt(robot.get(4));
                    robots.add(new ControlledRobot(cordX, cordY, size, room, angle));
                } else if (type.equals("R")) {
                    Logger.getLogger().log(System.Logger.Level.INFO, robot);
                    int cordX = Integer.parseInt(robot.get(1));
                    int cordY = Integer.parseInt(robot.get(2));
                    int size = Integer.parseInt(robot.get(3));
                    int angle = Integer.parseInt(robot.get(4));
                    robots.add(new AutomatedRobot(cordX, cordY, size, room, angle));
                }
            }
        } catch (Exception e){
            Logger.getLogger().log(System.Logger.Level.ERROR, e.getMessage());
        }
        finally {
            Logger.getLogger().log(System.Logger.Level.INFO, "ROBOT IMPORT ENDED");
        }
        return robots;
    }

    /**
     * Method that takes actual robots positions, sizes and view angles
     * and stores this information to new *.csv file with name entered in according text field.
     *
     * @param filePath  Path to file in with new configuration will be stored.
     * @param room      Room that holds list of robots.
     */
    public void exportRobots(String filePath, Room room){
        List<Robot> robots = room.getRobots();
        try {
            FileWriter writer = new FileWriter(filePath, true);
            BufferedWriter buffer = new BufferedWriter(writer);
            Logger.getLogger().log(System.Logger.Level.INFO, "BEGINNING ROBOT EXPORT");
            for (Obstacle robot : robots) {
                Logger.getLogger().log(System.Logger.Level.INFO, robot.getParams());
                buffer.write(robot.getParams());
                buffer.newLine();
            }
            buffer.close();
        } catch (Exception e){
            Logger.getLogger().log(System.Logger.Level.ERROR, e.getMessage());
        }
        finally {
            Logger.getLogger().log(System.Logger.Level.INFO, "ROBOT EXPORT ENDED");
        }
    }
}
