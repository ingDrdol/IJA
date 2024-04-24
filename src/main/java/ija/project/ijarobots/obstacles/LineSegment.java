package ija.project.ijarobots.obstacles;

import ija.project.ijarobots.common.Obstacle;
import ija.project.ijarobots.common.Position;
import ija.project.ijarobots.common.Robot;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Vector;

import static java.lang.Math.abs;

public class LineSegment extends BaseObstacle {
    private final Position start, end;
    private final double A, B, C;
    public LineSegment(Position start, Position end) {
        this.start = start;
        this.end = end;
        this.A = end.getRow() - start.getRow();
        this.B = end.getCol() - start.getCol();
        this.C = getCConst(start);
    }

    private double getCConst(Position start) {
        return (this.A * start.getRow() + this.B * start.getCol()) * (-1);
    }

    @Override
    public boolean containsPosition(Position p) {
        double distanceStart = start.distance(p);
        double distanceEnd = end.distance(p);
        return distanceStart + distanceEnd == abs(start.distance(p));
    }

    @Override
    protected ArrayList<LineSegment> getLines() {
        ArrayList<LineSegment> line = new ArrayList<>();
        line.add(this);
        return line;
    }

    @Override
    public Shape getShape() {
        return new Line(start.getRow(), start.getCol(), end.getRow(), end.getCol());
    }
}
