/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package minigolf2D;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author Muhammed
 */

import javax.swing.JPanel;
import minigolf2D.GamePanel.InfoPanel;
import javax.swing.ImageIcon;


class Golf extends JFrame{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        
        JFrame frame = new JFrame("Golf Game");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        ImageIcon icon = new ImageIcon(Golf.class.getResource("/minigolf2D/icon.png"));
        frame.setIconImage(icon.getImage());
        
        
        
        GamePanel gamePanel = new GamePanel();
        JPanel infoPanel =  gamePanel.new InfoPanel();
        
        frame.add(infoPanel, BorderLayout.NORTH);
        frame.add(gamePanel, BorderLayout.CENTER);
        
        frame.setVisible(true);
        
        
        
    }
    
}
