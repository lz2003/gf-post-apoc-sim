import java.util.ArrayList;
import greenfoot.*;

public class WorldManagement {
    public static final int
            WORLD_SIZE = 1200,
            CAM_SPEED = 2,
            GRID_SEPARATION = 10,
            BUILDING_SIZE = 50,
            BUILDING_PADDING = 135,
            TREE_SPAWN_RATE = 50;
            
    public static int
    camX = 0, camY = 0;

    public static ArrayList
            humans  = new ArrayList<Human>(),
            buildings = new ArrayList<BuildingSlot>(),
            enemies  = new ArrayList<EnemyData>(),
            trees = new ArrayList<Tree>(),
            backgrounds = new ArrayList<BackgroundSprite>();
            
    public static MyWorld world;

    public static float deltaTime = 0.01f, elapsed = 0;
    public static long lastTime = 0;

    public static float threatLevel;

    public static float
            armouryDemand,
            farmDemand,
            mineDemand,
            houseDemand,
            sentryDemand,
            storageDemand;

    public static int
            highestDemand;

    public static int
            totalArmoury,
            totalBarracks,
            totalWood,
            totalFarm,
            totalMine,
            totalHouse,
            totalSentry,
            totalStorage;
            
    public static int 
            totalFarmers,
            totalMiners,
            totalBuilders,
            totalGuards,
            totalLumberjacks;
            
    public static final float
            POP = 0f,
            FOOD = 100f,
            WOOD = 200f,
            IRON = 0f,
            STORAGE = 100f,
            HOUSING = 0f;
            
    public static float
            pop,
            food = 100f,
            wood = 200f,
            iron,
            storage = 100f,
            housing;

    private int
            maxBuildings = 0,
            width,
            height;

    public WorldManagement(int worldWidth, int worldHeight, MyWorld world) {
        width = worldWidth;
        height = worldHeight;
        this.world = world;
    }
    
    public void init() {
        initBackgrounds();
        initBuildings();
        
        addHuman(Human.BUILDER, 33, 2);
        addHuman(Human.LUMBERJACK, 400, 400);
        addHuman(Human.FARMER, 400, 400);
    }

    public void _update() {
        updateCounts();
        updateLoop();
        updateSprites();
        generateTrees();
        cameraActions();
    }
    
    private static void updateSprites() {
        for(int i = 0, n = humans.size(); i < n; i++) {
            Human human = ((Human)(humans.get(i)));
            human.getSprite().setLocation(human.getX() + camX, human.getY() + camY);
        }
        
        for(int i = 0, n = buildings.size(); i < n; i++) {
            BuildingSlot building = ((BuildingSlot)(buildings.get(i)));
            building.getSprite().setLocation(building.getX() + camX, building.getY() + camY);
        }
        
        for(int i = 0, n = trees.size(); i < n; i++) {
            Tree tree = ((Tree)(trees.get(i)));
            tree.getSprite().setLocation(tree.getX() + camX, tree.getY() + camY);
        }
        
        for(int i = 0, n = backgrounds.size(); i < n; i++) {
            BackgroundData bg = ((BackgroundData)(backgrounds.get(i)));
            bg.getSprite().setLocation(bg.getX() + camX,bg.getY() + camY);
        }
    }
    
    private static void updateLoop() {
        for(int i = 0, n = humans.size(); i < n; i++) {
            ((Human)(humans.get(i)))._update();
        }
        
        for(int i = 0, n = buildings.size(); i < n; i++) {
            ((BuildingSlot)(buildings.get(i)))._update();
        }
    }

    private static void updateCounts() {
        pop = humans.size();
        setBuildingCounts();
        setHumanCounts();
        limitResources();
        calculateDemand();
    }
    
    private static void updateResources() {
        food += ((float) totalFarmers / (float) Math.max(totalFarm, 1f)) * BuildingSlot.Farm.PRODUCTION * deltaTime;
    }
    
    private void cameraActions() {
        if(Greenfoot.isKeyDown("w")) {
            camY += CAM_SPEED;
        }
        if(Greenfoot.isKeyDown("a")) {
            camX += CAM_SPEED;
        }
        if(Greenfoot.isKeyDown("s")) {
            camY -= CAM_SPEED;
        }
        if(Greenfoot.isKeyDown("d")) {
            camX -= CAM_SPEED;
        }
    }
    
    private void generateTrees() {
        float rand = (float) (Math.random() * 12345);    
        int randInInt = (int) rand % TREE_SPAWN_RATE;
        if(randInInt == 1) {
            trees.add(new Tree(rand));
        }
    }

    public static BuildingSlot getBuildingSlot(int index) {
        return (BuildingSlot)(buildings.get(index));
    }

    public static ArrayList<BuildingSlot> getBuildings() {
        return buildings;
    }

