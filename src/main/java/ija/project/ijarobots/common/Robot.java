package ija.project.ijarobots.common;

public interface Robot extends Obstacle{
    boolean move();
    boolean turn();
    Position getPosition();
    int getRadius();
}
