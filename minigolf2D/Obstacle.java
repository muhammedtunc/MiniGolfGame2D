/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package minigolf2D;

/**
 *
 * @author Muhammed
 */
abstract class Obstacle{

    private int x;
    private int y;
    private int width;
    private int height;

    Obstacle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    
    /**
     * @return the x
     */
    int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    void setY(int y) {
        this.y = y;
    }

    /**
     * @return the width
     */
    int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    void setHeight(int height) {
        this.height = height;
    }
}
