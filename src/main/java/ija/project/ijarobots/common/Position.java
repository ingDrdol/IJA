package ija.project.ijarobots.common;

public class Position {
    private final int row;
    private final int col;

    public Position(int r, int c){
        this.row = r;
        this.col = c;
    }

    public int getRow(){ return this.row; }
    public int getCol(){ return this.col; }
    public double distance(Position p){
        double xSide = Math.abs(p.row - this.row);
        double ySide = Math.abs(p.col - this.col);
        return Math.sqrt(xSide * xSide + ySide * ySide);
    }
}
