package ija.project.ijarobots.robots;

import ija.project.ijarobots.common.Position;
import ija.project.ijarobots.common.Robot;
import javafx.scene.shape.Shape;

public abstract class BaseRobot implements Robot {
    protected int angle = 0, radius;
    protected double row, col;
    protected double dirX = 0, dirY = 0, speed = 1;
    protected BaseRobot(int r, int c, int size){
        this.row = r;
        this.col = c;
        this.radius = size;
    }
    @Override
    public boolean colision(Robot r, Position p) {
        return !(this.getPosition().distance(p) > r.getRadius() + this.getRadius());
    }

    @Override
    public void turn(int degrees) {
        this.angle += degrees;
    }

    @Override
    public int getRadius(){ return this.radius;}

    @Override
    public Position getPosition() {
        return new Position(this.row, this.col);
    }

    @Override
    public void stop(){
        this.dirX = 0;
        this.dirY = 0;
    }

    @Override
    public boolean containsPosition(Position p) {
        return p.distance(this.getPosition()) <= this.radius;
    }

    @Override
    public int getAngle() {return this.angle;}

    public void changeDirX(int x){
        this.dirX = this.dirX + x;
    }

    public void changeDirY(int y){ this.dirY = this.dirY + y; }

    public void getDirection(){
        double sin = Math.sin(Math.toRadians(this.angle));
        double cos = Math.cos(Math.toRadians(this.angle));
        this.dirY = this.speed*cos;
        this.dirX = this.speed*sin;
    }

    public double getSpeed(){
        return this.speed;
    }
}
