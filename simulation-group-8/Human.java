public abstract class Human {
    public static final int
            BUILDER = 0,
            FARMER = 1,
            LUMBERJACK = 2, 
            MINER = 3;
            
    public static final int
            TOTAL_HUMAN_TYPES = 4;
    
    protected static final int
            DEFAULT_HP = 100;

    protected static final float
            FOOD_BIAS = 1.5f,
            HOUSE_BIAS = 1.4f,
            DEFAULT_SPEED = 1,
            FULL_HUNGER = 25,
            STARVE_TIME = 10;

    protected int xLoc, yLoc, xVel, yVel;
    protected int nearestIndex;
    protected int speed = (int) DEFAULT_SPEED;

    protected float hunger = 15f;
    protected boolean isStarving = false;
    protected float starveDeathTime = 10f;
    protected int type;
    protected HumanSprite sprite;

    public abstract void _update();

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

    protected BuildingSlot getNearestBuilding(int buildngID, int x, int y) {
        int lowest = 9999, index = 0;
        for(int i = 0, n = WorldManagement.buildings.size(); i < n; i++) {
            BuildingSlot building = (BuildingSlot)(WorldManagement.buildings.get(i));
            int xLoc = building.getX(), yLoc = building.getY();
            int distance = calcDist(x, xLoc, y, yLoc);
            if(distance < lowest && building.getType() == buildngID) {
                lowest = distance;
                index = i;
            }
        }
        nearestIndex = index;
        return WorldManagement.getBuildingSlot(index);
    }

    protected BuildingSlot getNearestBuilding(int buildngID) {
        int x = xLoc;
        int y = yLoc;

        int lowest = 9999, index = -1;
        for(int i = 0, n = WorldManagement.buildings.size(); i < n; i++) {
            BuildingSlot building = (BuildingSlot)(WorldManagement.buildings.get(i));
            int xLoc = building.getX(), yLoc = building.getY();
            int distance = calcDist(x, xLoc, y, yLoc);
            if(distance < lowest && building.getType() == buildngID) {
                lowest = distance;
                index = i;
            }
        }
        nearestIndex = index;
        
        return index != -1 ? WorldManagement.getBuildingSlot(index) : null;
    }

    protected int calcDist(int ax, int bx, int ay, int by) {
        return (int) Math.sqrt(Math.pow(ax - bx, 2) + Math.pow(ay - by, 2));
    }

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
    
    protected BuildingSlot getRandBuildingOfType(int buildingID) {
        int targetX = (int) (Math.random() * 3000 % WorldManagement.world.getWidth());
        int targetY = (int) (Math.random() * 3000 % WorldManagement.world.getHeight());
        return getNearestBuilding(buildingID, targetX, targetY);
    }

    public void die() {
        WorldManagement.humans.remove(this);
        WorldManagement.world.removeObject(sprite);
    }
    
    public int getX() {
        return xLoc;
    }
    
    public int getY() {
        return yLoc;
    }
    
    public int getType() {
        return type;
    }
    
    public HumanSprite getSprite() {
        return sprite;
    }
}
