import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class Human extends Actor {
    // Human IDs
    public static final int
            BUILDER = 0,
            FARMER = 1,
            LUMBERJACK = 2, 
            MINER = 3;
    
    public static final int
            TOTAL_HUMAN_TYPES = 4;
    
    protected static final int
            DEFAULT_HP = 100;
           
    // Attributes of humans
    protected static final float
            FOOD_BIAS = 1.5f,
            HOUSE_BIAS = 1.4f,
            DEFAULT_SPEED = 1,
            FULL_HUNGER = 15,
            STARVE_TIME = 10;

    // Variables that control the appearance and movement
    protected int xLoc, yLoc, xVel, yVel;
    protected int nearestIndex;
    protected int speed = (int) DEFAULT_SPEED;
    protected GreenfootImage sprite;
    
    // "Worker" Class variables
    protected BuildingSlot targetBuilding;
    protected boolean atBuilding = false, enroute = false;
    protected int targetX = 600, targetY = 0;
    protected int buildingType;
    
    // Variables related to survival
    protected float hunger = 15f;
    protected boolean isStarving = false;
    protected float starveDeathTime = 10f;
    protected int hp = DEFAULT_HP, type;
    
    /**
     * Essentially the act method for all human instances. Allows for
     * better control of which actors act first.
     */
    public abstract void _update();

    /**
     * Moves the human to the chosen location
     * 
     * @param xDest     the x destination
     * @param yDest     the y destination
     */
    protected void moveTo(int xDest, int yDest) {
        // Turns the human
        if (!atBuilding)
        {
            turnTowards(xDest + WorldManagement.camX, yDest + WorldManagement.camY);
        }
        
        xVel = 0;
        yVel = 0;
        int x = xLoc, y = yLoc;
        if(x < xDest) {
            xVel = (int) speed;
        } else if(x > xDest) {
            xVel = (int) -speed;
        }

        if(y < yDest) {
            yVel = (int) speed;
        } else if(y > yDest) {
            yVel = (int) -speed;
        }
        
        xLoc += xVel;
        yLoc += yVel;
        
    }
    
    /**
     * Returns the nearest building when given a specific starting
     * location and type.
     * 
     * @param buildngID     the type of building to be found
     * @param x             the x starting location
     * @param y             the y starting location
     * @return BuildingSlot the closest building
     */
    protected BuildingSlot getNearestBuilding(int buildngID, int x, int y) {
        int lowest = 9999;
        int index = (int) (Math.random() * WorldManagement.buildings.size());
        for(int i = 0, n = WorldManagement.buildings.size(); i < n; i++) {
            BuildingSlot building = (BuildingSlot)(WorldManagement.buildings.get(i));
            int xLoc = building.getX(), yLoc = building.getY();
            int distance = Utils.calcDist(x, xLoc, y, yLoc);
            if(distance < lowest && building.getType() == buildngID) {
                if (!building.getTargetStatus())
                {
                    lowest = distance;
                    index = i;
                }
            }
        }
        nearestIndex = index;
        return WorldManagement.getBuildingSlot(index);
    }
    
    /**
     * Returns the nearest building when given a specific building type.
     * 
     * @param buildngID     the type of building to be found
     * @return BuildingSlot the closest building
     */
    protected BuildingSlot getNearestBuilding(int buildngID) {
        int x = xLoc;
        int y = yLoc;

        int lowest = 9999, index = -1;
        for(int i = 0, n = WorldManagement.buildings.size(); i < n; i++) {
            BuildingSlot building = (BuildingSlot)(WorldManagement.buildings.get(i));
            int xLoc = building.getX(), yLoc = building.getY();
            int distance = Utils.calcDist(x, xLoc, y, yLoc);
            if(distance < lowest && building.getType() == buildngID) {
                if (!building.getTargetStatus())
                {
                    lowest = distance;
                    index = i;
                }
            }
        }
        nearestIndex = index;
        
        return index != -1 ? WorldManagement.getBuildingSlot(index) : null;
    }
    
    /**
     * Drains food (eaten) and controls whether or not humans die of
     * starvation. 
     */
    protected void drainFood() {
        if(WorldManagement.food > 0) {
            float foodEaten = Math.min((((FULL_HUNGER - hunger) / 3f) * WorldManagement.deltaTime) * 10f, WorldManagement.food);
            hunger += foodEaten;
            WorldManagement.food -= foodEaten;
        }

        if(hunger > 0) {
            hunger -= WorldManagement.deltaTime;
        } else {
            die();
        }
    }
    
    /**
     * Checks if the human's route, if there is none, select a building
     * for the human to move to.
     * 
     * @param buildngID     the type of building
     * @param xLoc          the x location to check
     * @param yLoc          the y location to check
     */
    protected void checkRoute(int buildngID, int xLoc, int yLoc)
    {
        if (!enroute)
        {
            targetBuilding = getNearestBuilding(buildngID, xLoc ,yLoc);
            targetX = targetBuilding.getX();
            targetY = targetBuilding.getY();
            enroute = true;
            targetBuilding.setTargetStatus(true);
        }
    }
    
    /**
     * Causes the human instance to lose a specified number of health 
     * points.
     * 
     * @param damage    the value of hp lost
     */
    public void damage(int damage) {
        hp -= damage;
        if(hp <= 0) {
            die();
        }
    }
    
    /**
     * Removes the human instance from the list and the world.
     */
    public void die() {
        if (targetBuilding != null)
        {
            targetBuilding.setTargetStatus(false);
        }
        WorldManagement.humans.remove(this);
        WorldManagement.world.removeObject(this);
    }
    
    /**
     * Checks if the human has reached targeted building slot
     * 
     * @param xDest     the x destination 
     * @param yDest     the y destination
     */
    protected void checkIsAtBuilding(int xDest, int yDest) {
        atBuilding = (Utils.calcDist(xLoc, xDest, yLoc, yDest) < DEFAULT_SPEED);
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
     * @return int  the y location of the human
     */
    public int getY() {
        return yLoc;
    }
   
    /**
     * Returns the type of human
     * 
     * @return int  the type of the human
     */    
    public int getType() {
        return type;
    }
}
