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
    private static boolean inGame;
    private Font mainFont;
    private boolean midAir;
    private boolean firstEnter;
    private DoodleJumper jumper;
    private ArrayList<DoodleOpstacle> obstacles;
    private boolean startPicture;
    
    private Image background;
    private Image startImage;
    private int backgroundPosition;
    private final int BACKGROUND_WIDTH = 600;
    int frameCount = 1;
    int score = -2;
    
    private final int PANEL_HEIGHT = MainFrame.getHeighT();
    private final int PANEL_WIDTH = MainFrame.getWidtH();
    
    private long frames;
    
    private Timer timer;
    private Random random;

    public static boolean isInGame() {
        return inGame;
    }
    
    public MainPanel() {
        mainFont = new Font("Ariel", Font.BOLD, 18);
        firstEnter = false;
        loadImages();
        
        backgroundPosition = 0;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(null);
        setBackground(Color.WHITE);
        setFocusable(true);
        
        addKeyListener(this);
        startPicture();
        obstacles = new ArrayList<>();
        timer = new Timer(30, this);
        timer.start();
        
        random = new Random();
        
    }
    
    private void resetJumper() {
        jumper = new DoodleJumper((int)PANEL_WIDTH/2, (int)PANEL_HEIGHT-30);
        DoodleOpstacle.setSpeed(2);
        jumper.setGRAVITY(2);
    }
    private void startPicture(){
        startPicture = true;
    }
    private void loadImages() {
        try {
            background = ImageIO.read(new File("src/images/background.png"));
            startImage = ImageIO.read(new File("src/images/start.png"));
	    DoodleJumper.loadImages();
            DoodleOpstacle.loadImages();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public void startGame(){
        inGame = false;
        midAir = false;
        resetJumper();
        frames = 0;
        score = -1;
        startPicture = false;
        

    }
    private void gameOver() {
        obstacles.clear();
        resetJumper();
        inGame = false;
        midAir = false;
        startPicture = true;
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        if(startPicture){
            g2d.drawImage(startImage, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
            if(score >=0)
                drawEndScore(g2d);
        }else{
            g.drawRect(jumper.getX(), jumper.getY(), jumper.getWidth(), jumper.getHeight());
            drawBackground(g2d);
            drawJumper(g2d);

            if (inGame) {
                drawObstacles(g2d);
                drawScore(g2d);
            } else {
                drawMessage(g2d);
            }
        }
    }
    
    private void drawMessage(Graphics g2d) {
        g2d.setFont(mainFont);
            
        String message = "Press SPACE to start the game";

        FontMetrics fontMetrics = g2d.getFontMetrics(mainFont);
        int stringWidth = fontMetrics.stringWidth(message);

        g2d.drawString(message, (PANEL_WIDTH - stringWidth) / 2, PANEL_HEIGHT/2);
    }
    private void drawScore(Graphics g2d) {
        g2d.setFont(mainFont);
            
        String message = "SCORE: " + score;

        FontMetrics fontMetrics = g2d.getFontMetrics(mainFont);
        int stringWidth = fontMetrics.stringWidth(message);
        g2d.setColor(Color.red);
        g2d.drawString(message, 0, PANEL_HEIGHT);
    }
    private void drawEndScore(Graphics g2d) {
        g2d.setFont(mainFont);
            
        String message = "SCORE: " + score;

        FontMetrics fontMetrics = g2d.getFontMetrics(mainFont);
        int stringWidth = fontMetrics.stringWidth(message);
        g2d.setColor(Color.green);
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
            if(frames == frameCount*1000){
                System.out.print("\tBRZINA POVECANA = " + frameCount);
                frameCount++;
                DoodleOpstacle.setSpeed(frameCount+1);
            }
        }
        repaint();
    }
    private void handleCollisions() {
        int jumperY = jumper.getY();
        
        if(jumper.getSpeedY() >=0)
        {
            if(hasJumperHitObstacle()!= -1){
                if(midAir)
                    updateScore(5);
                stop(jumper, obstacles.get(hasJumperHitObstacle()));
        }
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
        for (DoodleOpstacle obstacle : obstacles) {
            obstacle.move();
        }
        jumper.move();
        backgroundPosition--;
        
        if (backgroundPosition < -BACKGROUND_WIDTH) {
            backgroundPosition = 0;
        }
    }
    
    private void cleanObstacles() {
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
        if(!startPicture){
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if(firstEnter && inGame){
                firstEnter = false;
                jumper.jump();
                
                System.out.println("sad smo ovde");
            }
            if (!inGame){
                inGame = true;
                frames = 65;
                score = 0;
                firstEnter = true;
                jumper.setGRAVITY(0);
            }
            else{
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
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    private void stop(DoodleJumper jumpers, DoodleOpstacle obstacle) 
    {
        jumpers.stop(obstacle.getY() - 20, DoodleOpstacle.getSpeed());
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
        if(!obstacles.isEmpty()){
            if(!obstacles.get(0).getRectangle().intersects(jumper.getBounds()))
                jumper.setGRAVITY(1);
            }
    }
    private void updateScore(int point){
        score += point;
    }
}