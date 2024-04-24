package ija.project.ijarobots.room;



import ija.project.ijarobots.common.Area;
import ija.project.ijarobots.common.Obstacle;
import ija.project.ijarobots.common.Position;
import ija.project.ijarobots.common.Robot;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class Room implements Area {
    AnchorPane anchor;

    private ArrayList<Obstacle> items;
    public Room(AnchorPane anchor){
        this.anchor = anchor;
        this.items = new ArrayList<>();
    }
    @Override
    public boolean addRobot(Robot r) {
        return false;
    }

    public void addObstacle(Obstacle o){
        items.add(o);
        this.anchor.getChildren().add(o.getShape());
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
                || item.colision(r)) {
                return true;
            }
        }
        if (p.getCol() - r.getRadius() < 0 || p.getRow() - r.getRadius() < 0)
            return true;

        return p.getRow() + r.getRadius() > this.getCols()
                || p.getCol() + r.getRadius() > this.getRows();
    }
}
