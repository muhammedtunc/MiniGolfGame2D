/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package minigolf2D;

/**
 *
 * @author Muhammed
 */
class Ball {

    private double x, y;
    private double vx = 0, vy = 0;
    private final int radius = 10;
    private static final double FRICTION = 0.98;

    Ball(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void move() {
        setX(getX() + getVx());
        setY(getY() + getVy());
        setVx(getVx() * FRICTION);
        setVy(getVy() * FRICTION);
    }
    
    boolean isStopped() {
        return vx*vx + vy*vy < 0.1;
    }
    
    double distanceTo(double hx, double hy) {
        return Math.hypot(x - hx, y - hy);
    }
    
//    public boolean isSlow() {
//        return vx*vx + vy*vy < 4.0;
//    }
    
    boolean intersects(Wall wall){
        double closestX = Math.max(wall.getX(), Math.min(x, wall.getX() + wall.getWidth()));
        double closestY = Math.max(wall.getY(), Math.min(y, wall.getY() + wall.getHeight()));
        // distance between the ball and the wall
        double dx = x - closestX;
        double dy = y - closestY;
        double distanceSquared = dx * dx + dy * dy;
        
        return distanceSquared <= (radius * radius);
    }
    
    boolean intersects(Lake lake){
        return x>lake.getX()+10 && 
               x<lake.getHeight()+lake.getX()-10 && 
               y>lake.getY()+10 && 
               y<lake.getWidth()+lake.getY()-10;
    }
    
    
    /**
     * @return the x
     */
    double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    void setY(double y) {
        this.y = y;
    }

    /**
     * @return the vx
     */
    double getVx() {
        return vx;
    }

    /**
     * @param vx the vx to set
     */
    void setVx(double vx) {
        this.vx = vx;
    }

    /**
     * @return the vy
     */
    double getVy() {
        return vy;
    }

    /**
     * @param vy the vy to set
     */
    void setVy(double vy) {
        this.vy = vy;
    }

    /**
     * @return the radius
     */
    int getRadius() {
        return radius;
    }
}
