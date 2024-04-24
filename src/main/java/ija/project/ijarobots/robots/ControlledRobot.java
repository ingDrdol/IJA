package ija.project.ijarobots.robots;


import ija.project.ijarobots.common.Area;
import ija.project.ijarobots.common.Obstacle;
import ija.project.ijarobots.common.Position;
import ija.project.ijarobots.common.Robot;
import javafx.geometry.Pos;
import javafx.scene.shape.Shape;

public class ControlledRobot implements Robot {

    Area ar;
    private int row = 0;
    private int col = 0;
    private int dirX = 0;
    private int dirY = 0;
    private int radius = 0;
    private int speed = 5;

    public ControlledRobot(int r, int c, int size, Area a){
        this.row = r;
        this.col = c;
        this.radius = size;
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
    public boolean colision(Robot r, Position p) {
        return r.containsPosition(new Position(this.row, this.col));
    }

    @Override
    public Shape getShape() {
        return null;
    }

    private boolean canMove(Position dest){
        return !ar.robotCollision(this, dest);
    }

    @Override
    public boolean move() {
        for(int i = 0; i < this.speed; i++) {
            Position dest = new Position(this.row + this.dirX, this.col + this.dirY);
            if (!this.canMove(dest))
                return false;
            this.row += this.dirX;
            this.col += this.dirY;
        }
        return true;
    }

    public void changeDirX(int x){
        this.dirX = this.dirX + x;
    }

    public void changeDirY(int y){ this.dirY = this.dirY + y; }

    @Override
    public boolean turn() {
        return false;
    }

    @Override
    public Position getPosition() {
        return new Position(this.row, this.col);
    }

    @Override
    public int getRadius(){ return this.radius;}

    @Override
    public void stop(){
        this.dirX = 0;
        this.dirY = 0;
    }
    public void printPoition(){
        System.out.println("Robot at:" + this.row + " " + this.col);
    }
}
