package ija.project.ijarobots;

import ija.project.ijarobots.RoomLoader.ObstacleLoader;
import ija.project.ijarobots.RoomLoader.RobotLoader;
import ija.project.ijarobots.common.Logger;
import ija.project.ijarobots.common.Position;
import ija.project.ijarobots.common.Robot;
import ija.project.ijarobots.obstacles.Square;
import ija.project.ijarobots.robots.AutomatedRobot;
import ija.project.ijarobots.robots.ControlledRobot;
import ija.project.ijarobots.room.Room;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
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
    @FXML
    Label speedLabel;
    @FXML
    Button reverseButton, addButton;
    Logger logger = new Logger();

    Timeline simulation = new Timeline(new KeyFrame(Duration.seconds(1.0 / 20), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (reverse){
                reverseMoves();
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
            speedLabel.setText(String.format("Speed: %.1f", playerModel.getSpeed()));
        }
    }));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        room = new Room(robotPlayground);
        playerModel = new ControlledRobot(500, 300, 20, room);
        room.addRobot(playerModel);
        drawRobots();
        keyListenerSetUp();
        simulation.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    public void play(){
        addButton.setDisable(true);
        xCord.setDisable(true);
        yCord.setDisable(true);
        size.setDisable(true);
        addingOption.setDisable(true);
        simulation.play();
    }
    @FXML
    public void pause(){
        simulation.pause();
    }

    @FXML
    public void reverseAction(){
        reverse = !reverse;
        if (reverse){
            reverseButton.setText("Stop");
        }
        else{
            reverseButton.setText("Reverse");
        }
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

    private void reverseMoves(){
        for (Robot robot : room.getRobots()){
            robot.reverseMove();
        }
        drawRobots();
    }
    @FXML
    public void importRoom(){
        String filePath = "data/" + fileName.getText();
        File file = new File(filePath);
        if (!file.exists()){
            logger.log(System.Logger.Level.WARNING, "File " + fileName.getText() + " not found");
        }
        ObstacleLoader obstacleloader = new ObstacleLoader();
        RobotLoader robotloader = new RobotLoader();

        room.addObstacle(obstacleloader.loadObstacles(filePath));
        room.addRobot(robotloader.loadRobots(filePath, room, logger));
        room.removeRobot(playerModel);
        playerModel = room.getPlayer();
        drawRobots();
    }
    @FXML
    public void exportRoom(){
        String filePath = "data/" + fileName.getText();
        ObstacleLoader obstacleloader = new ObstacleLoader();
        RobotLoader robotloader = new RobotLoader();
        try {
            new FileWriter(filePath, false).close();
            robotloader.exportRobots(filePath, room, logger);
            obstacleloader.exportObstacles(filePath, room, logger);
        } catch (Exception e) {
            logger.log(System.Logger.Level.WARNING, e.getMessage());
        }
    }
    @FXML
    public void addItem(){
        int x = Integer.parseInt(xCord.getText());
        int y = Integer.parseInt(yCord.getText());
        int s = Integer.parseInt(size.getText());
        switch (addingOption.getValue()){
            case "Robot":
                AutomatedRobot newRobot = new AutomatedRobot(x, y, s, room);
                room.addRobot(newRobot);
                drawRobots();
                break;
            case "Obstacle":
                Square newSquare = new Square(x, y, s);
                room.addObstacle(newSquare);
                break;
            default:
                logger.log(System.Logger.Level.WARNING, "Unexpected value: " + addingOption.toString());
        }

    }
}