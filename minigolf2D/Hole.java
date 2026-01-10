/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package minigolf2D;

/**
 *
 * @author Muhammed
 */
public class Hole {

    private int x;
    private int y;
    private final int radius = 18;

    public Hole(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the radius
     */
    public int getRadius() {
        return radius;
    }
}
