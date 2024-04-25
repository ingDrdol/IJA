package ija.project.ijarobots.common;

import javafx.scene.shape.Shape;

public interface Robot extends Obstacle{
    boolean move();
    void turn(int degrees);
    Position getPosition();
    int getRadius();
    int getAngle();
    void stop();
    Shape getShape();
    public String getParams();
}
