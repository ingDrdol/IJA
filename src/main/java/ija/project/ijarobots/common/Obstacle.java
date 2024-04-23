package ija.project.ijarobots.common;

public interface Obstacle {
    boolean containsPosition(Position p);
    boolean colision(Obstacle o);
}
