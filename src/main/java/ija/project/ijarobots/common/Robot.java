package ija.project.ijarobots.common;

public interface Robot extends Obstacle{
    boolean move();
    void turn(int degrees);
    Position getPosition();
    int getRadius();
    int getAngle();
    void reverseMove();
    int getDetectRadius();
    boolean canMove(Position position);
}
