import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Meteor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Meteor extends Event
{
    private static final int MIN_DIST = 200, MAX_DIST = 300 + MIN_DIST;
    private static final int SPEED = 5;
    
    private int targetY, targetX;
    
    private float angleToTarget;
    
    public Meteor(int x, int y) {
        GreenfootImage image = new GreenfootImage("tornado.png");
        setImage(image);
        
        targetX = x;
        targetY = y;
        
        float randomRotation = (float)(Math.random() * 29393f);
        
        int distanceFromTarget = (int)((randomRotation * 1312) % MAX_DIST) + MIN_DIST;
        
        this.xLoc = x + (int) (Math.cos(randomRotation) * distanceFromTarget);
        this.yLoc = y + (int) (Math.sin(randomRotation) * distanceFromTarget);
       
        angleToTarget = (float) Math.atan2( (float) (y - yLoc), (float) (x - xLoc));
        
        this.rot = (int) (angleToTarget % (Math.PI * 2));
        
        throw new Error("We no have meteor");
    }
    public void _update() 
    {
        xLoc += (int) (Math.cos(angleToTarget) * SPEED);
        yLoc += (int) (Math.sin(angleToTarget) * SPEED);
        setRotation(rot);
    }    
}
