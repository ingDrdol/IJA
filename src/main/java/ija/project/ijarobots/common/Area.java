package ija.project.ijarobots.common;

public interface Area {
    boolean robotCollision(Robot r, Position p);
    boolean addRobot(Robot r);
    boolean obstacleAt(Position p);
    boolean containsPosition(Position p);
}
