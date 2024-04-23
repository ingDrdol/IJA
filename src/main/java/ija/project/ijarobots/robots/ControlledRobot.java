package ija.project.ijarobots.robots;


import ija.project.ijarobots.common.Area;
import ija.project.ijarobots.common.Obstacle;
import ija.project.ijarobots.common.Position;
import ija.project.ijarobots.common.Robot;

public class ControlledRobot implements Robot {

    Area ar;
    private int row = 0;
    private int col = 0;
    private int dirX = 0;
    private int dirY = 0;

    public ControlledRobot(int r, int c, Area a){
        this.row = r;
        this.col = c;
        this.ar = a;
    }
    public ControlledRobot(Area a){
        this.ar = a;
    }
    @Override
    public boolean containsPosition(Position p) {
        return false;//no hitbox for now
    }

    @Override
    public boolean colision(Obstacle o) {
        return o.containsPosition(new Position(this.row, this.col));
    }

    private boolean canMove(){
        Position newPos = new Position(this.row+this.dirX, this.col+this.dirY);
        return ar.obstacleAt(newPos);
    }
    @Override
    public boolean move() {
        if(this.canMove()) {
            this.row += this.dirX;
            this.col += this.dirY;
            return true;
        }
        return false;
    }

    public void changeDirX(int x){
        this.dirX = x;
    }

    public void changeDirY(int y){
        this.dirY = y;
    }

    @Override
    public boolean turn() {
        return false;
    }

    @Override
    public Position getPosition() {
        return new Position(this.row, this.col);
    }



    public void printPoition(){
        System.out.println("Robot at:" + this.row + " " + this.col);
    }
}
