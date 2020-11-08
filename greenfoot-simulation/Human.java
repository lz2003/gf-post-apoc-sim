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
            FULL_HUNGER = 25,
            STARVE_TIME = 10;

    protected int xLoc, yLoc, xVel, yVel;
    protected int nearestIndex;
    protected int speed = (int) DEFAULT_SPEED;
    
    // "Worker" Class variables
    protected BuildingSlot targetBuilding;
    protected boolean atBuilding = false, enRoute = false;
    protected int targetX = 600, targetY = 0;
    protected int buildingType;
    
    protected float hunger = 15f;
    protected boolean isStarving = false;
    protected float starveDeathTime = 10f;
    protected int hp = DEFAULT_HP, type;
    protected GreenfootImage sprite;
    

    public abstract void _update();

    /**
     * Moves the human to the chosen location
     * 
     * @param xDest     the x destination
     * @param yDest     the y destination
     */
    protected void moveTo(int xDest, int yDest) {
        xVel = 0;
        yVel = 0;
        int toX = xDest, toY = yDest;
        int x = xLoc, y = yLoc;
        if(x < toX) {
            xVel = (int) speed;
        } else if(x > toX) {
            xVel = (int) -speed;
        }

        if(y < toY) {
            yVel = (int) speed;
        } else if(y > toY) {
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
        int lowest = 9999, index = 0;
        for(int i = 0, n = WorldManagement.buildings.size(); i < n; i++) {
            BuildingSlot building = (BuildingSlot)(WorldManagement.buildings.get(i));
            int xLoc = building.getX(), yLoc = building.getY();
            int distance = Utils.calcDist(x, xLoc, y, yLoc);
            if(distance < lowest && building.getType() == buildngID) {
                lowest = distance;
                index = i;
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
                lowest = distance;
                index = i;
            }
        }
        nearestIndex = index;
        
        return index != -1 ? WorldManagement.getBuildingSlot(index) : null;
    }
    
    /**
     * Returns the nearest building of a specified type based on a
     * randomized starting location.
     * 
     * @param buildngID     the type of building to be found
     * @return BuildingSlot the closest building
     */
    protected BuildingSlot getRandBuildingOfType(int buildingID) {
        int targetX = (int) (Math.random() * 3000 % WorldManagement.world.getWidth());
        int targetY = (int) (Math.random() * 3000 % WorldManagement.world.getHeight());
        return getNearestBuilding(buildingID, targetX, targetY);
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
        WorldManagement.humans.remove(this);
        WorldManagement.world.removeObject(this);
    }
        
    /**
     * Causes human instance to move towards a random building.
     */ 
    public void goToRandBuilding() {
        setAtBuilding();
        if(!atBuilding) {
            if(!enRoute) {
                targetBuilding = getRandBuildingOfType(buildingType);
                enRoute = true;
            }
        } else  {
            enRoute = false;
            if(!enRoute) {
                targetBuilding = getRandBuildingOfType(buildingType);
                enRoute = true;
            }
        }
        if(targetBuilding != null) {
            moveTo(targetBuilding.getX(), targetBuilding.getY());
        }
    }
    
    /**
     * Determines if human has reached target building.
     */ 
    private void setAtBuilding() {
        if(targetBuilding != null) {
            atBuilding = Utils.calcDist(xLoc, targetBuilding.getX(), yLoc, targetBuilding.getY()) < DEFAULT_SPEED;
        }
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
     * Returns the type of human
     * 
     * @return int  the type of the human
     */    
    public int getType() {
        return type;
    }
}
