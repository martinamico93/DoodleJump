
package doodlejump;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainPanel extends JPanel implements ActionListener{
    private boolean inGame;
    private Font mainFont;

    private DoodleJumper jumper;
    private ArrayList<DoodleOpstacle> obstacles;
    
    private final int PANEL_HEIGHT = MainFrame.getHeighT();
    private final int PANEL_WIDTH = MainFrame.getWidtH();
    
    private long frames;
    
    private Timer timer;
    private Random random;
    
    public MainPanel() {
        mainFont = new Font("Ariel", Font.BOLD, 18);
        
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(null);
        setBackground(Color.WHITE);
        setFocusable(true);
        
        obstacles = new ArrayList<>();
        resetJumper();
        frames = 0;
        
        timer = new Timer(30, this);
        timer.start();
        
        random = new Random();
        
        inGame = true;
        
    }
    private void resetJumper() {
        jumper = new DoodleJumper((int)PANEL_WIDTH/2, (int)PANEL_HEIGHT-30);
        DoodleOpstacle.setSpeed(2);
        jumper.setGRAVITY(2);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        
        g.drawRect(jumper.getX(), jumper.getY(), jumper.getWidth(), jumper.getHeight());
	Rectangle2D.Double upperRect;
        if (inGame) {
            if(!obstacles.isEmpty()){
                for(int i=0; i< obstacles.size(); i++){
                    upperRect = obstacles.get(i).getRectangle();
                    g2d.drawRect((int)upperRect.x, (int)upperRect.y, (int)upperRect.width, (int)(upperRect.height));
                }
            }else {
            drawMessage(g2d);
            }
        }
    }
        
    private void drawMessage(Graphics2D g2d) {
        g2d.setFont(mainFont);
            
        String message = "Press SPACE to start the game";

        FontMetrics fontMetrics = g2d.getFontMetrics(mainFont);
        int stringWidth = fontMetrics.stringWidth(message);

        g2d.drawString(message, (PANEL_WIDTH - stringWidth) / 2, PANEL_HEIGHT/2);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            frames++;
            moveObjects();
            generateObstacles();
            cleanObstacles();
        }
        repaint();
    }
    
    private void moveObjects() {
        for (DoodleOpstacle obstacle : obstacles) {
            obstacle.move();
        }
    }
    
    private void cleanObstacles() {
        int d = obstacles.size();
        int i=0;
        if( !obstacles.isEmpty())
            if (obstacles.get(i).getY() >= PANEL_HEIGHT - 30){
                obstacles.remove(i);
            }
    }
    
    private void generateObstacles() {
        if (frames % 70 == 0) {
            obstacles.add(new DoodleOpstacle(random.nextInt(200), random.nextInt(200)+150));
        }
    }
}
