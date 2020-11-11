import java.util.ArrayList;
import greenfoot.*;
/**
 * Class to manage all the world events
 * 
 * @author Leo Foo
 * @author Lucy Zhao
 * @author Young Chen
 * @version 2020-11-10
 */
public class WorldManagement  
{
    public static final int
            WORLD_SIZE = 700,
            CAM_SPEED = 4,
            GRID_SEPARATION = 30,
            BUILDING_SIZE = 100,
            BUILDING_PADDING = -100,
            TREE_SPAWN_RATE = 50,
            ZOMBIE_SPAWN_RATE = 2000,
            MAX_TREES = 100,
            START_FREEZE_FRAMES = 50;
            
    public static final int 
        EASY = 2,
        NORMAL = 5,
        HARD = 10;
        
    public static int difficulty = EASY;
    
    public static int 
            zombieSpawnRate = ZOMBIE_SPAWN_RATE;
    
    public static int
            camX = 0, 
            camY = 0;

    public static ArrayList
            humans  = new ArrayList<Human>(),
            buildings = new ArrayList<BuildingSlot>(),
            enemies  = new ArrayList<Enemy>(),
            trees = new ArrayList<Tree>(),
            backgrounds = new ArrayList<Background>(),
            events = new ArrayList<Event>();
            
    public static MyWorld world;
    public static ScoreBar scoreboard;

    public static float deltaTime = 0.01f, elapsed = 0f;
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
            
    private static boolean 
            hasHousingSpace = false;

    private int
            maxBuildings = 0,
            width,
            height;
            
    private int 
            freezeFrames = START_FREEZE_FRAMES;

    
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
        
