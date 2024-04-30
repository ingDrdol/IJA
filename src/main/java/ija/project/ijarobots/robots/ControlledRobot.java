package ija.project.ijarobots.robots;


import ija.project.ijarobots.common.*;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import static java.lang.Math.sqrt;

public class ControlledRobot extends BaseRobot {
    private Circle shape;
    public ControlledRobot(int r, int c, int size, Area a){
        super(r, c, size, a);
        this.shape = new Circle(0, 0, size);
        this.radius = size;
        Image skin = new Image("file:data/playerRobotSkin.jpg");
        this.shape.setFill(new ImagePattern(skin));
    }

    public ControlledRobot(int r, int c, int size, Area a, int angle){
        this(r, c, size, a);
        this.angle = angle;
    }

    @Override
    public Shape getShape() {
        return this.shape;
    }

    @Override
    public boolean move() {
        for(int i = 0; i < this.speed; i++) {
            this.getDirection();
            Position dest = new Position(this.row + this.dirX, this.col + this.dirY);
            if (!this.canMove(dest))
                return false;
            this.savePosition();
            this.row += this.dirX;
            this.col += this.dirY;
        }
        return true;
    }

    @Override
    public int getDetectRadius() {
        return this.radius;
    }

    public void speedUp(){
        if (this.speed < 4)
            this.speed += 0.1;
    }

    public void slowDown(){
        if (this.speed > 0)
            this.speed -= 0.1;
    }

    @Override
    public String getParams(){
        double x = this.row;
        double y = this.col;
        int r = this.radius;
        int ang = this.angle;
        return "P" + "," + String.valueOf((int)x) + "," +  String.valueOf((int)y) + "," +  String.valueOf((int)r) + "," +  String.valueOf((int)r);
    }

    @Override
    public boolean colision(Robot r, Position p) {

        if(super.colision(r, p)){
            double x = p.getRow() - this.row;
            double y = p.getCol() - this.col;
            double incomingAngle = y < 0 ? (angleFromDirection(x, y) + 180)%360 : angleFromDirection(x, y);
            double collisionAngle = ((incomingAngle + 180) % 360 - r.getAngle());
            Logger.getLogger().log(System.Logger.Level.INFO, r.hashCode() + " col from: " +collisionAngle + " cur angle: " + r.getAngle());
            if(collisionAngle < 180 && collisionAngle > 0){
                return true;
            }
            return super.hardColision(r, p);

        }
        return false;
    }

    private double angleFromDirection(double x, double y){
        double cos = x/sqrt(x*x+y*y);
        return Math.toDegrees(Math.acos(cos));
    }
}
