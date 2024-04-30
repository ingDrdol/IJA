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

/**
 * Class that services FXML objects
 * @author xkocma09 (xkocma09@stud.fit.vutbr.cz)
 * @author xvelic05 (xvelic05@stud.fit.vutbr.cz)
 */
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
    Button reverseButton, addButton, importButton;

    /**
     * TODO
     */
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

    /**
     * Initializes room with controllable robot, sets keyListener and simulation.
     * Prepares application for start.
     *
     * @param url               TODO
     * @param resourceBundle    TODO
     * @see #keyListenerSetUp()
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        room = new Room(robotPlayground);
        playerModel = new ControlledRobot(500, 300, 20, room);
        room.addRobot(playerModel);
        drawRobots();
        keyListenerSetUp();
        simulation.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Checks all robots to permanent collisions due to wrong room setup,
     * plays the game and disables all initializing GUI options.
     * Called when Play button is pressed.
     */
    @FXML
    public void play(){
        addButton.setDisable(true);
        xCord.setDisable(true);
        yCord.setDisable(true);
        size.setDisable(true);
        addingOption.setDisable(true);
        importButton.setDisable(true);
        this.room.initCollisions();
        simulation.play();
    }

    /**
     * Pauses the game.
     * Called when Pause button is pressed.
     */
    @FXML
    public void pause(){
        simulation.pause();
    }

    /**
     * Plays reversed record of the game.
     * Called when Reverse button is pressed, canceled when Stop button is pressed.
     * Game continuous normally after Stopping reversed play.
     */
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

    /**
     * Sets up key listener, that reacts to selected keyboard keys which have assigned functionality.
     * When key is pressed, its symbol is added to list of pressed keys.
     * When key is released, its symbol is removed from the list.
     *
     * @see #keys
     */
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

    /**
     * Method that is used by key listener to add character corresponding to key to the list
     * of pressed keys.
     *
     * @param c     Character to be added to list
     *
     * @see #keyListenerSetUp()
     */
    private void addSingle(Character c){
        if (!keys.contains(c))
            keys.add(c);
    }

    /**
     * Draws all robots to according positions on screen.
     */
    private void drawRobots(){
        for(Robot robot : room.getRobots()){
            Position p = robot.getPosition();
            robot.getShape().setLayoutX(p.getRow());
            robot.getShape().setLayoutY(p.getCol());
            robot.getShape().setRotate(robot.getAngle()*(-1) + (double)180);
        }
    }

    /**
     * Reverses direction of movement of all robots
     */
    private void reverseMoves(){
        for (Robot robot : room.getRobots()){
            robot.reverseMove();
        }
        drawRobots();
    }

    /**
     * Imports robots and obstacles setup from *.csv file.
     * Takes files from Data folder.
     * Called after writing filename to according text field and pressing button Import.
     */
    @FXML
    public void importRoom(){
        String filePath = "data/" + fileName.getText();
        File file = new File(filePath);
        if (!file.exists()){
            Logger.getLogger().log(System.Logger.Level.WARNING, "File " + fileName.getText() + " not found");
        }
        ObstacleLoader obstacleloader = new ObstacleLoader();
        RobotLoader robotloader = new RobotLoader();

        room.addObstacle(obstacleloader.loadObstacles(filePath));
        room.addRobot(robotloader.loadRobots(filePath, room));
        room.removeRobot(playerModel);
        playerModel = room.getPlayer();
        drawRobots();
    }

    /**
     * Exports current position of all robots and obstacles to *.csv file as room setup.
     * Stores files to Data folder.
     * Called after writing filename to according text field and pressing button Export.
     */
    @FXML
    public void exportRoom(){
        String filePath = "data/" + fileName.getText();
        ObstacleLoader obstacleloader = new ObstacleLoader();
        RobotLoader robotloader = new RobotLoader();
        try {
            new FileWriter(filePath, false).close();
            robotloader.exportRobots(filePath, room);
            obstacleloader.exportObstacles(filePath, room);
        } catch (Exception e) {
            Logger.getLogger().log(System.Logger.Level.WARNING, e.getMessage());
        }
    }

    /**
     * Takes coordinates and size entered to according text fields, type of adding object from
     * dropbox and adds robot or obstacle to according position on play field.
     */
    @FXML
    public void addItem(){
        int x = Integer.parseInt(xCord.getText());
        int y = Integer.parseInt(yCord.getText());
        int s = Integer.parseInt(size.getText());
        switch (addingOption.getValue()){
            case "Robot":
                Logger.getLogger().log(System.Logger.Level.INFO, "ROBOT OF SIZE " + s +
                " ADDED TO POSITION " + x + " " + y);
                AutomatedRobot newRobot = new AutomatedRobot(x, y, s, room);
                room.addRobot(newRobot);
                drawRobots();
                break;
            case "Obstacle":
                Logger.getLogger().log(System.Logger.Level.INFO, "OBSTACLE OF SIZE " + s +
                " ADDED TO POSITION " + x + " " + y);
                Square newSquare = new Square(x, y, s);
                room.addObstacle(newSquare);
                break;
            default:
                Logger.getLogger().log(System.Logger.Level.WARNING, "Unexpected value: " + addingOption.toString());
        }

    }
}