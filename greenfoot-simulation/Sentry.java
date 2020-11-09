import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Sentry here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sentry extends Building
{
    private static final int RANGE = 450, DAMAGE = 10, COOLDOWN = 15, IRONUSAGE = 2;
    private GreenfootImage fireImage;
    private Event nearestEvent;
    private float angleToNearest;
    private int xLoc, yLoc, coolDown = 0;
    private BuildingSlot slot;
    
    public Sentry(int xLoc, int yLoc, BuildingSlot slot) {
        sprite = SENTRY_SPRITE;
        fireImage = new GreenfootImage("sentryFire.png");
        this.slot = slot;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }
    
    public void _update() 
    {
        nearestEvent = getNearestEvent(Event.ZOMBIE, xLoc, yLoc);
        coolDown--;

        setAnimationImage();
        
        if(nearestEvent == null) return;

        angleToNearest = Utils.getAngleTo(xLoc, nearestEvent.getX(), yLoc, nearestEvent.getY());
        if(Utils.calcDist(xLoc, nearestEvent.getX(), yLoc, nearestEvent.getY()) <= RANGE) {
            if(coolDown < 0 && WorldManagement.iron >= IRONUSAGE) {
                nearestEvent.damage(DAMAGE);
                WorldManagement.iron -= IRONUSAGE;
                coolDown = COOLDOWN;
            } 
        }
        slot.setRotation((int) (angleToNearest * (180 / Math.PI)));
    }    
    
    private void setAnimationImage() {
        if(coolDown > COOLDOWN - 15) {
            slot.setSprite(fireImage);
        } else {
            slot.setSprite(sprite);
        }
    }
    public void destroy() {
    }
}
