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

public class LineSegment implements Obstacle {
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
    public boolean colision(Robot r, Position p) {
        int radius = r.getRadius();
        double pointD = this.pointDistance(p);
        if (pointD < radius){
            double side = Math.max(start.distance(p), end.distance(p));
            double actualDist = Math.sqrt(side*side - pointD*pointD);
            if(actualDist > start.distance(end)){
                return start.distance(p) < radius
                        || end.distance(p) < radius;
            }
            else{
                return true;
            }
        }
        return false;
    }

    private double pointDistance(Position p){
        double a = p.distance(start);
        double b = p.distance(end);
        double c = start.distance(end);
        double s = (a + b + c)/2;
        double area = Math.sqrt(s*(s - a)*(s - b)*(s - c));

        return 2*area/c;
    }
    @Override
    public Shape getShape() {
        return new Line(start.getRow(), start.getCol(), end.getRow(), end.getCol());
    }

    @Override
    public String getParams() {
        return null;
    }

}
