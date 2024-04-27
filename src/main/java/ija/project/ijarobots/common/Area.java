package ija.project.ijarobots.common;

import java.util.List;

public interface Area {
    boolean robotCollision(Robot r, Position p);
    void addRobot(Robot r);
    boolean obstacleAt(Position p);
    boolean containsPosition(Position p);
    List<Obstacle> getObstacles();
    List<Robot> getRobots();
}
