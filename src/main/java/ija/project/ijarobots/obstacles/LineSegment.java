package ija.project.ijarobots.obstacles;

import ija.project.ijarobots.common.Obstacle;
import ija.project.ijarobots.common.Position;
import ija.project.ijarobots.common.Robot;

public class LineSegment implements Obstacle {
    private final Position start, end;
    public LineSegment(Position start, Position end) {
        this.start = start;
        this.end = end;
    }
    @Override
    public boolean containsPosition(Position p) {
        return false;
    }

    @Override
    public boolean colision(Robot r) {
        return false;
    }
}
