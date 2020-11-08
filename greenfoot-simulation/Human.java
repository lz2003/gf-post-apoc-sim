import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Superclass for the humans, who are beings who try and survive in the 
 * world by building structures and collecting resources.
 * 
 * @author Lucy Zhao
 * @author Young Chen
 * @version 2020-11-08
 */
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
            
    public static final int
        BUILDER_WORK_TIME = 100,
        FARMER_WORK_TIME = 200,
        LUMBERJACK_WORK_TIME = 75, 
        MINER_WORK_TIME = 200;
           
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
    
    // Related to human behaviour and working
    protected BuildingSlot targetBuilding;
    protected boolean atLocation = false, enroute = false;
    protected int targetX = 0, targetY = 0;
    protected int buildingType;
    protected boolean isWorking = false;
    protected StatBar workBar;
    
    // Variables related to survival
    protected float hunger = 15f;
    protected boolean isStarving = false;
    protected float starveDeathTime = 10f;
    protected int hp = DEFAULT_HP, type;
    protected StatBar hpBar;
    
    /**
     * Essentially the act method for all human instances. Allows for
     * better control of which actors act first.
     */
    public abstract void _update();
    
    /**
     * Each human has their own work method as they gain different
     * resources and take different amounts of time to complete their
     * tasks.
     */
    protected abstract void work();

    /**
     * Moves the human to the chosen location
     * 
     * @param xDest     the x destination
     * @param yDest     the y destination
     */
    protected void moveTo(int xDest, int yDest) {
        turnTo(xDest, yDest);
 
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
     * Turns the human to the direction it is moving towards
     * 
     * @param x     the x destination
     * @param y     the y destination
     */
    protected void turnTo(int x, int y)
    {
        if (!isWorking || !atLocation)
        {
            float angleTo = Utils.getAngleTo(xLoc, x, yLoc, y);
            this.setRotation((int) (angleTo * (180 / Math.PI)));
        }
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
     * Checks the human's route, if there is none, select a building
     * or random location for the human to move to.
     * 
     * @param buildngID     the type of building
     * @param xLoc          the x location to check
     * @param yLoc          the y location to check
     */
    protected void checkRoute(int buildngID, int xLoc, int yLoc)
    {
        if (!enroute)
        {
            targetBuilding = getNearestBuilding(buildngID, xLoc, yLoc);
            if (targetBuilding != null)
            {
                targetBuilding.setTargetStatus(true);
                targetX = targetBuilding.getX();
                targetY = targetBuilding.getY();
            }
            else // Move randomly
            {
                targetX = Math.abs((int) (Math.random() * 2000) % 500);
                targetY = Math.abs((int) (Math.random() * 2000) % 500);
            }
            enroute = true;
        }
    }

    /**
     * Causes the human instance to lose a specified number of health 
     * points.
     * 
     * @param damage    the value of hp lost
     */
    public void damage(int val) {
        hp -= val;
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
    
    protected StatBar getWorkBar()
    {
        return workBar;
    }
    
    protected StatBar getHealthBar()
    {
        return hpBar;
    }
    
    /**
     * Checks if the human has reached targeted location.
     * 
     * @param xDest     the x destination 
     * @param yDest     the y destination
     */
    protected void checkIsAtLocation(int xDest, int yDest) {
        atLocation = (Utils.calcDist(xLoc, xDest, yLoc, yDest) < DEFAULT_SPEED);
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
