/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doodlejump;

import java.awt.geom.Rectangle2D;

public class DoodleOpstacle {
    private int x;
    private int maxHeight;
    private int y;
        
    private static int SPEED_X = 2;
    
    private final int OBSTACLE_WIDTH = 80;
    private final int GAP_HEIGHT = 150;

private Rectangle2D.Double Rectangle;
    

    static int getSpeed() {
        return SPEED_X;
    }
    static void setSpeed(int i){
        SPEED_X = i;
    }
    public int getY() {
        return y;
    }
    
    public int getX() {
        return x;
    }

    public DoodleOpstacle(int x, int maxHeight) {
        this.x = x;
        this.maxHeight = maxHeight;
        
        int y = 0;
        
        Rectangle = new Rectangle2D.Double(x, y, maxHeight, 20);
    }
    
    public void move() {
        y += SPEED_X;
    }
        
    public Rectangle2D.Double getRectangle() {
        Rectangle.y = y;
        return Rectangle; 
    }
}
/**
 *
 * @author Marta Cvoro
 */
