import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Sentry that attacks nearby zombies
 * 
 * @author Young Chen
 * @version 2020-10-09
 */
public class Sentry extends Building
{
    private static final int RANGE = 450, DAMAGE = 20, COOLDOWN = 15, IRONUSAGE = 3;
    private GreenfootImage fireImage;
    private Event nearestEvent;
    private float angleToNearest;
    private int xLoc, yLoc, coolDown = 0;
    
    public Sentry(int xLoc, int yLoc) {
        sprite = SENTRY_SPRITE;
        fireImage = new GreenfootImage("sentryFire.png");
        setImage(sprite);
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.setRotation((int)Math.round(Math.random()*4.0)*90);
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
        this.setRotation((int) (angleToNearest * (180 / Math.PI)));
    }    
    
    private void setAnimationImage() {
        if(coolDown > COOLDOWN - 15) {
            setImage(fireImage);
        } else {
            setImage(sprite);
        }
    }
}
