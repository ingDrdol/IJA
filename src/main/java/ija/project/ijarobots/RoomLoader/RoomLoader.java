package ija.project.ijarobots.RoomLoader;

import ija.project.ijarobots.common.Area;
import ija.project.ijarobots.RoomLoader.RobotLoader;
import ija.project.ijarobots.RoomLoader.ObstacleLoader;

import java.io.FileWriter;

public class RoomLoader {
    public RoomLoader(){

    }

    public void LoadRoomSetup(String filePath, Area area){
        RobotLoader robotloader = new RobotLoader();
        ObstacleLoader obstacleLoader = new ObstacleLoader();

        robotloader.loadRobots(filePath, area);
        obstacleLoader.loadObstacles(filePath);
    }

    public void ExportRoomSetup(String filePath, Area area){
        RobotLoader robotloader = new RobotLoader();
        ObstacleLoader obstacleLoader = new ObstacleLoader();

        try {
            new FileWriter(filePath, false).close();
            //robotloader.exportRobots(filePath, area);
            //obstacleLoader.exportObstacles(filePath, area);
        } catch (Exception e){

        }
    }
}
