package ija.project.ijarobots.obstacles;

import ija.project.ijarobots.common.Obstacle;
import ija.project.ijarobots.common.Position;
import ija.project.ijarobots.common.Robot;
import javafx.geometry.Pos;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Square extends BaseObstacle{
    private int x;
    private int y;
    private int a;

    public Square(int xCor, int yCor, int side){
        this.x = xCor;
        this.y = yCor;
        this.a = side;
    }
    @Override
    public boolean containsPosition(Position p) {
        int row = p.getRow();
        int col = p.getCol();
        if (row > x && row < x+a){
            return col > y && col < y + a;
        }
        return false;
    }

    @Override
    public Shape getShape() {
        return null;
    }

    @Override
    protected ArrayList<LineSegment> getLines() {
        ArrayList<LineSegment> lines = new ArrayList<>();
        Position A = new Position(x,y);
        Position B = new Position(x+a,y);
        Position C = new Position(x+a,y+a);
        Position D = new Position(x,y+a);

        lines.add(new LineSegment(A, B));
        lines.add(new LineSegment(B, C));
        lines.add(new LineSegment(C, D));
        lines.add(new LineSegment(D, A));
        return lines;
    }
}
