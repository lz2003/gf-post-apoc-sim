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
        SPEED = 1, 
        COOLDOWN = 200, 
        SPRITE_SIZE = 100,
        MAX_SIZE = 200;
    private int 
        turnCooldown = 50, 
        targetRot = 0,
        size = SPRITE_SIZE;
    private int spriteRot = 0;
    private GreenfootImage fullres;
    
    /**
     * Creates a tornado at specified location
     * 
     * @param xLoc Location in x axis of tornado
     * @param yLoc Location in y axis of tornado
     */
    public Tornado(int xLoc, int yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        damage = 100;
        rot = 0;
        hp = 10000;
        fullres = new GreenfootImage("tornado.png");
        GreenfootImage img = new GreenfootImage(fullres);
        img.scale(SPRITE_SIZE, SPRITE_SIZE);
        setImage(img);
        this.type = TORNADO;
    }
    /**
     * Tornado update method
     */
    public void _update() {
        moveWithRot();
        turnTowards();
        setTargetRot();
        int sizeIncrease = killNearbyThings((int) xLoc,(int) yLoc, (int) (size / 2), damage);
        if(sizeIncrease > 0) {
            GreenfootImage img = new GreenfootImage(fullres);
            size += Math.min(sizeIncrease, 2);
            size = Math.min(size, MAX_SIZE);
            img.scale(size, size);
            setImage(img);
        }
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
        keepWithinWorld();
    }
    
    private void keepWithinWorld() {
        int limitMax = WorldManagement.WORLD_SIZE * 3;
        int limitMin = -WorldManagement.WORLD_SIZE;
        int max = (int) ((float) limitMax / 1.4f);
        int min = (int) ((float) limitMin / 1.4f);
        
        if(xLoc < min) targetRot = 0;
        if(xLoc > max) targetRot = 180;
        if(yLoc < min) targetRot = 90;
        if(yLoc > max) targetRot = 270;
        
        if(xLoc < limitMin) rot = 0;
        if(xLoc > limitMax) rot = 180;
        if(yLoc < limitMin) rot = 90;
        if(yLoc > limitMax) rot = 270;
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
