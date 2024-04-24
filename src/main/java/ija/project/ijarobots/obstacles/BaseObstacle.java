package ija.project.ijarobots.obstacles;

import ija.project.ijarobots.common.Obstacle;
import ija.project.ijarobots.common.Robot;

import java.util.ArrayList;

public abstract class BaseObstacle implements Obstacle {
    protected abstract ArrayList<LineSegment> getLines();

    @Override
    public boolean colision(Robot r){
        if(this.containsPosition(r.getPosition())){
            return true;
        }
        for(LineSegment line : getLines()){
            if(line.colision(r))
                return true;
        }
        return false;
    }
}