    public static Human getHuman(int index) {
        return (Human)(humans.get(index));
    }

    public static ArrayList<Human> getHumans() {
        return humans;
    }
    
    public static void addHuman(int humanID, int xLoc, int yLoc) {
           switch(humanID) {
               case Human.BUILDER: 
                   humans.add(new Human.Builder(xLoc, yLoc));
                   break;
               case Human.FARMER: 
                   humans.add(new Human.Farmer(xLoc, yLoc));
                   break;
               case Human.LUMBERJACK: 
                   humans.add(new Human.Lumberjack(xLoc, yLoc));
                   break;
               case Human.MINER: 
                   humans.add(new Human.Miner(xLoc, yLoc));
                   break;
           }
    }
    
    private void initBackgrounds() {
        int width = world.getWidth();
        int height = world.getHeight();
        for(int x = -1; x < 2; x++) {
            for(int y = -1; y < 2; y++) {
                backgrounds.add(new BackgroundData(x * width + width / 2, y * height + height / 2));
            }
        }
    }

    private void initBuildings() {
        for(int x = BUILDING_PADDING; x < width - BUILDING_PADDING; x += GRID_SEPARATION + BUILDING_SIZE) {
            for(int y = BUILDING_PADDING; y < width - BUILDING_PADDING; y += GRID_SEPARATION + BUILDING_SIZE){
                BuildingSlot building = new BuildingSlot(x, y, maxBuildings);
                buildings.add(building);
                maxBuildings++;
            }
        }
    }

    public static void calculateDemand() {
        limitResources();
        farmDemand = totalFarm;
        houseDemand = totalHouse;
        mineDemand = totalMine;
        sentryDemand = totalSentry;
        storageDemand = totalStorage - 0.1f;
        // how do you do json objects in java
        float[] demands = {farmDemand, houseDemand, mineDemand, sentryDemand, storageDemand};
        int[] demandNames = {BuildingSlot.FARM, BuildingSlot.HOUSE, BuildingSlot.MINE,
                BuildingSlot.SENTRY, BuildingSlot.STORAGE};

        float smallest = 99999f;
        int index = 0;
        for(int i = 0, n = demands.length; i < n; i++) {
            if(demands[i] < smallest) {
                smallest = demands[i];
                index = i;
            }
        }

        highestDemand = demandNames[index];
    }

    public static void limitResources() {
        if(wood > storage) {
            wood = storage;
        }
        if(food > storage) {
            food = storage;
        }
        if(iron > storage) {
            iron = storage;
        }

        if(food < 0) {
            food = 0;
        }
        if(wood < 0) {
            wood = 0;
        }
        if(iron < 0) {
            iron = 0;
        }
    }

    private void setDeltaTime() {
        long curT = System.currentTimeMillis();
        long delta = curT - lastTime;
        lastTime = curT;
        // initial calculation will return the time from epoch to the current time, so if its more than 10s, return 1
        if(delta > 10000) {
            deltaTime = 0.001f;
            return;
        }
        deltaTime = delta / 1000f; // milliseconds to seconds
        elapsed += deltaTime;
    }
    
    private static void setHumanCounts() {
        
        totalFarmers = 0;
        totalMiners = 0;
        totalBuilders = 0;
        totalGuards = 0;
        totalLumberjacks = 0; 
        for(int i = 0, n = humans.size(); i < n; i++) {
            switch(getHuman(i).getType()) {
               case Human.BUILDER: 
                   totalBuilders++;
                   break;
               case Human.FARMER: 
                   totalFarmers++;
                   break;
               case Human.LUMBERJACK: 
                   totalLumberjacks++;
                   break;
               case Human.MINER: 
                   totalMiners++;
                   break;
           }
        }
    }

    private static void setBuildingCounts() {
        ArrayList<BuildingSlot> arr = getBuildings();
        totalArmoury = 0;
        totalBarracks = 0;
        totalFarm = 0;
        totalMine = 0;
        totalHouse = 0;
        totalSentry = 0;
        totalStorage = 0;
        
        for(int i = 0, n = arr.size(); i < n; i++) {
   
            switch(arr.get(i).getType()) {
                case BuildingSlot.ARMOURY:
                    totalArmoury++;
                    break;
                case BuildingSlot.BARRACKS:
                    totalBarracks++;
                    break;
                case BuildingSlot.FARM:
                    totalFarm++;
                    break;
                case BuildingSlot.MINE:
                    totalMine++;
                    break;
                case BuildingSlot.SENTRY:
                    totalSentry++;
                    break;
                case BuildingSlot.STORAGE:
                    totalStorage++;
                    break;
                case BuildingSlot.HOUSE:
                    totalHouse++;
                    break;
            }
        }
    }
}
