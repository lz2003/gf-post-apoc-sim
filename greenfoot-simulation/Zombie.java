import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Class of enemy that chases down and attacks humans, and if they manage to kill a human, that human turns into
 * a zombie too.
 * 
 * @author Young Chen
 * @version 2020-10-09
 */
public class Zombie extends Enemy
{
    private static float SPEED = 1.1f;
    private static int DAMAGE = 5, RANGE = 5, SPAWN_DELAY = 100, ATTACK_DELAY = 10;
    
    private float moveAngle;
    private int targetX, targetY;
    private Human nearestHuman;
    
    private int spawnDelay, attackDelay;
    
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
        spawnDelay = SPAWN_DELAY;
        hp = 120;
        setImage(new GreenfootImage("zombie.png"));
    }

    /**
     * Zombie update method
     */
    public void _update() {
        if(spawnDelay > 0) {
            spawnDelay--;
            return;
        }
        moveToHuman();
        damageHuman();
    }
    
    /**
     * Damages nearest human if its within range
     */
    private void damageHuman() {
        if(nearestHuman == null) return;
        
        if(attackDelay > 0) {
            attackDelay--;
            return;
        }
        
        int x = nearestHuman.getX();
        int y = nearestHuman.getY();
        
        if(x >= xLoc - RANGE && x <= xLoc + RANGE && y >= yLoc - RANGE && y <= yLoc + RANGE) {
            nearestHuman.damage(DAMAGE);
            attackDelay = ATTACK_DELAY;
        } else {
            setRotation((int)(moveAngle * (180f / Math.PI)));
        }
    }
    
    /**
     * Moves to nearest human
     */
    private void moveToHuman() {
        setNearestHuman();
        moveAngle = Utils.getAngleTo((int) xLoc, targetX,(int) yLoc, targetY);
        xLoc += (Math.cos(moveAngle) * SPEED);
        yLoc += (Math.sin(moveAngle) * SPEED);
    }
    
    /**
     * Finds the nearest human
     */
    private void setNearestHuman() {
        if(WorldManagement.humans.size() <= 0) return;
        
        nearestHuman = getNearestHuman((int) xLoc, (int) yLoc);
        
        targetX = nearestHuman.getX();
        targetY = nearestHuman.getY();
    }
}
