package ija.project.ijarobots.obstacles;

import ija.project.ijarobots.common.Obstacle;
import ija.project.ijarobots.common.Position;
import ija.project.ijarobots.common.Robot;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public abstract class BaseObstacle implements Obstacle {
    protected abstract ArrayList<LineSegment> getLines();

    @Override
    public boolean colision(Robot r, Position p){
        ArrayList<LineSegment> lines = getLines();
        for (LineSegment line : lines) {
            if(line.colision(r, p))
                return true;
        }
        return false;
    }
}
