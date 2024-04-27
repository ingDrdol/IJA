package ija.project.ijarobots.robots;


import ija.project.ijarobots.common.Area;
import ija.project.ijarobots.common.Obstacle;
import ija.project.ijarobots.common.Position;
import ija.project.ijarobots.common.Robot;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class ControlledRobot extends BaseRobot {
    private Circle shape;
    public ControlledRobot(int r, int c, int size, Area a){
        super(r, c, size, a);
        this.shape = new Circle(0, 0, size);
        this.radius = size;
        Image skin = new Image("file:data/playerRobotSkin.jpg");
        this.shape.setFill(new ImagePattern(skin));
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
        return "P" + "," + String.valueOf(x) + "," +  String.valueOf(y) + "," +  String.valueOf(r);
    }
}
