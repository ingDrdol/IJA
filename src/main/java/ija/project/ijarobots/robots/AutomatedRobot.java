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
    private Circle shape;
    private int size;
    private boolean turning = false;
    private int turnAngle = 0;

    public AutomatedRobot(int r, int c, int size, Area a) {
        super(r, c, (size + 5), a);
        this.size = size;
        this.speed = 0;

        this.shape = new Circle(this.col, this.row, this.size);
        Image image = new Image("file:data/playerBackground.jpg");
        shape.setFill(new ImagePattern(image));
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
        }
        return true;
    }

    @Override
    public Shape getShape() {
        return this.shape;
    }

    public String getParams(){
        double x = this.row;
        double y = this.col;
        int r = this.radius;
        return "R" + "," + String.valueOf(x) + "," +  String.valueOf(y) + "," +  String.valueOf(r);
    }
}
