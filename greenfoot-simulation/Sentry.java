import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Sentry here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sentry extends Building
{
    private static final int RANGE = 450, DAMAGE = 20, COOLDOWN = 100;
    private GreenfootImage fireImage;
    private Event nearestEvent;
    private float angleToNearest;
    private int xLoc, yLoc, coolDown = 0;
    private BuildingSlot slot;
    protected Event getNearestEvent(int eventID, int x, int y) {
        int lowest = 9999, index = 0;
        boolean found = false;
        for(int i = 0, n = WorldManagement.events.size(); i < n; i++) {
            Event event = (Event)(WorldManagement.events.get(i));
            int xLoc = event.getX(), yLoc = event.getY();
            int distance = Utils.calcDist(x, xLoc, y, yLoc);
            if(distance < lowest && event.getType() == eventID) {
                lowest = distance;
                index = i;
                found = true;
            }
        }
        // If no event of indicated type if found and the event arraylist contains other events, return null
        if(!found) return null;
        
        return WorldManagement.events.size() == 0 ? null : WorldManagement.getEvent(index);
    }
    
    public Sentry(int xLoc, int yLoc, BuildingSlot slot) {
        sprite = new GreenfootImage("sentry.png");
        fireImage = new GreenfootImage("sentryFire.png");
        fireImage.scale(WorldManagement.BUILDING_SIZE, WorldManagement.BUILDING_SIZE);
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
            if(coolDown < 0 && WorldManagement.iron >= 2) {
                nearestEvent.damage(DAMAGE);
                WorldManagement.iron -= 2;
                coolDown = COOLDOWN;
            } 
        }
        slot.setRotation((int) (angleToNearest * (180 / Math.PI)));
    }    
    
    private void setAnimationImage() {
        if(coolDown > 0) {
            slot.setSprite(fireImage);
        } else if ((float) coolDown < (float) COOLDOWN / 1.2){
            slot.setSprite(sprite);
        }
    }
    public void destroy() {
    }
}