        addHuman(Human.BUILDER, 350, 350);
        addHuman(Human.LUMBERJACK, 350, 400);
        addHuman(Human.LUMBERJACK, 350, 300);
        addHuman(Human.BUILDER, 300, 350);
        addHuman(Human.FARMER, 400, 350);
        addHuman(Human.FARMER, 400, 400);
        addHuman(Human.FARMER, 300, 300);
        addHuman(Human.MINER, 300, 400);
        addEvent(Event.TORNADO, -1000, -1000);
    }
    
    /**
     * Methods that updates the world.
     */
    public void _update() {
        if(freezeFrames > 0) {
            freezeFrames--;
            return;
        }
        
        setDeltaTime();
        updateCounts();
        updateLoop();
        updateSprites();
        generateTrees();
        increaseDifficulty();
        generateZombies();
        updateResources();
        updateScoreBar();
        cameraActions();
    }
    
    /**
     * Sets the difficulty
     * 
     * @param difficulty The new difficulty to set to
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    
    /**
     * Increases difficulty as time goes on
     */
    private static void increaseDifficulty() {
        zombieSpawnRate = Math.max(ZOMBIE_SPAWN_RATE - (int) (elapsed + 0.5) * difficulty, 5);
    }
    
    /**
     * Methods that updates the sprites based on camera movement.
     */
    private static void updateSprites() {
            
        for(int i = 0, n = humans.size(); i < n; i++) {
            Human human = ((Human)(humans.get(i)));
            human.setLocation(human.getX() + camX, human.getY() + camY);
            StatBar wb = human.getWorkBar();
            StatBar hp = human.getHealthBar();
            if (wb != null)
            {
                wb.setLocation(wb.getX() + camX, wb.getY() + camY);
            }
            if (hp != null)
            {
                hp.setLocation(hp.getX() + camX, hp.getY() + camY);
            }
        }
        
        for(int i = 0, n = buildings.size(); i < n; i++) {
            BuildingSlot building = ((BuildingSlot)(buildings.get(i)));
            building.setLocation(building.getX() + camX, building.getY() + camY);
            building.getBuilding().setLocation(building.getX() + camX, building.getY() + camY);
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
     * Methods that updates each human and building instance. Using an
     * _update method instead of the act method allows control over the
     * order which instances act.
     */
    private static void updateLoop() {
        for(int i = 0; i < humans.size(); i++) {
            ((Human)(humans.get(i)))._update();
        }
        
        for(int i = 0; i < buildings.size(); i++) {
            ((BuildingSlot)(buildings.get(i)))._update();
        }
        
        for(int i = 0; i < trees.size(); i++) {
            ((Tree)(trees.get(i)))._update();
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
        iron += totalMine == 0 ? 0 : ((float) totalMiners / (float) Math.max(totalMine, 1f)) * totalMine * BuildingSlot.MINE_PRODUCTION * deltaTime / 2.5;    
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
        int width = this.width + this.width / 4;
        for(int x = BUILDING_PADDING - this.width / 4; x < width - BUILDING_PADDING; x += GRID_SEPARATION + BUILDING_SIZE) {
            int columnOffset = (int) (Math.random() * 230141) % 100;
            for(int y = BUILDING_PADDING - this.width / 4; y < width - BUILDING_PADDING; y += GRID_SEPARATION + BUILDING_SIZE){
                int xOffset = generateOffset();
                int yOffset = generateOffset() + columnOffset;
                BuildingSlot building = new BuildingSlot(x+xOffset, y+yOffset, maxBuildings);
                buildings.add(building);
                world.addObject((BuildingSlot)buildings.get(buildings.size()-1), x+xOffset, y+yOffset);
                maxBuildings++;
            }
        }
    }
    
    /**
     * Initializes the ScoreBar that displays the stats of the world.
     */
    private void initScoreBar()
    {
        scoreboard = new ScoreBar(width + 80, height/20);
        world.addObject(scoreboard, width/2 - 30, height/20/2);
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
     * Generates a random offset for buildings
     * 
     * @return int  the value of the offset
     */
    public int generateOffset()
    {
        int offset = (int) (Math.random() * (GRID_SEPARATION/2));
        if ((int)(Math.random() * 2) == 0) // negative offset
        {
            offset = -offset;
        }
        return offset;
    }
    
    /**
     * Generates and places trees based on a random seed.
     */
    private void generateTrees() {
        float rand = (float) (Math.random() * 12345);    
        int randInInt = (int) rand % TREE_SPAWN_RATE;
        if(randInInt == 1) {
            rand = (float) Math.random() * 20321;
            int min = -WORLD_SIZE;
            int max = WORLD_SIZE * 3;
            int xTree = min + (((int)(rand * 981)) % max);
            int yTree = min + (((int)(rand * 231)) % max);
            trees.add(new Tree(xTree, yTree));
            world.addObject((Tree)trees.get(trees.size()-1), xTree, yTree);
        }
    }
    
    /**
     * Generates zombies at the world border
     */
    private void generateZombies() {
        float rand = (float) (Math.random() * 67891);    
        float randInInt = (int) rand % zombieSpawnRate;
        if(randInInt == 1) {
            int corner = (int) (Math.random() * 981927) % 4;
            int randLoc = ((int) (rand * 129) % (WORLD_SIZE * 3)) - WORLD_SIZE;
            int max = WORLD_SIZE * 2;
            int min = -WORLD_SIZE;
            switch(corner) {
                // top right
                case 0:
                    addEvent(Event.ZOMBIE, randLoc, min);
                    break;
                // bot 
                case 1:
                    addEvent(Event.ZOMBIE, randLoc, max);
                    break;
                // right
                case 2:
                    addEvent(Event.ZOMBIE, max, randLoc);
                    break;
                // left
                case 3:
                    addEvent(Event.ZOMBIE, min, randLoc);
                    break;
            }
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
        sentryDemand = totalSentry;
        storageDemand = totalStorage - 0.1f;

        float[] demands = {farmDemand, houseDemand, mineDemand, sentryDemand, storageDemand};
        int[] demandNames = {BuildingSlot.FARM, BuildingSlot.HOUSE, BuildingSlot.MINE, BuildingSlot.SENTRY, BuildingSlot.STORAGE};

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
            case Event.ZOMBIE:
                events.add(new Zombie(xLoc, yLoc));
                world.addObject((Zombie)events.get(events.size() - 1), xLoc, yLoc);
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
     * Returns a list of all the current human instances
     * 
     * @return ArrayList<Human> the list containing all human instances
     */
    public static ArrayList<Human> getHumans() {
        return humans;
    }
    
    /**
     * Gets the event at specified index
     * 
     * @param index Index of event
     * 
     * @return Event at index
     */
    public static Event getEvent(int index) {
        return (Event)(events.get(index));
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
