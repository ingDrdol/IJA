package ija.project.ijarobots.common;

public interface Area {
    boolean addRobot(Robot r);
    boolean obstacleAt(Position p);
    boolean containsPosition(Position p);
}
