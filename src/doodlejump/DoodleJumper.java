/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doodlejump;

/**
 *
 * @author Marta Cvoro
 */
public class DoodleJumper {
    private int x;
    private int y;
    
    private final int JUMPER_WIDTH = 20;
    private final int JUMPER_HEIGHT = 20;
    
    private int speedY = 1;
    private int GRAVITY = 2;
    
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

    int getSpeedY() {
        return speedY;
    }

    public int getWidth() {
        return JUMPER_WIDTH;
    }

    public int getHeight() {
        return JUMPER_HEIGHT;
    }
    
    public void jump() {
        speedY = -20;
        setGRAVITY(1);
    }

    public void setGRAVITY(int GRAVITY) {
        this.GRAVITY = GRAVITY;
    }
    
    public void move() {
        y += speedY;
        speedY += GRAVITY;
    }

    void stop(int position) {
        y = position;
        setGRAVITY(0);
        speedY = Math.abs(DoodleOpstacle.getSpeed());
    }


}

