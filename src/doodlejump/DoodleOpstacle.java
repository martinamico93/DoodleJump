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
public class DoodleOpstacle {
    static BufferedImage PillarHead = null;

    private int x;
    private int maxHeight;
    private int y;
    
    private static int SPEED_X = 2;
    
    private Rectangle2D.Double Rectangle;
    
    public static int getSpeed() {
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
        
        this.y = 0;
        
        Rectangle = new Rectangle2D.Double(x, y, maxHeight, 20);
    }
     public DoodleOpstacle(int x, int maxHeight, int y1) {
         this.x = x;
        this.maxHeight = maxHeight;
        
        this.y = y1;
        
        Rectangle = new Rectangle2D.Double(x, y, maxHeight, 20);
     }
    
    public static void loadImages() {
        try {
            PillarHead = ImageIO.read(new File("src/images/pillar-head.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public void move() {
        y += SPEED_X;
    }
    
    public boolean isOutOfPanel() {
        return y  > MainFrame.getHeighT() - 50;
    }
    
    public static BufferedImage getPillarHeadImage() {
        return PillarHead;
    }
    
    public Rectangle2D.Double getRectangle() {
        Rectangle.y = y;
        return Rectangle; 
    }
}