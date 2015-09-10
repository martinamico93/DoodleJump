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
        rulesOfGame.setText("Moraces odkucati tekst ovde");
        rulesOfGame.setBounds(0, 0, 262, 248);
        rulesOfGame.setEditable(false);

        createdBy.setVerticalAlignment(JLabel.BOTTOM);
        createdBy.setHorizontalAlignment(JLabel.RIGHT);
        add(rulesOfGame);
        add(createdBy);
    }

}

