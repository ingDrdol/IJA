package ija.project.ijarobots.common;

public class Position {
    private final double row;
    private final double col;

    public Position(double r, double c){
        this.row = r;
        this.col = c;
    }

    public double getRow(){ return this.row; }
    public double getCol(){ return this.col; }
    public double distance(Position p){
        double xSide = Math.abs(p.row - this.row);
        double ySide = Math.abs(p.col - this.col);
        return Math.sqrt(xSide * xSide + ySide * ySide);
    }
}
