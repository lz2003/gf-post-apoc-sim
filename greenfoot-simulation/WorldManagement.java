
import java.util.ArrayList;
import greenfoot.*;
/**
 * Write a description of class WorldManagement here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WorldManagement  
{
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
            enemies  = new ArrayList<Enemy>(),
            trees = new ArrayList<Tree>(),
            backgrounds = new ArrayList<Background>(),
            events = new ArrayList<Event>();
            
    public static MyWorld world;
    public static ScoreBar scoreboard;

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
            totalLumberjacks;
            
    public static final float
            START_POP = 0f,
            START_FOOD = 100f,
            START_WOOD = 200f,
            START_IRON = 0f,
            START_STORAGE = 100f,
            START_HOUSING = 0f;
            
    public static float
            pop = 0f,
            food = 100f,
            wood = 200f,
            iron = 0f,
            storage = 100f,
            housing = 0f;

    private int
            maxBuildings = 0,
            width,
            height;
    
    private static boolean hasHousingSpace = false;
    
    /**
     * Constructor of the WorldManagement Class. Use to set up the world
     */
    public WorldManagement(int worldWidth, int worldHeight, MyWorld world) {
        width = worldWidth;
        height = worldHeight;
        this.world = world;
    }
    
    /**
     * Initializes the assets of the world. This includes
     */
    public void init() {
        initBackgrounds();
        initBuildings();
        initScoreBar();
        
        addHuman(Human.BUILDER, 33, 2);
        addHuman(Human.LUMBERJACK, 400, 400);
        addHuman(Human.FARMER, 400, 400);
        addHuman(Human.FARMER, 200, 400);
        addHuman(Human.FARMER, 300, 400);
        addHuman(Human.MINER, 300, 300);
        addEvent(Event.TORNADO, 100, 500);
    }
    
    /**
     * Methods that updates the world.
     */
    public void _update() {
        updateCounts();
        updateLoop();
        updateSprites();
        generateTrees();
        updateResources();
        updateScoreBar();
        cameraActions();
    }
    
    /**
     * Methods that updates the sprites based on camera movement.
     */
    private static void updateSprites() {
            
        for(int i = 0, n = humans.size(); i < n; i++) {
            Human human = ((Human)(humans.get(i)));
            human.setLocation(human.getX() + camX, human.getY() + camY);
        }
        
        for(int i = 0, n = buildings.size(); i < n; i++) {
            BuildingSlot building = ((BuildingSlot)(buildings.get(i)));
            building.setLocation(building.getX() + camX, building.getY() + camY);
        }
        
        for(int i = 0, n = trees.size(); i < n; i++) {
            Tree tree = ((Tree)(trees.get(i)));
            tree.setLocation(tree.getX() + camX, tree.getY() + camY);
        }
        
        for(int i = 0, n = backgrounds.size(); i < n; i++) {
            Background bg = ((Background)(backgrounds.get(i)));
            bg.setLocation(bg.getX() + camX,bg.getY() + camY);
        }
        
        for(int i = 0, n = events.size(); i < n; i++) {
            Event e = ((Event)(events.get(i)));
            e.setLocation(e.getX() + camX,e.getY() + camY);
        }
    }
    
    /**
     * Methods that updates each human and building instance.
     */
    private static void updateLoop() {
        for(int i = 0; i < humans.size(); i++) {
            ((Human)(humans.get(i)))._update();
        }
        
        for(int i = 0; i < buildings.size(); i++) {
            ((BuildingSlot)(buildings.get(i)))._update();
        }
        
        for(int i = 0; i < events.size(); i++) {
            ((Event)(events.get(i)))._update();
        }
    }
    
    /**
     * Method that updated the numbers/variables of the game. This
     * includes population, buildings, resources and demand,
     */
    private static void updateCounts() {
        pop = humans.size();
        setBuildingCounts();
        setHumanCounts();
        limitResources();
        calculateDemand();
    }
    
    /**
     * Method that updates resources produced from structures.
     */
    private static void updateResources() {
        food += totalFarm == 0 ? 0 : ((float) totalFarmers / (float) Math.max(totalFarm, 1f)) * totalFarm * BuildingSlot.FARM_PRODUCTION * deltaTime;
        iron += totalMine == 0 ? 0 : ((float) totalMiners / (float) Math.max(totalMine, 1f)) * totalMine * BuildingSlot.MINE_PRODUCTION * deltaTime;    
    }
    
    /**
     * Method that updates the scorebar and its stats.
     */
    private static void updateScoreBar()
    {
        scoreboard.updateStat("Population", (int) pop);
        scoreboard.updateStat("Wood", (int) wood);
        scoreboard.updateStat("Iron", (int) iron);
        scoreboard.updateStat("Food", (int) food);
    }
    
    /**
     * Method that intializes the background of the world.
     */
    private void initBackgrounds() {
        int width = world.getWidth();
        int height = world.getHeight();
        for(int x = -1; x < 2; x++) {
            for(int y = -1; y < 2; y++) {
                backgrounds.add(new Background(x * width + width / 2, y * height + height / 2));
                world.addObject((Background)backgrounds.get(backgrounds.size()-1), x * width + width / 2, y * height + height / 2);
            }
        }
    }
    
    /**
     * Initializes the building slots on the world.
     */
    private void initBuildings() {
        for(int x = BUILDING_PADDING; x < width - BUILDING_PADDING; x += GRID_SEPARATION + BUILDING_SIZE) {
            for(int y = BUILDING_PADDING; y < width - BUILDING_PADDING; y += GRID_SEPARATION + BUILDING_SIZE){
                BuildingSlot building = new BuildingSlot(x, y, maxBuildings);
                buildings.add(building);
                world.addObject((BuildingSlot)buildings.get(buildings.size()-1), x, y);
                maxBuildings++;
            }
        }
    }
    
    /**
     * Initializes the ScoreBar that displays the stats of the world.
     */
    private void initScoreBar()
    {
        scoreboard = new ScoreBar(width, height/20);
        world.addObject(scoreboard, width/2, height/20/2);
        scoreboard.addStat("Population", (int) pop);
        scoreboard.addStat("Wood", (int) wood);
        scoreboard.addStat("Iron", (int) iron);
        scoreboard.addStat("Food", (int) food);
    }
    
    /**
     * Controls the camera's movement.
     */
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
        int width = world.getWidth();
        int height = world.getHeight();
        // Clamp camera to within space with background
        camX = camX < -width ? -width : camX > width ? width : camX;
        camY = camY < -width ? -width : camY > width ? width : camY;
    }
    
    /**
     * Generates and places trees based on a random seed.
     */
    private void generateTrees() {
        float rand = (float) (Math.random() * 12345);    
        int randInInt = (int) rand % TREE_SPAWN_RATE;
        if(randInInt == 1) {
            rand = (float) Math.random() * 20321;
            int xTree = ((int) (rand * 1352)) % WORLD_SIZE - (int)((float) WORLD_SIZE * 0.2f);
            int yTree = ((int) (rand * 6112)) % WORLD_SIZE - (int)((float) WORLD_SIZE * 0.2f);
            trees.add(new Tree(xTree, yTree));
            world.addObject((Tree)trees.get(trees.size()-1), xTree, yTree);
        }
    }
    
    /**
     * Limits the resources if either the resource goes over the
     * max capacity or less than zero.
     */
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
    
    /**
     * Calculates the demand for each building
     */
    public static void calculateDemand() {
        limitResources();
        farmDemand = totalFarm * 0.4f;
        houseDemand = totalHouse;
        mineDemand = totalMine;
        //sentryDemand = totalSentry;
        storageDemand = totalStorage - 0.1f;
        // how do you do json objects in java
        float[] demands = {farmDemand, houseDemand, mineDemand, storageDemand};
        int[] demandNames = {BuildingSlot.FARM, BuildingSlot.HOUSE, BuildingSlot.MINE, BuildingSlot.STORAGE};

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
    
    /**
     * Adds a human instance to the world.
     * 
     * @param humanID   the type of human to be added
     * @param xLoc      the x location of the human
     * @param yLoc      the y location of the human
     */
    public static void addHuman(int humanID, int xLoc, int yLoc) {
           switch(humanID) {
               case Human.BUILDER: 
                   humans.add(new Builder(xLoc, yLoc));
                   world.addObject((Builder)humans.get(humans.size()-1), xLoc, yLoc);
                   break;
               case Human.FARMER: 
                   humans.add(new Farmer(xLoc, yLoc));
                   world.addObject((Farmer)humans.get(humans.size()-1), xLoc, yLoc);
                   break;
               case Human.LUMBERJACK: 
                   humans.add(new Lumberjack(xLoc, yLoc));
                   world.addObject((Lumberjack)humans.get(humans.size()-1), xLoc, yLoc);
                   break;
               case Human.MINER: 
                   humans.add(new Miner(xLoc, yLoc));
                   world.addObject((Miner)humans.get(humans.size()-1), xLoc, yLoc);
                   break;
           }
    }
    
    /**
     * Adds an event to the world.
     * 
     * @param eventID   the type of human to be added
     * @param xLoc      the x location of the human
     * @param yLoc      the y location of the human
     */
    public static void addEvent(int eventID, int xLoc, int yLoc) {
        switch(eventID) {
            case Event.TORNADO: 
                events.add(new Tornado(xLoc, yLoc));
                world.addObject((Tornado)events.get(events.size() - 1), xLoc, yLoc);
                break;
            case Event.METEOR:
                events.add(new Meteor());
                world.addObject((Meteor)events.get(events.size() - 1), xLoc, yLoc);
                break;
        }
    }
    
    /**
     * Returns a human instance at a specified index from the humans
     * Arraylist
     * 
     * @param index     the index of the Arraylist
     * @return Human    the human instance at that index
     */
    public static Human getHuman(int index) {
        return (Human)(humans.get(index));
    }

    /**
     * Returns a list of all th current human instances
     * 
     * @return ArrayList<Human> the list containing all human instances
     */
    public static ArrayList<Human> getHumans() {
        return humans;
    }
    
    /**
     * Calculates the total number of existing humans.
     */
    private static void setHumanCounts() {
        
        totalFarmers = 0;
        totalMiners = 0;
        totalBuilders = 0;
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
    
    /**
     * Calculates the total number of existing buildings.
     */
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
        housing = totalHouse * BuildingSlot.HOUSE_CAPACITY;
        storage = totalStorage * BuildingSlot.STORAGE_CAPACITY + START_STORAGE;
        
        checkHousingSpace();
    }
    
    /**
     * Checks if there is housing space
     */
    private static void checkHousingSpace() {
        hasHousingSpace = pop < housing ? true : false;
    }
    
    /**
     * @return hasHousingSpace
     */
    public static boolean hasHousing() {
        return hasHousingSpace;
    }
    
    /**
     * Returns the building slot at a specified index.
     * 
     * @param index             the index of the ArrayList
     * @return BuildingSlot     the building slot at that index
     */
    public static BuildingSlot getBuildingSlot(int index) {
        return (BuildingSlot)(buildings.get(index));
    }

    /**
     * Returns an ArrayList of all existing buildings.
     * 
     * @return ArrayList<BuildingSlot>  list of all buildings
     */
    public static ArrayList<BuildingSlot> getBuildings() {
        return buildings;
    }
    
    /**
     * Set the delta time and updates the time elapsed.
     */
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
    
}
