package doodlejump;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainPanel extends JPanel implements ActionListener, KeyListener{
    private boolean inGame;
    private Font mainFont;
    private boolean midAir;
    private boolean firstEnter;
    private DoodleJumper jumper;
    private ArrayList<DoodleOpstacle> obstacles;
    
    private Image background;
    private int backgroundPosition;
    private final int BACKGROUND_WIDTH = 600;

    private final int PANEL_HEIGHT = MainFrame.getHeighT();
    private final int PANEL_WIDTH = MainFrame.getWidtH();
    
    private long frames;
    
    private Timer timer;
    private Random random;
    
    public MainPanel() {
        mainFont = new Font("Ariel", Font.BOLD, 18);
        firstEnter = false;
        loadImages();
        DoodleJumper.loadImages();
        DoodleOpstacle.loadImages();
        
        backgroundPosition = 0;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(null);
        setBackground(Color.WHITE);
        setFocusable(true);
        
        addKeyListener( this);
        
        resetJumper();
        
        obstacles = new ArrayList<>();
        frames = 0;
        
        timer = new Timer(30, this);
        
        random = new Random();
        
        inGame = false;
        midAir = false;
    }
    
    private void resetJumper() {
        jumper = new DoodleJumper((int)PANEL_WIDTH/2, (int)PANEL_HEIGHT-30);
        DoodleOpstacle.setSpeed(2);
        jumper.setGRAVITY(2);
    }
    
    private void loadImages() {
        try {
            background = ImageIO.read(new File("src/images/background.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void gameOver() {
        obstacles.clear();
        timer.stop();
        resetJumper();
        inGame = false;
        midAir = false;
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2d = (Graphics2D)g;
        g.drawRect(jumper.getX(), jumper.getY(), jumper.getWidth(), jumper.getHeight());
        drawBackground(g2d);
        drawJumper(g2d);
        
        if (inGame) {
            drawObstacles(g2d);
        } else {
            drawMessage(g2d);
        }
    }
    
    private void drawMessage(Graphics g2d) {
        g2d.setFont(mainFont);
            
        String message = "Press SPACE to start the game";

        FontMetrics fontMetrics = g2d.getFontMetrics(mainFont);
        int stringWidth = fontMetrics.stringWidth(message);

        g2d.drawString(message, (PANEL_WIDTH - stringWidth) / 2, PANEL_HEIGHT/2);
    }
    private void drawBackground(Graphics2D g2d) {
        g2d.drawImage(background, 0, backgroundPosition, BACKGROUND_WIDTH, PANEL_HEIGHT, null);
        
        if (backgroundPosition < -(BACKGROUND_WIDTH - PANEL_HEIGHT)) {
            g2d.drawImage(background, 0,backgroundPosition + BACKGROUND_WIDTH, BACKGROUND_WIDTH, PANEL_HEIGHT, null);
        }
    }
    
    private void drawJumper(Graphics2D g2d) {
        g2d.drawImage(DoodleJumper.getImage(), jumper.getX(), jumper.getY(), jumper.getWidth(), jumper.getHeight(), null);
    }
    
    private void drawObstacles(Graphics2D g2d) {
        for (DoodleOpstacle obstacle : obstacles) {
            Rectangle2D.Double upperRect = obstacle.getRectangle();

            g2d.drawImage(DoodleOpstacle.getPillarHeadImage(), (int)upperRect.x, (int)upperRect.y, (int)upperRect.width, (int)(upperRect.height), null);
            g2d.drawRect((int)upperRect.x, (int)upperRect.y, (int)upperRect.width, (int)(upperRect.height));
        }
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            frames++;
            moveObjects();
            handleCollisions();
            generateObstacles();
            cleanObstacles();
        }
        repaint();
    }
    private void handleCollisions() {
        int jumperY = jumper.getY();
        
        if(jumper.getSpeedY() >=0)
        {
            if(hasJumperHitObstacle()!= -1)
                stop(jumper, obstacles.get(hasJumperHitObstacle()));
        }
        if (jumperY + jumper.getHeight() > PANEL_HEIGHT) {
            gameOver();
            }
    }
    
    private int hasJumperHitObstacle() {
        Rectangle2D.Double jumperBounds = jumper.getBounds();
        if(jumper.getY() >= 0)
        for (int i = 0; i < obstacles.size(); i++) {
            if (obstacles.get(i).getRectangle().intersects(jumperBounds))
            {
                return i;
            }
        }
        return -1;
    }
    
    private void moveObjects() {
        int touch = -2;
        jumper.move();
        for (DoodleOpstacle obstacle : obstacles) {
            obstacle.move();
            touch = hasJumperHitObstacle();
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

    @Override
        public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(!inGame)
        {
            inGame = true;
            timer.start();
            frames = 70;
            firstEnter = true;
            jumper.setGRAVITY(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if(firstEnter){
                firstEnter = false;
            }
            else if(inGame){
                jumper.setGRAVITY(2);
                if(!midAir){
                    jumper.jump();
                    midAir = true;
                }
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
            moveSide(-5);
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            moveSide(5);
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    private void stop(DoodleJumper jumpers, DoodleOpstacle obstacle) {
        jumpers.stop(obstacle.getY() - 20);
        midAir = false;
    }

    private void moveSide(int side) {
        if(side < 0)
        {
            if(jumper.getX() > -side)
                jumper.setX(jumper.getX() + side);
            else
                jumper.setX(0);
        }else{
            if(jumper.getX() < PANEL_WIDTH - side -20)
                jumper.setX(jumper.getX() + side);
            else
                jumper.setX(PANEL_WIDTH - 20);
            
        }
        if(!obstacles.isEmpty())
            if(!obstacles.get(0).getRectangle().intersects(jumper.getBounds()))
                jumper.setGRAVITY(1);
    }
}

