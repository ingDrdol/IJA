package ija.project.ijarobots.RoomLoader;

import ija.project.ijarobots.common.Area;
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

    public List<BaseRobot> loadRobots(String filePath, Area area){
        List<List<String>> Atributes = new ArrayList<>();
        List<BaseRobot> robots = new ArrayList<BaseRobot>();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = buffer.readLine()) != null){
                String[] values = line.split(",");
                Atributes.add(Arrays.asList(values));
            }
        } catch (Exception e){
        }
        for (List<String> robot : Atributes) {
            String type = robot.get(0);
            if (type.equals("P")) {
                int cordX = Integer.parseInt(robot.get(1));
                int cordY = Integer.parseInt(robot.get(2));
                int size = Integer.parseInt(robot.get(3));
                robots.add(new ControlledRobot(cordX, cordY, size, area));
            } else if (type.equals("R")) {
                int cordX = Integer.parseInt(robot.get(1));
                int cordY = Integer.parseInt(robot.get(2));
                int size = Integer.parseInt(robot.get(3));
                robots.add(new AutomatedRobot(cordX, cordY, size));
            }
        }
        return robots;
    }

    public void exportRobots(String filePath, Room room){
        List<Robot> robots = room.getRobots();
        try {
            FileWriter writer = new FileWriter(filePath, true);
            BufferedWriter buffer = new BufferedWriter(writer);
            for (Obstacle robot : robots) {
                buffer.write(robot.getParams());
                buffer.newLine();
            }
            buffer.close();
        } catch (Exception e){

        }
    }
}
