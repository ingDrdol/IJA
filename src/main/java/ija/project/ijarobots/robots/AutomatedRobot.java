package ija.project.ijarobots.robots;

import javafx.scene.shape.Shape;

public class AutomatedRobot extends BaseRobot{
    public AutomatedRobot(int r, int c, int size) {
        super(r, c, size);
    }

    @Override
    public boolean move() {
        return false;
    }

    @Override
    public Shape getShape() {
        return null;
    }
}
