package ija.project.ijarobots;

import ija.project.ijarobots.common.Position;
import ija.project.ijarobots.obstacles.Square;
import ija.project.ijarobots.robots.ControlledRobot;
import ija.project.ijarobots.room.Room;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
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

    Timeline simulation = new Timeline(new KeyFrame(Duration.seconds(1.0 / 20), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            for (var key: keys){
                switch (key){
                    case 'w' -> playerModel.changeDirY(-5);
                    case 's' -> playerModel.changeDirY(5);
                    case 'a' -> playerModel.changeDirX(-5);
                    case 'd' -> playerModel.changeDirX(5);
                }
            }
            playerModel.move();
            playerModel.stop();
            Position p = playerModel.getPosition();
            player.setLayoutX(p.getRow());
            player.setLayoutY(p.getCol());
            label.setText(String.format("rows: %d, cols: %d| %d, %d",room.getRows(), room.getCols(), p.getRow(), p.getCol()));
        }
    }));

    @FXML
    public void changeBackground(){
        label.setText(String.format("rows: %d, cols: %d", room.getRows(), room.getCols()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        room = new Room(robotPlayground);
        playerModel = new ControlledRobot(50, 50, 20, room);
        Square obstacle = new Square(90, 90, 30);
        room.addObstacle(obstacle);
        player.setFill(Color.BLUE);
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