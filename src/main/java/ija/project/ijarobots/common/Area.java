package ija.project.ijarobots.common;

public interface Area {
    boolean robotCollision(Robot r, Position p);
    void addRobot(Robot r);
    boolean obstacleAt(Position p);
    boolean containsPosition(Position p);
}
