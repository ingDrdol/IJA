package ija.project.ijarobots;

import ija.project.ijarobots.RoomLoader.ObstacleLoader;
import ija.project.ijarobots.RoomLoader.RobotLoader;
import ija.project.ijarobots.common.Obstacle;
import ija.project.ijarobots.common.Position;
import ija.project.ijarobots.common.Robot;
import ija.project.ijarobots.obstacles.Square;
import ija.project.ijarobots.robots.AutomatedRobot;
import ija.project.ijarobots.robots.BaseRobot;
import ija.project.ijarobots.robots.ControlledRobot;
import ija.project.ijarobots.room.Room;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    ArrayList<Character> keys = new ArrayList<>();
    Position playerPos;
    boolean reverse = false;
    @FXML
    AnchorPane robotPlayground;
    @FXML
    Label label;
    @FXML
    GridPane frame;
    ControlledRobot playerModel;
    AutomatedRobot airobot;
    Room room;
    public List<Obstacle> obstacles = new ArrayList<>();
    public List<BaseRobot> robots = new ArrayList<>();

    Timeline simulation = new Timeline(new KeyFrame(Duration.seconds(1.0 / 20), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (reverse){
                reverse();
                return;
            }
            for (var key: keys){
                switch (key){
                    case 'w' -> playerModel.speedUp();
                    case 's' -> playerModel.slowDown();
                    case 'a' -> playerModel.turn((int)(3 * playerModel.getSpeed()));
                    case 'd' -> playerModel.turn((int)(-3 * playerModel.getSpeed()));
                }
            }


            room.moveRobots();
            drawRobots();
            label.setText(String.format("speed: %f", playerModel.getSpeed()));
        }
    }));

    @FXML
    public void changeBackground(){
        label.setText(String.format("rows: %d, cols: %d", room.getRows(), room.getCols()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String filePath = "data/roomSetup.csv";
        room = new Room(robotPlayground);

        ObstacleLoader obstacleloader = new ObstacleLoader();
        RobotLoader robotloader = new RobotLoader();

        this.obstacles = obstacleloader.loadObstacles(filePath);
        for (Obstacle obstacle : obstacles){
            room.addObstacle(obstacle);
        }
        this.robots = robotloader.loadRobots(filePath, this.room);
        for (BaseRobot robot : robots){
            if (robot.getParams().charAt(0) == 'P'){
                playerModel = (ControlledRobot) robot;
            }
            room.addRobot(robot);
        }

        keyListenerSetUp();
        simulation.setCycleCount(Timeline.INDEFINITE);
        simulation.play();
    }

    private void keyListenerSetUp(){
        frame.setOnKeyPressed(key -> {
            switch(key.getCode()){
                case W -> addSingle('w');
                case S -> addSingle('s');
                case A -> addSingle('a');
                case D -> addSingle('d');
                case P -> simulation.pause();
                case R -> simulation.play();
                case Z -> reverse = !reverse;
            }
        });

        frame.setOnKeyReleased(key -> {
            switch(key.getCode()){
                case W -> keys.remove((Object)'w');
                case S -> keys.remove((Object)'s');
                case A -> keys.remove((Object)'a');
                case D -> keys.remove((Object)'d');
            }
        });
    }

    private void addSingle(Character c){
        if (!keys.contains(c))
            keys.add(c);
    }

    private void drawRobots(){
        for(Robot robot : room.getRobots()){
            Position p = robot.getPosition();
            robot.getShape().setLayoutX(p.getRow());
            robot.getShape().setLayoutY(p.getCol());
            robot.getShape().setRotate(robot.getAngle()*(-1) + (double)180);
        }
    }

    private void reverse(){
        for (Robot robot : room.getRobots()){
            robot.reverseMove();
        }
        drawRobots();
    }
}