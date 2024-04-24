package ija.project.ijarobots.obstacles;

import ija.project.ijarobots.common.Obstacle;
import ija.project.ijarobots.common.Position;
import ija.project.ijarobots.common.Robot;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public abstract class BaseObstacle implements Obstacle {
    protected abstract ArrayList<LineSegment> getLines();

    @Override
    public boolean colision(Robot r) {
        Position center = r.getPosition();
        Circle robot = new Circle();
        robot.setCenterX(center.getRow());
        robot.setCenterY(center.getCol());
        robot.setRadius(r.getRadius());

        return robot.intersects(this.getShape().getLayoutBounds());
    }
}
