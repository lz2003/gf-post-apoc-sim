import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BuildingSlot here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
            MINE_PRODUCTION = 2;
            
    // Capacity constants   
    public static final int 
            STORAGE_CAPACITY = 100,
            HOUSE_CAPACITY = 5,
            DEFAULT_HP = 100;

    private int xLoc, yLoc, index, type;
    private Building building;
    private GreenfootImage sprite;
    private int hp = DEFAULT_HP;
    
    public BuildingSlot(int x, int y, int index) {
        xLoc = x;
        yLoc = y;
        this.index = index;
        
        // Initialize all buildings as empty slots
        type = EMPTY;
        building = new Empty();
        sprite = sprite = building.getSprite();
        setImage(sprite);
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
                sprite = building.getSprite();
                break;
            case MINE:
                this.type = MINE; 
                building = new Mine();
                sprite = building.getSprite();
                break;
            case STORAGE:
                this.type = STORAGE; 
                building = new Storage();
                sprite = building.getSprite();
                break;
            case HOUSE:
                this.type = HOUSE; 
                building = new House(this);
                sprite = building.getSprite();
                break;
            case EMPTY:
                this.type = EMPTY; 
                building = new Empty();
                sprite = building.getSprite();
                break;
        }
        setImage(sprite);
    }
    
    public void damage(int damage) {
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
    }
    
    public void _update() {
        building._update();
    }
}
