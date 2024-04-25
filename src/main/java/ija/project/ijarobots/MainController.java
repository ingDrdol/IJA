package ija.project.ijarobots;

import ija.project.ijarobots.RoomLoader.ObstacleLoader;
import ija.project.ijarobots.RoomLoader.RobotLoader;
import ija.project.ijarobots.RoomLoader.RoomLoader;
import ija.project.ijarobots.common.Obstacle;
import ija.project.ijarobots.common.Position;
import ija.project.ijarobots.common.Robot;
import ija.project.ijarobots.obstacles.Square;
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
    @FXML
    AnchorPane robotPlayground;
    @FXML
    Label label;
    @FXML
    Circle player;
    @FXML
    GridPane frame;
    ControlledRobot playerModel;
    Room room;
    public List<Obstacle> obstacles = new ArrayList<Obstacle>();
    public List<BaseRobot> robots = new ArrayList<BaseRobot>();

    Timeline simulation = new Timeline(new KeyFrame(Duration.seconds(1.0 / 20), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            for (var key: keys){
                switch (key){
                    case 'w' -> playerModel.speedUp();
                    case 's' -> playerModel.slowDown();
                    case 'a' -> playerModel.turn((int)(3 * playerModel.getSpeed()));
                    case 'd' -> playerModel.turn((int)(-3 * playerModel.getSpeed()));
                }
            }
            playerModel.move();
            playerModel.stop();
            Position p = playerModel.getPosition();
            player.setLayoutX(p.getRow());
            player.setLayoutY(p.getCol());
            player.setRotate(playerModel.getAngle()*(-1) + (double)90);
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
        obstacles = obstacleloader.loadObstacles(filePath);
        for (Obstacle obstacle : obstacles){
            room.addObstacle(obstacle);
        }

        /**
        RobotLoader robotloader = new RobotLoader();
        robots = robotloader.loadRobots(filePath, this.room);
        */
        playerModel = new ControlledRobot(50, 50, 50, room);
        Image skin = new Image("file:data/PlayerRobotSkin.jpg");
        player.setFill(new ImagePattern(skin));
        player.setRadius(playerModel.getRadius());

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
}