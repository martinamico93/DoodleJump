
package doodlejump;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class MainPanel extends JPanel{
	private boolean inGame;
	private Font mainFont;
	private final int PANEL_HEIGHT = MainFrame.getHeighT();
	private final int PANEL_WIDTH = MainFrame.getWidtH();

public MainPanel() {
        mainFont = new Font("Ariel", Font.BOLD, 18);

	setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(null);
        setBackground(Color.WHITE);
        setFocusable(true);
	
	 inGame = false;
}

@Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}