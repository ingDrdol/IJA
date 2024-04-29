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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    ArrayList<Character> keys = new ArrayList<>();
    boolean reverse = false;
    @FXML
    AnchorPane robotPlayground;
    @FXML
    GridPane frame;
    ControlledRobot playerModel;
    Room room;
    @FXML
    TextField xCord, yCord, size, fileName;
    @FXML
    ComboBox<String> addingOption;

    Timeline simulation = new Timeline(new KeyFrame(Duration.seconds(1.0 / 20), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (reverse){
                reverse();
                return;
            }
            if (playerModel != null){
                for (var key : keys) {
                    switch (key) {
                        case 'w' -> playerModel.speedUp();
                        case 's' -> playerModel.slowDown();
                        case 'a' -> playerModel.turn((int) (3 * playerModel.getSpeed()));
                        case 'd' -> playerModel.turn((int) (-3 * playerModel.getSpeed()));
                    }
                }
            }

            room.moveRobots();
            drawRobots();
        }
    }));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        room = new Room(robotPlayground);
        playerModel = new ControlledRobot(room.getRows()/2, room.getCols()/2, 20, room);
        room.addRobot(playerModel);
        drawRobots();
        keyListenerSetUp();
        simulation.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    public void play(){
        simulation.play();
    }
    @FXML
    public void pause(){
        simulation.pause();
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

    public void importRoom() throws Exception {
        String filePath = "data/" + fileName.toString();
        File file = new File(filePath);
        if (!file.exists()){
            throw new Exception("File " + fileName.toString() + " not found");
        }
        ObstacleLoader obstacleloader = new ObstacleLoader();
        RobotLoader robotloader = new RobotLoader();

        room.addObstacle(obstacleloader.loadObstacles(filePath));
        room.addRobot(robotloader.loadRobots(filePath, room));
        room.removeRobot(playerModel);
        playerModel = room.getPlayer();
    }

    public void exportRoom(){
        String filePath = "data/" + fileName.toString();
        ObstacleLoader obstacleloader = new ObstacleLoader();
        RobotLoader robotloader = new RobotLoader();
        try {
            new FileWriter(filePath, false).close();
            robotloader.exportRobots(filePath, room);
            obstacleloader.exportObstacles(filePath, room);
        } catch (Exception e) {}
    }

    public void addItem(){
        int x = Integer.parseInt(xCord.getText());
        int y = Integer.parseInt(yCord.getText());
        int s = Integer.parseInt(size.getText());
        switch (addingOption.toString()){
            case "Robot":
                AutomatedRobot newRobot = new AutomatedRobot(x, y, s, room);
                room.addRobot(newRobot);
                break;
            case "Square":
                Square newSquare = new Square(x, y, s);
                room.addObstacle(newSquare);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + addingOption.toString());
        }

    }
}