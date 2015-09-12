/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doodlejump;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
public class MainFrame extends JFrame{
    private final static int Height = 600;
    private final static int Width = 400;  
    MainPanel panel = new MainPanel();
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
        setJMenuBar(initMenu());
        add(panel);
    }


    private JMenuBar initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem game = new JMenuItem("New Game");

        JMenu help = new JMenu("Help");
        help.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Help helpFrame = new Help();
                helpFrame.setVisible(true);
                helpFrame.setLocationRelativeTo(null);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int answer;
                answer = javax.swing.JOptionPane.showConfirmDialog(null, "Are you sure you want to exit ?", "QUESTION ?",
                        javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.WARNING_MESSAGE);
                if (answer == javax.swing.JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (MainPanel.isInGame()) {
                    int answer;
                    answer = javax.swing.JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel this game ?", "QUESTION ?",
                            javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.WARNING_MESSAGE);
                    if (answer == javax.swing.JOptionPane.YES_OPTION) {
                        panel.startGame();
                    }
                }
                else
                    panel.startGame();
            }
        });

        menu.add(game);
        menu.add(exit);

        menuBar.add(menu);
        menuBar.add(help);

        return menuBar;
    }
}