package ija.project.ijarobots.obstacles;

import ija.project.ijarobots.common.Obstacle;
import ija.project.ijarobots.common.Position;

public class Square implements Obstacle {
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
    public boolean colision(Obstacle o) {
        return false;
    }
}
