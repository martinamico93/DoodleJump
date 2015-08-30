/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doodlejump;
import javax.swing.JFrame;

public class MainFrame extends JFrame{
    private final static int Height = 600;
    private final static int Width = 400;
    
    public static int getHeighT(){
        return Height;
    }
    
    public static int getWidtH(){
        return Width;
    }
    
    public MainFrame(){
        setSize(Width, Height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        MainPanel panel = new MainPanel();
        add(panel);  }
    }
