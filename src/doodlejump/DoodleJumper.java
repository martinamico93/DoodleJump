/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doodlejump;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Marta Cvoro
 */
public class DoodleJumper {

    static BufferedImage jumperImage = null;

    private int x;
    private int y;

    private final int JUMPER_WIDTH = 35;
    private final int JUMPER_HEIGHT = 35;

    private double speedY = 0;
    private double GRAVITY = 2;
 
    public DoodleJumper(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }

    double getSpeedY() {
        return speedY;
    }

    public int getWidth() {
        return JUMPER_WIDTH;
    }

    public int getHeight() {
        return JUMPER_HEIGHT;
    }

    public static void loadImages() {
        try {
            jumperImage = ImageIO.read(new File("src/images/jumper.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void jump() {
        speedY = -16;
        
    }

    public void setGRAVITY(double GRAVITY) {
        this.GRAVITY = GRAVITY;
    }

    public double getGRAVITY() {
        return GRAVITY;
    }

    public void move() {
        y += speedY;
        speedY += GRAVITY;
       
        
    }

    public static BufferedImage getImage() {
        return jumperImage;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    void stop(int position, double speed) {
        y = position;
        speedY = speed;
    }

    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(x, y, JUMPER_WIDTH, JUMPER_HEIGHT);
    }
}
