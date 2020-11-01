public class BuildingSlot {
    public static final int
            EMPTY = -1,
            ARMOURY = 0,
            BARRACKS = 1,
            FARM = 2,
            MINE = 3,
            SENTRY = 4,
            STORAGE = 5,
            HOUSE = 6;

    private int xLoc, yLoc, index, type;
    private Building building;
    private BuildingSprite sprite;


    public BuildingSlot(int x, int y, int index) {
        xLoc = x;
        yLoc = y;
        this.index = index;
        
        type = EMPTY;
        building = new Empty();
        sprite = new BuildingSprite();
        WorldManagement.world.addObject(sprite, x, y);
    }

    public int getType() {
        return type;
    }

    public int getX() {
        return xLoc;
    }

    public int getY() {
        return yLoc;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(int buildingID) {
        switch(buildingID) {
            case ARMOURY:
                this.type = ARMOURY;
                building = new Armoury();
                break;
            case BARRACKS:
                this.type = BARRACKS;
                building = new Barracks();
                break;
            case FARM:
                this.type = FARM; 
                building = new Farm();
                break;
            case MINE:
                this.type = MINE; 
                building = new Mine();
                break;
            case SENTRY:
                this.type = SENTRY; 
                building = new Sentry();
                break;
            case STORAGE:
                this.type = STORAGE; 
                building = new Storage();
                break;
            case HOUSE:
                this.type = HOUSE; 
                building = new House();
                break;
            case EMPTY:
                this.type = EMPTY; 
                building = new Empty();
                break;
        }
        sprite.setImage(this.type);
    }
    
    public BuildingSprite getSprite() {
        return sprite;
    }
    
    public void destroy() {
        building.destroy();
        WorldManagement.buildings.remove(this);
        WorldManagement.world.removeObject(sprite);
    }
    
    public void _update() {
        building._update();
    }

    public abstract class Building {
        protected static final int
            DEFAULT_HP = 300;
            
        protected int 
        staff = 0,
        hp = DEFAULT_HP;
        
        public abstract void destroy();
        public abstract void _update();
    }

    public class Armoury extends Building {
        public void _update() {
            
        }
        public void destroy() {
            
        }
    }

    public class Barracks extends Building {
        public void _update() {
            
        }
        public void destroy() {
                    
        }
    }

    public class Farm extends Building {
        public static final float PRODUCTION = 4;
        
        public void _update() {
             
        }
        
        public void destroy() {
                    
        }        
    }

    public class Mine extends Building {
        public void _update() {
            
        }
        public void destroy() {
                    
        }
    }

    public class Sentry extends Building {
        public void _update() {
            
        }
        public void destroy() {
                    
        }
    }

    public class Storage extends Building {
        public static final int CAPACITY = 100;
        
        public Storage() {
            WorldManagement.storage += CAPACITY;
        }
        public void _update() {
            
        }
        public void destroy() {
            WorldManagement.storage -= CAPACITY;
        }
    }

    public class House extends Building {
        public void _update() {
            
        }
        public void destroy() {
                    
        }
    }

    public class Empty extends Building {
        public void _update() {
            
        }
        public void destroy() {
                    
        }
    }
}
