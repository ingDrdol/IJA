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

public class RobotLoader {
    public RobotLoader(){

    }

    public ArrayList<Robot> loadRobots(String filePath, Area area, Logger logger){
        List<List<String>> Atributes = new ArrayList<>();
        ArrayList<Robot> robots = new ArrayList<>();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = buffer.readLine()) != null) {
                String[] values = line.split(",");
                Atributes.add(Arrays.asList(values));
            }
            logger.log(System.Logger.Level.INFO, "BEGINNING ROBOT IMPORT");
            for (List<String> robot : Atributes) {
                String type = robot.get(0);
                if (type.equals("P")) {
                    logger.log(System.Logger.Level.INFO, robot);
                    int cordX = Integer.parseInt(robot.get(1));
                    int cordY = Integer.parseInt(robot.get(2));
                    int size = Integer.parseInt(robot.get(3));
                    int angle = Integer.parseInt(robot.get(4));
                    robots.add(new ControlledRobot(cordX, cordY, size, area, angle));
                } else if (type.equals("R")) {
                    logger.log(System.Logger.Level.INFO, robot);
                    int cordX = Integer.parseInt(robot.get(1));
                    int cordY = Integer.parseInt(robot.get(2));
                    int size = Integer.parseInt(robot.get(3));
                    int angle = Integer.parseInt(robot.get(4));
                    robots.add(new AutomatedRobot(cordX, cordY, size, area, angle));
                }
            }
        } catch (Exception e){
            logger.log(System.Logger.Level.ERROR, e.getMessage());
        }
        finally {
            logger.log(System.Logger.Level.INFO, "ROBOT IMPORT ENDED");
        }
        return robots;
    }

    public void exportRobots(String filePath, Room room, Logger logger){
        List<Robot> robots = room.getRobots();
        try {
            FileWriter writer = new FileWriter(filePath, true);
            BufferedWriter buffer = new BufferedWriter(writer);
            logger.log(System.Logger.Level.INFO, "BEGINNING ROBOT EXPORT");
            for (Obstacle robot : robots) {
                logger.log(System.Logger.Level.INFO, robot.getParams());
                buffer.write(robot.getParams());
                buffer.newLine();
            }
            buffer.close();
        } catch (Exception e){
            logger.log(System.Logger.Level.ERROR, e.getMessage());
        }
        finally {
            logger.log(System.Logger.Level.INFO, "ROBOT EXPORT ENDED");
        }
    }
}
