package ija.project.ijarobots.RoomLoader;

import ija.project.ijarobots.common.Area;
import ija.project.ijarobots.RoomLoader.RobotLoader;
import ija.project.ijarobots.RoomLoader.ObstacleLoader;

import java.io.FileWriter;

public class RoomLoader {
    private String filePath = "data/roomSetup.csv";

    public RoomLoader(){

    }

    public void LoadRoomSetup(Area area){
        RobotLoader robotloader = new RobotLoader();
        ObstacleLoader obstacleLoader = new ObstacleLoader();

        robotloader.loadRobots(this.filePath, area);
        obstacleLoader.loadObstacles(this.filePath);
    }

    public void ExportRoomSetup(Area area){
        RobotLoader robotloader = new RobotLoader();
        ObstacleLoader obstacleLoader = new ObstacleLoader();

        try {
            new FileWriter(this.filePath, false).close();
            robotloader.exportRobots(this.filePath, area);
            obstacleLoader.exportObstacles(this.filePath, area);
        } catch (Exception e){

        }
    }
}
