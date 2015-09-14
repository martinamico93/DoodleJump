/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doodlejump;

import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author Marta Cvoro
 */

public class Help extends JFrame {

    private JLabel createdBy = new JLabel();
    private JTextArea rulesOfGame = new JTextArea();

    public Help() throws HeadlessException {
        this.setTitle("Help");
        this.setSize(262, 300);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        createdBy.setText("Created by: Marta Čvoro");
        rulesOfGame.setBackground(this.getBackground());
        rulesOfGame.setText (  "  Keep jumping higher and higher while\n "
                + " earning points.\n"
                + " As you go higher, the platforms move faster\n"
                + " After you lose, calmly recall exactly what\n "
                + " happened  before you crashed, died, etc.\n"
                + "  In retrospect, what could\n"
                + " you have done to avoid that end?\n"
                + " Doodle jump is not your life,\n"
                + "don't let it waste your time.");
         rulesOfGame.setBounds(0, 0, 262, 248);
        rulesOfGame.setEditable(false);

        createdBy.setVerticalAlignment(JLabel.BOTTOM);
        createdBy.setHorizontalAlignment(JLabel.RIGHT);
        add(rulesOfGame);
        add(createdBy);
    }

}

