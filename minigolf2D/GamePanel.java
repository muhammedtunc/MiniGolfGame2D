/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package minigolf2D;

/**
 *
 * @author Muhammed
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener{
    
    private List<Obstacle> obstacles;
    
    private Point startDrag;
    private Point endDrag;
    
    private Timer timer;
    private Ball ball;
    private Hole hole;
    
    private int currentLevel = 1;
    private int strokes = 0;
    private int maxStrokes;
    
    private boolean isMoving = false;
    private boolean isDragging = false;
    private boolean levelComplete = false;
    
    private JComboBox<String> levelBox = new JComboBox<>();
    private DefaultComboBoxModel model = new DefaultComboBoxModel();
    private ArrayList<Integer> seenLevels = new ArrayList<>();
    
    JLabel strokesLabel;
    JButton levelBtn;
    
    GamePanel(){
        setBackground(new Color(0, 102,0));// color the pitch  
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(new GameKeyListener());
        timer = new Timer(16, this); // ~60 FPS
        strokesLabel = new JLabel();
        levelBtn = new JButton("Select Level");
        levelBox.setModel(model);
        initLevel(currentLevel);  
    }
    
    private void initLevel(int level){
        strokes = 0;
        ball = new Ball(100, 500);
        hole = new Hole(700, 100);
        obstacles = new ArrayList<>();
        levelComplete = false;
        isMoving = false;
        
        if(!seenLevels.contains(level)){
            model.addElement("Level: " + currentLevel);
            seenLevels.add(level);
        }
        levelBox.setSelectedIndex(currentLevel-1);
        
        
        // level designs
        switch (level) {
            case 1 -> {
                maxStrokes = 3;
            }    
            case 2 -> {
                maxStrokes = 3;
                obstacles.add(new Wall(200, 200, 20, 200));
                obstacles.add(new Lake(400, 150, 300, 300));
            }
            case 3 -> {
                maxStrokes = 4;
                obstacles.add(new Wall(300, 50, 20, 200));
                obstacles.add(new Wall(500, 100, 20, 200));
                obstacles.add(new Lake(500, 300, 200, 200));
            }
            case 4 -> {
                maxStrokes = 4;
                obstacles.add(new Wall(500, 100, 50, 50));
                obstacles.add(new Wall(350, 300, 50, 50));
                obstacles.add(new Wall(650, 200, 50, 50));
                obstacles.add(new Wall(150, 200, 50, 50));
                obstacles.add(new Wall(600, 450, 50, 50));
            }
            case 5 -> {
                maxStrokes = 5;
                obstacles.add(new Wall(260, 0, 20, 300));
                obstacles.add(new Wall(400, 150, 20, 500));
                obstacles.add(new Wall(540, 0, 20, 300));
                obstacles.add(new Lake(600, 350, 150, 150));
            }
            case 6 -> {
                maxStrokes = 6;
                obstacles.add(new Wall(0, 400, 400, 20));
                obstacles.add(new Wall(400, 200, 400, 20));
                obstacles.add(new Wall(550, 320, 50, 50));
                obstacles.add(new Wall(500, 100, 50, 50));
                obstacles.add(new Lake(100, 100, 200, 200));
            }
        }
        strokesLabel.setText("Strokes " + strokes + " / " + maxStrokes);
        repaint();
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //Draw Hole
        g2d.setColor(Color.BLACK);
        g2d.fill(new Ellipse2D.Double(hole.getX() - hole.getRadius(), hole.getY() - hole.getRadius(), hole.getRadius()*2, hole.getRadius()*2));
        // Obstacles
        for (Obstacle obs : obstacles) {
            if(obs instanceof Lake){
                g2d.setColor(Color.blue);
                g2d.fillOval(obs.getX(), obs.getY(), obs.getWidth(), obs.getHeight());
            }else if (obs instanceof Wall){
                g2d.setColor(Color.orange.darker());
                g2d.fillRect(obs.getX(), obs.getY(), obs.getWidth(), obs.getHeight());
            }
        }
        // draw hit line
        if (isDragging && startDrag != null && endDrag != null) {
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
        }
        //Draw Ball
        g2d.setColor(Color.WHITE);
        g2d.fill(new Ellipse2D.Double(ball.getX() - ball.getRadius(), ball.getY() - ball.getRadius(), ball.getRadius()*2, ball.getRadius()*2));
        
              
        g2d.setColor(Color.RED.darker());
        if (levelComplete) {
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            g2d.drawString("Level Compeleted!", 220, 200);
            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            g2d.drawString("Press SPACE to proceed", 240, 250);
        } else if (strokes >= maxStrokes && ball.isStopped()) {
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            g2d.drawString("No strokes left!", 250, 200);
            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            g2d.drawString("Press R to repeat the level!", 240, 250);
        }
        
    }
    
    private void checkCollisions(){
        // Collisions with frames
        if (ball.getX()-ball.getRadius()-2 < 0 || ball.getX()+ball.getRadius()+2 > getWidth()) ball.setVx(-ball.getVx()*0.8);
        if (ball.getY()-ball.getRadius()-2 < 0 || ball.getY()+ball.getRadius()+2 > getHeight()) ball.setVy(-ball.getVy()*0.8);
        
        // collisions with obstacles
        for(Obstacle obs : obstacles){
            if (obs instanceof Wall){
                Wall wall = (Wall) obs;
                if(ball.intersects(wall)){
                    double closestX = Math.max(obs.getX(), Math.min(ball.getX(), obs.getX() + obs.getWidth()));
                    double closestY = Math.max(obs.getY(), Math.min(ball.getY(), obs.getY() + obs.getHeight()));


                    double dx = ball.getX() - closestX;
                    double dy = ball.getY() - closestY;
                    double distance = Math.hypot(dx, dy);

                    // unit vector components
                    double nx = dx/distance; 
                    double ny = dy/distance; 


                    double dotProduct = ball.getVx() * nx + ball.getVy() * ny;

                    if (dotProduct < 0) {
                        // reflection: v' = v - 2 * (v · n) * n  
                        ball.setVx(ball.getVx() - 2 * dotProduct * nx);
                        ball.setVy(ball.getVy() - 2 * dotProduct * ny);

                        // Energy loss
                        ball.setVx(ball.getVx()*0.8);
                        ball.setVy(ball.getVy()*0.8);
                    }
                }
            }else if (obs instanceof Lake){ // collision with lakes
                Lake lake = (Lake) obs;
                if(ball.intersects(lake)){
                    ball.setVx(ball.getVx()*0.9);
                    ball.setVy(ball.getVy()*0.9);
                }
            }
        }
    }
    
    private void checkHole() {
        if (ball.distanceTo(hole.getX(), hole.getY()) < ball.getRadius() - 2) {
            isMoving = false;
            timer.stop();
            levelComplete = true;
            repaint();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isMoving) {
            ball.move();
            checkCollisions();
            checkHole();

            if (ball.isStopped()) {
                isMoving = false;
                timer.stop();
            }
            repaint();
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        requestFocus();
        if(!isMoving && !levelComplete && strokes < maxStrokes){
            startDrag = e.getPoint();
            isDragging = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(isDragging && !isMoving && strokes <= maxStrokes){
            endDrag = e.getPoint();
            isDragging = false;
            
            double dx = startDrag.x - endDrag.x;
            double dy = startDrag.y - endDrag.y;
            double speed = Math.hypot(dx, dy) / 12.0; // Scale power
            double angle = Math.atan2(dy, dx);

            ball.setVx(speed * Math.cos(angle));  // Negative because drag is opposite
            ball.setVy(speed * Math.sin(angle));

            isMoving = true;
            timer.start();
            if(!(speed < 0.1)) strokes++;
            strokesLabel.setText("Strokes " + strokes + " / " + maxStrokes);
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isDragging) {
            endDrag = e.getPoint();
            repaint();
        }
    }

    
    
    @Override public void mouseClicked(MouseEvent e){}
    @Override public void mouseEntered(MouseEvent e){}
    @Override public void mouseExited(MouseEvent e){}
    @Override public void mouseMoved(MouseEvent e){}
    
    
    class InfoPanel extends JPanel{
        InfoPanel(){
            this.setLayout(new FlowLayout());
            this.add(levelBox);
            this.add(levelBtn);
            this.add(strokesLabel);
            levelBtn.addActionListener(e -> {
                currentLevel = levelBox.getSelectedIndex()+1;
                initLevel(currentLevel);
            });
        }
    }
    
    private class GameKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            
            if (levelComplete && e.getKeyCode() == KeyEvent.VK_SPACE) {
                currentLevel++;
                if (currentLevel > 6) {
                    JOptionPane.showMessageDialog(null, "Congratulations! All levels are completed!");
                    JOptionPane.showMessageDialog(null,  "Your files has been encrypted!","Warning",JOptionPane.WARNING_MESSAGE);
                    JOptionPane.showMessageDialog(null, "Just kidding!");
                    System.exit(0);
                }
                initLevel(currentLevel);
            } else if (strokes == maxStrokes && e.getKeyCode() == KeyEvent.VK_R) {
                initLevel(currentLevel);  
            }
        }
    }
    
    
}
