package ija.project.ijarobots.robots;

import ija.project.ijarobots.common.Area;
import ija.project.ijarobots.common.Position;
import ija.project.ijarobots.common.Robot;
import javafx.geometry.Pos;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public abstract class BaseRobot implements Robot {
    protected int angle = 0, radius;
    protected double row, col;
    protected double dirX = 0, dirY = 0, speed = 1;

    private final Area ar;
    private final ArrayList<HistoryRecord> history;
    protected boolean canMove = true;

    protected BaseRobot(int r, int c, int size, Area a){
        this.row = r;
        this.col = c;
        this.radius = size;
        this.ar = a;

        this.history = new ArrayList<>();
        this.history.add(new HistoryRecord(new Position(r, c), this.angle, this.speed));
    }
    @Override
    public boolean colision(Robot r, Position p) {
        return !(this.getPosition().distance(p) > r.getRadius() + this.getRadius());
    }

    @Override
    public void setMoving(boolean move){
        this.canMove = move;
    }

    @Override
    public boolean canMove(Position dest){
        return !ar.robotCollision(this, dest);
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

    public void getDirection(){
        double sin = Math.sin(Math.toRadians(this.angle));
        double cos = Math.cos(Math.toRadians(this.angle));
        this.dirY = this.speed*cos;
        this.dirX = this.speed*sin;
    }

    public double getSpeed(){
        return this.speed;
    }

    public void savePosition(){
        this.history.add(new HistoryRecord(new Position(this.row, this.col), this.angle, this.speed));
    }

    public void reverseMove(){
        if (!this.history.isEmpty()){
            HistoryRecord prev = this.history.get(this.history.size() - 1);
            this.history.remove(this.history.size() - 1);
            this.col = prev.p().getCol();
            this.row = prev.p().getRow();
            this.speed = prev.speed();
            this.angle = prev.angle();
        }
    }

}

record HistoryRecord (
        Position p,
        int angle,
        double speed
){}
