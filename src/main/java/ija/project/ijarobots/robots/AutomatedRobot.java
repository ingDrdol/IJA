package ija.project.ijarobots.robots;

import ija.project.ijarobots.common.Area;
import ija.project.ijarobots.common.Position;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.image.Image;

import java.util.Random;

public class AutomatedRobot extends BaseRobot{
    private final Random rand = new Random();
    private int size;
    private boolean turning = false;
    private int turnAngle = 0;
    private Shape shape;
    public AutomatedRobot(int r, int c, int size, Area a) {
        super(r, c, (size + 5), a);
        this.size = size;
        this.speed = 2;
        this.shape = new Circle(0, 0, size);
        Image skin = new Image("file:data/playerBackground.jpg");
        this.shape.setFill(new ImagePattern(skin));
    }

    @Override
    public boolean move() {
        for(int i = 0; i < this.speed; i++) {
            this.getDirection();
            Position dest = new Position(this.row + this.dirX, this.col + this.dirY);
            if (this.canMove(dest)) {
                turning = false;
                this.savePosition();
                this.row += this.dirX;
                this.col += this.dirY;
            }
            else{
                if (turning){
                    this.turn(turnAngle);
                }
                else {
                    turning = true;
                    turnAngle = rand.nextInt(16) - 8;
                    if (turnAngle == 0){ turnAngle = 5; }
                    this.turn(turnAngle);
                }
            }
            this.savePosition();
        }
        return true;
    }

    public Shape getShape(String skin) {
        return this.shape;
    }

    @Override
    public Shape getShape() {
        return this.getShape("file:data/playerBackground.jpg");
    }

    public String getParams(){
        double x = this.row;
        double y = this.col;
        int r = this.radius;
        return "R" + "," + String.valueOf(x) + "," +  String.valueOf(y) + "," +  String.valueOf(r);
    }
}
