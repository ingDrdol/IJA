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

    public String getParams(){
        double x = this.row;
        double y = this.col;
        int r = this.radius;
        return "R" + "," + String.valueOf(x) + "," +  String.valueOf(y) + "," +  String.valueOf(r);
    }
}
