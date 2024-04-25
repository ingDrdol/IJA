package ija.project.ijarobots.room;



import ija.project.ijarobots.common.Area;
import ija.project.ijarobots.common.Obstacle;
import ija.project.ijarobots.common.Position;
import ija.project.ijarobots.common.Robot;
import ija.project.ijarobots.robots.BaseRobot;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Room implements Area {
    AnchorPane anchor;

    private ArrayList<Obstacle> items;
    private ArrayList<Robot> robots;
    public Room(AnchorPane anchor){
        this.anchor = anchor;
        this.items = new ArrayList<>();
        this.robots = new ArrayList<>();
    }
    @Override
    public void addRobot(Robot r) {
        robots.add(r);
        anchor.getChildren().add(r.getShape());
    }

    public void addObstacle(Obstacle o){
        items.add(o);
        anchor.getChildren().add(o.getShape());
    }

    @Override
    public boolean obstacleAt(Position p) {
        if (this.containsPosition(p)){
            for(Obstacle item : items){
                if(item.containsPosition(p))
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean containsPosition(Position p) {
        if (p.getCol() < 0 || p. getRow() < 0)
            return false;
        return p.getCol() <= this.getCols() && p.getRow() <= this.getRows();
    }

    @Override
    public List<Obstacle> getObstacles() {
        return items;
    }

    @Override
    public List<Robot> getRobots() {
        return robots;
    }

    public int getRows(){
        return (int)this.anchor.getHeight();
    }

    public int getCols(){
        return (int)this.anchor.getWidth();
    }

    @Override
    public boolean robotCollision(Robot r, Position p){
        for(Obstacle item : items){
            if (item.containsPosition(r.getPosition())
                || item.colision(r, p)) {
                return true;
            }
        }

        for(Robot robot : robots){
            if (robot.hashCode() != r.hashCode()){
                if(robot.containsPosition(r.getPosition())
                        || robot.colision(r, p)) {
                    return true;
                }
            }
        }

        if (p.getCol() - r.getRadius() < 0 || p.getRow() - r.getRadius() < 0)
            return true;

        return p.getRow() + r.getRadius() > this.getCols()
                || p.getCol() + r.getRadius() > this.getRows();
    }

    public void moveRobots(){
        for(Robot robot : robots) {
            robot.move();

            Shape robotShape = robot.getShape();
            Position p = robot.getPosition();
            robotShape.setLayoutX(p.getRow());
            robotShape.setLayoutY(p.getCol());
            robotShape.setRotate(robot.getAngle()*(-1) + (double)180);
        }
    }
}
