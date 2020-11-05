import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * A tornado that moves around and damages nearby buildings
 * 
 * @author Young Chen
 * @version 2020-11-04
 */
public class Tornado extends Event
{
    private static final int 
        SPEED = 3, 
        COOLDOWN = 200, 
        RANGE = 50, 
        SPRITE_SIZE = 100;
    private int 
        turnCooldown = 50, 
        targetRot = 0;
    private int spriteRot = 0;
    
    /**
     * Creates a tornado at specified location
     * 
     * @param xLoc Location in x axis of tornado
     * @param yLoc Location in y axis of tornado
     */
    public Tornado(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        damage = DEFAULT_DAMAGE;
        range = RANGE;
        rot = 0;
        GreenfootImage img = new GreenfootImage("tornado.png");
        img.scale(SPRITE_SIZE, SPRITE_SIZE);
        setImage(img);
    }
    /**
     * Tornado update method
     */
    public void _update() {
        moveWithRot();
        turnTowards();
        setTargetRot();
        killNearbyThings(xLoc, yLoc, range, damage);
        setSpriteRot();
    }
    
    private void setSpriteRot() {
        spriteRot += 8;
        setRotation(spriteRot);
    }
    
    private void setTargetRot() {
        if(turnCooldown <= 0) {
            targetRot = (int) (Math.random() * 20301);
            turnCooldown = COOLDOWN;
        } else {
            turnCooldown -= 1;
        }
    }
    
    private void turnTowards() {
        rot = rot != targetRot ? (rot > targetRot ? rot - 1 : rot + 1) : rot;
    }
    
    private void moveWithRot() {
        // convert to radians
        this.xLoc += Math.cos(rot * (Math.PI / 180)) * SPEED;
        this.yLoc += Math.sin(rot * (Math.PI / 180)) * SPEED;
    }
}
