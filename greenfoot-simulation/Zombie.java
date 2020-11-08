import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Zombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Zombie extends Enemy
{
    private static float SPEED = 0.5f;
    private static int DAMAGE = 1, RANGE = 5;
    
    private float moveAngle;
    private int targetX, targetY;
    private Human nearestHuman;
    
    /**
     * Create a zombie at specified location
     * 
     * @param xLoc Location in x-axis of zombie
     * @param yLoc Location in y-axis of zombie
     */
    public Zombie(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.type = ZOMBIE;
        setImage(new GreenfootImage("zombie.png"));
    }
    int e = 0;
    /**
     * Zombie update method
     */
    public void _update() {
        moveToHuman();
        damageHuman();
    }
    
    private void damageHuman() {
        int x = nearestHuman.getX();
        int y = nearestHuman.getY();
        if(x >= xLoc - RANGE && x <= xLoc + RANGE && y >= yLoc - RANGE && y <= yLoc + RANGE) {
            nearestHuman.damage(DAMAGE);
        }
    }
    
    private void moveToHuman() {
        setNearestHuman();
        moveAngle = Utils.getAngleTo((int) xLoc, targetX,(int) yLoc, targetY);
        xLoc += (Math.cos(moveAngle) * SPEED);
        yLoc += (Math.sin(moveAngle) * SPEED);
    }
    
    private void setNearestHuman() {
        if(WorldManagement.humans.size() <= 0) return;
        
        nearestHuman = getNearestHuman((int) xLoc, (int) yLoc);
        
        targetX = nearestHuman.getX();
        targetY = nearestHuman.getY();
    }
}
