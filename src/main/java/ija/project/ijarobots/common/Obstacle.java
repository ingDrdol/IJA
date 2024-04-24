package ija.project.ijarobots.common;

import javafx.scene.shape.Shape;

public interface Obstacle {
    boolean containsPosition(Position p);
    boolean colision(Robot r, Position p);
    Shape getShape();
}
