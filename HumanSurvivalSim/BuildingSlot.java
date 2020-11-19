import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Locations where buildings can be built.
 * 
 * @author Lucy Zhao
 * @author Young Chen
 * @version 2020-10-11
 */
public class BuildingSlot extends Actor
{
    // Building IDs
    public static final int
            EMPTY = -1,
            ARMOURY = 0,
            BARRACKS = 1,
            FARM = 2,
            MINE = 3,
            SENTRY = 6,
            STORAGE = 4,
            HOUSE = 5;
    
    // Production constants
    public static final float 
            FARM_PRODUCTION = 4,
            MINE_PRODUCTION = 3;
            
    // Capacity constants   
    public static final int 
            STORAGE_CAPACITY = 100,
            HOUSE_CAPACITY = 5,
            DEFAULT_HP = 100;
    
    public static final GreenfootSound destroySound = new GreenfootSound("building_destroyed.wav");
    private static final GreenfootImage sprite = new GreenfootImage("empty.png");         
            
    private int xLoc, yLoc, index, type;
    private Building building;
    private int hp = DEFAULT_HP;
    private boolean targeted = false, destroyed = true;
    
    /**
     * Constructor of BuildingSlot, takes coordinates and a index.
     * 
     * @param x         the x location
     * @param y         the y location
     * @param index     the index of the BuildingSlot
     */
    public BuildingSlot(int x, int y, int index) {
        xLoc = x;
        yLoc = y;
        this.index = index;
        
        // Initialize all buildings as empty slots
        type = EMPTY;
        building = new Empty();
        setImage(sprite);
    }
    
    /**
     * Sets whether or not a human is targeting this building.
     * 
     * @param status    true if a human is targeting it, else false
     */
    public void setTargetStatus(boolean status)
    {
        targeted = status;
    }
    
    /**
     * Returns whether or not a human is targeting this building.
     * 
     * @return boolean  true if so, otherwise false
     */
    public boolean getTargetStatus()
    {
        return targeted;
    }
    
    /**
     * Returns the type of building
     * 
     * @return int  the id of the building
     */
    public int getType() {
        return type;
    }

    /**
     * Returns the x location
     * 
     * @return int  the x location of the building
     */
    public int getX() {
        return xLoc;
    }

    /**
     * Returns the y location
     * 
     * @return int  the y location of the building
     */
    public int getY() {
        return yLoc;
    }

    /**
     * Returns the building at that slot
     * 
     * @return Building    the building
     */
    public Building getBuilding() {
        return building;
    }
    
    /**
     * Sets the the building sprite and type.
     * 
     * @param buildingID  the type of building
     */
    public void setBuilding(int buildingID) {
        switch(buildingID) {
            case FARM:
                this.type = FARM; 
                building = new Farm();
                destroyed = false;
                break;
            case MINE:
                this.type = MINE; 
                building = new Mine();
                destroyed = false;
                break;
            case STORAGE:
                this.type = STORAGE; 
                building = new Storage();
                destroyed = false;
                break;
            case HOUSE:
                this.type = HOUSE; 
                building = new House(this);
                destroyed = false;
                break;
            case SENTRY:
                this.type = SENTRY;
                building = new Sentry(xLoc, yLoc);
                destroyed = false;
                break;
            case EMPTY:
                this.type = EMPTY; 
                setRotation(0);
                building = new Empty();
                destroyed = true;
                break;
        }
        WorldManagement.getWorld().addObject(building, xLoc, yLoc);
    }
    
    /**
     * Hurt the current building by set amount of damage
     * 
     * @param damage    how much damage is dealt
     */
    public void damage(int damage) {
        if(destroyed) return;
        hp -= damage;
        if(hp <= 0) {
            destroy();
        }
    }
    
    /**
     * Destroys the building
     */
    public void destroy() {
        building.destroy();
        setBuilding(EMPTY);
        destroyed = true;
        WorldManagement.playSound(destroySound);
    }
    
    /**
     * Gets whether or not the buildingslot is destroyed
     * 
     * @return Whether its destroyed or not
     */
    public boolean isDestroyed() {
        return destroyed;
    }
    
    /**
     * Object update method
     */
    public void _update() {
        building._update();
    }
}
