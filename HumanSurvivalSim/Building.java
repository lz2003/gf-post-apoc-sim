import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Building abstract class that contains the sprite and helper methods
 * 
 * @author Young Chen
 * @author Leo Foo
 * @version 2020-10-10
 */
public abstract class Building extends Actor
{
    protected GreenfootImage sprite;
    
    public static final GreenfootImage
        EMPTY_SPRITE = new GreenfootImage("empty.png"),
        FARM_SPRITE = new GreenfootImage("farm.png"),
        HOUSE_SPRITE = new GreenfootImage("house.png"), 
        MINE_SPRITE = new GreenfootImage("mine.png"),
        SENTRY_SPRITE = new GreenfootImage("sentry.png"), 
        STORAGE_SPRITE = new GreenfootImage("storage.png");
    
    private boolean destroyed = false;
    
    /**
     * Essentially the act method for all buildings.
     */
    public void _update() {
        // Default is empty
    }
    
    /**
     * Destroys the building
     */
    public void destroy()
    {
        if(!destroyed) {
            WorldManagement.getWorld().removeObject(this);
            destroyed = true;
        }
    }
    
    /**
     * Returns the image of the building
     * 
     * @return GreenfootImage   the building's image
     */
    public GreenfootImage getSprite()
    {
        return sprite;
    }
    
    /**
     * Get the nearest event.
     * 
     * @param eventID   the type of event
     * @param x         the x reference location 
     * @param y         the y reference location
     * @return Event    the nearest event
     */
    public static Event getNearestEvent(int eventID, int x, int y) {
        int lowest = 9999, index = 0;
        boolean found = false;
        for(int i = 0, n = WorldManagement.getEvents().size(); i < n; i++) {
            Event event = (Event)(WorldManagement.getEvents().get(i));
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
        
        return WorldManagement.getEvents().size() == 0 ? null : WorldManagement.getEvent(index);
    }
}
