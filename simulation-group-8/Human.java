public abstract class Human {
    public static final int
            BUILDER = 0,
            FARMER = 1,
            LUMBERJACK = 3, 
            MINER = 4;
    
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
    
    // BECAUSE this thing called greenfoot does not allow any type of file management,

    public static class Builder extends Human {
        private BuildingSlot nearest;
        private boolean atBuilding;
        
        public Builder(int xLoc, int yLoc) {
            this.xLoc = xLoc;
            this.yLoc = yLoc;
            this.type = BUILDER;
            
            sprite = new HumanSprite(BUILDER);
            WorldManagement.world.addObject(sprite, xLoc, yLoc);
        }

        public void _update() {
            checkIsAtBuilding();
            build();
            moveTo(nearest.getX(), nearest.getY());
            drainFood();
        }

        private void build() { 
            if(atBuilding) { 
                if(WorldManagement.getBuildingSlot(nearestIndex).getType() == BuildingSlot.EMPTY) {
                    if(WorldManagement.wood >= 15) {
                        WorldManagement.getBuildingSlot(nearestIndex).setBuilding(WorldManagement.highestDemand);
                        WorldManagement.wood -= 15;
                    }
                }
            }
        }

        private void checkIsAtBuilding() {
            nearest = getNearestBuilding(BuildingSlot.EMPTY, 350 ,350);
            atBuilding = (calcDist(xLoc, nearest.getX(), yLoc, nearest.getY()) < DEFAULT_SPEED);
        }
    }
    
    public static abstract class Worker extends Human {
        protected BuildingSlot targetBuilding;
        protected boolean atBuilding = false, enRoute = false;
        protected int targetX = 600, targetY = 0;
        protected int buildingType;
        
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
            drainFood();
        }
        
        private void setAtBuilding() {
            if(targetBuilding != null) {
                atBuilding = calcDist(xLoc, targetBuilding.getX(), yLoc, targetBuilding.getY()) < DEFAULT_SPEED;
            }
        }
    }

    public static class Farmer extends Worker {
        public Farmer(int xLoc, int yLoc) {
            this.xLoc = xLoc;
            this.yLoc = yLoc;
            
            this.type = FARMER;
            this.buildingType = BuildingSlot.FARM;
            
            sprite = new HumanSprite(this.type);
            WorldManagement.world.addObject(sprite, xLoc, yLoc);
        }
        public void _update() {
            goToRandBuilding();
        }
    }
    
    public static class Lumberjack extends Human {
        private int tX, tY;
        private boolean foundTree = false;
        public Lumberjack(int xLoc, int yLoc) {
            this.xLoc = xLoc;
            this.yLoc = yLoc;
            
            this.type = LUMBERJACK;
            
            sprite = new HumanSprite(this.type);
            WorldManagement.world.addObject(sprite, xLoc, yLoc);
        }   

        public void _update() {
            findTree();
            moveTo(tX, tY);
            chopTree();
            drainFood();
        }
    
        private void findTree() {
            if(WorldManagement.trees.size() > 0) {
                tX = ((Tree)WorldManagement.trees.get(0)).getX();
                tY = ((Tree)WorldManagement.trees.get(0)).getY();
                foundTree = true;
            } else {
                // else go in random direction
                tX = Math.abs((int) (Math.random() * 2000) % 500);
                tY = Math.abs((int) (Math.random() * 2000) % 500);
                foundTree = false;
            }
        }

        private void chopTree() {
            if(foundTree) {
                if(calcDist(getX(), tX, getY(), tY) < DEFAULT_SPEED) {
                    ((Tree)WorldManagement.trees.get(0)).chop();
                }
            }
        }
    }

    public static class Miner extends Human {
        public Miner(int xLoc, int yLoc) {
            this.xLoc = xLoc;
            this.yLoc = yLoc;
            
            this.type = MINER;
            
            sprite = new HumanSprite(this.type);
            WorldManagement.world.addObject(sprite, xLoc, yLoc);
        }
        public void _update() {

        }

    }
}
