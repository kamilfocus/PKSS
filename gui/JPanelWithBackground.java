package com.piotrek;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;


public class JPanelWithBackground extends JPanel {

    private Image backgroundImage;

    public JPanelWithBackground(String fileName) {
        backgroundImage = Toolkit.getDefaultToolkit().createImage(fileName);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
