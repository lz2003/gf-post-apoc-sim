import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Main world class where the simulation takes place.
 *
 * This project simulates the survival of a group of humans in a post-apocalyptic world. In this simulation,
 * the humans will gather resources to keep themselves alive from starvation and any zombies that approach.
 * There are three resources present: wood, iron, and food. Wood is required to build buildings, and each
 * building requires 15 wood to build. Iron is required to fuel the sentries with ammunition, where each
 * sentry shot costs 3 iron. Food is required to keep all the humans alive. Each human consumes food every
 * update to fill up an internal hunger value, which itself decreases every update too. Once a human's hunger
 * reaches 0, the human will die. All of these resources, with the exception of wood, are generated through
 * buildings. Food is produced on farms, which generate food at a rate proportional to the ratio between
 * farmers and farms. Iron is produced in mines, which, just like the farms, produces iron at a rate proportional
 * to the number of miners. Wood, on the other hand, comes from the trees the lumberjacks cut down. Each tree
 * generates between 5 to 20 wood. There are also three other types of buildings in this simulation, namingly
 * sentries, storage, and houses. Sentries consume the aforementioned amount of iron with each shot fired to
 * protect the humans from zombies, with each shot dealing 20 damage points to their target. Storage buildings
 * set the limit for the maximum amount of each type of resource the humans can have. The starting capacity for
 * no storage buildings is 100, and with each additional storage building built, the capacity is increased by
 * 100. Houses are used to increase the population, and will only do so if the housing capacity is larger than
 * the population. Each house increases the housing capacity by 5, and once the housing capacity has grown larger
 * than the population, the houses will start spawning humans until the housing capacity is reached. Each human
 * required 15 food to spawn, and houses will only spawn a new human if the total food in the simulation is
 * greater than 25.
 *
 * There are four major categories of humans: farmers, miners, builders, and lumberjacks. Farmers seek the
 * nearest empty farm, and bind themselves to it. The same goes for miners, but with mines instead. Builders
 * look for the nearest empty building slot, which are generated when the world is first created, and build
 * the building with the highest demand on it. This demand is calculated by adding the numbers of each type of
 * building, and then taking the type of building with the lowest number. Some of these buildings, such as the
 * farm and storage, have a certain "bias" applied to them so that they're built first since they are vital to
 * the survival of the humans early on. Lumberjacks search for the nearest tree, and chop it down once they
 * get to the tree, which once chopped down generates the aforementioned amount of wood.
 *
 * Since a post-apocalyptic simulation would not be complete without some threats, this simulation has an events
 * superclass containing to subclasses that can harm the humans. The first one is the zombie, which will seek
 * the nearest human and head towards it. If it manages to reach the human, it will deal 50 damage per second to
 * the human, and if it manages to drain the human's entire hit point count of 100, the human will then become a
 * zombie and start to attack other humans. Humans will also sometimes randomly become zombies after a certain
 * period has passed. The second event subclass is the tornado, which is spawned at world creation in the top left 
 * corner of the world. The tornado moves in a random direction every few seconds, and if it manages to touch a 
 * building, tree, or human, the corresponding entity will be immediately destroyed.
 *
 * Credits for artworks used in the simulation:
 *
 * Statbar Class: MrCohen (https://www.greenfoot.org/users/3111)
 * Sprites: Lucy Zhao
 * Graphics: Lucy Zhao, Young Chen
 * Background image: Scribe (https://opengameart.org/content/topdown-tileset)
 *
 * Main menu soundtrack: https://freesound.org/people/tyops/sounds/484301/
 * Simulation soundtrack: https://freesound.org/people/frankum/sounds/317363/
 * End screen soundtrack: https://freesound.org/people/hear-no-elvis/sounds/120899/
 *
 * Sound effects:
 * Button: https://freesound.org/people/Leszek_Szary/sounds/171520/
 * Building destruction: https://freesound.org/people/ssierra1202/sounds/391961/
 * Zombie one: https://freesound.org/people/Under7dude/sounds/163440/
 * Zombie two: https://freesound.org/people/nanity05/sounds/193759/
 * Human hurt: https://freesound.org/people/AlineAudio/sounds/416839/
 * Building: https://freesound.org/people/zbig77/sounds/244985/
 * Mining: https://freesound.org/people/michorvath/sounds/270589/
 * Chopping: https://freesound.org/people/14FPanskaSilovsky_Petr/sounds/419928/
 * Sentry fire: https://freesound.org/people/Bird_man/sounds/275151/
 *
 * @author Young Chen
 * @version 2020-10-11
 */
public class Simulation extends World
{
    public static final int EASY = 0, NORMAL = 1, HARD = 2, END_DELAY = 300;
    
    public static int[] startHumans = {2, 2, 2, 1};
    
    private static GreenfootSound bgMusic = new GreenfootSound("sim_music.wav");
    private static GreenfootSound endSound = new GreenfootSound("end.wav");
    private LZTextBox end = new LZTextBox(350, 350, Color.WHITE, 19, 
    "center", 1, 140, 25, Color.BLACK, new Color(240, 40, 40));
    
    private EndScreen endScreen = new EndScreen();
    private Fade fadeOut = new Fade(false), fadeIn = new Fade(true);
    private boolean endWorld = false;
    private WorldManagement wm;
    private int endDelay = END_DELAY;
    private int difficulty;
    
    /**
     * Contructor of Simulation, creates the world where the simulation
     * takes place.
     * 
     * @param difficulty    the difficulty of the simulation
     * 
     */
    public Simulation(int difficulty)
    {    
        super(700, 700, 1, false); 
        
        this.difficulty = difficulty;
        setPaintOrder(Fade.class, EndScreen.class, LZTextBox.class, ScoreBar.class, Tornado.class, House.class, Storage.class, StatBar.class, Sentry.class, Tree.class, Zombie.class, Human.class, Farm.class, Mine.class, BuildingSlot.class);
        
        addObject(fadeOut, getWidth() / 2, getHeight() / 2);
        
        end.update(" End the World");
        end.updateText();
        addObject(end, 627, 17);
    }
    boolean runOnce = false;
    
    /**
     * Intializes the world variables, as well as checks if the simulation
     * has ended
     */
    public void act() { 
        if(!runOnce) {
            // BECAUSE GREENFOOT DOES NOT RESET VARIABLE OUTSIDE OF THE VALUES INSIDE THE ACTOR CLASS WHEN RESET IS PRESSED,
            // ALL THE INSTIANTIATING HAS BEEN MOVED TO THE ACT METHOD
            wm = new WorldManagement(getWidth(), getHeight(), this);
            wm.pop = wm.START_POP;
            wm.food = wm.START_FOOD;
            wm.wood = wm.START_WOOD;
            wm.iron = wm.START_IRON;
            wm.storage = wm.START_STORAGE;
            wm.housing = wm.START_HOUSING;
            wm.zombieSpawnRate = wm.ZOMBIE_SPAWN_RATE;
            wm.humans = new ArrayList<Human>();
            wm.buildings = new ArrayList<BuildingSlot>();
            wm.trees = new ArrayList<Tree>();
            wm.events = new ArrayList<Event>();
            wm.elapsed = 0;
            endDelay = END_DELAY;
            switch(difficulty) {
                case EASY: 
                    wm.difficulty = wm.EASY;
                    break;
                case NORMAL: 
                    wm.difficulty = wm.NORMAL;
                    break;
                case HARD: 
                    wm.difficulty = wm.HARD;
                    break;
                default:
                    wm.difficulty = wm.EASY;
            }
            wm.init();
            runOnce = true;
            fadeOut.start();
        }
        
        if(fadeOut != null) {
            if(fadeOut.isFinished()) {
                removeObject(fadeOut);
                fadeOut = null;
            }
        }
        
        if(Greenfoot.isKeyDown("escape")) {
            try {
                bgMusic.stop();
            } catch (Exception e){}
            endWorld = true;
            Greenfoot.setWorld(new Start());
        }
            
        wm._update();
        if(!endWorld) WorldManagement.playSound(bgMusic);
        checkEndWorld();
        checkEnd();
    }

    private void checkEnd() {
        if(wm.pop == 0 && (wm.food < 25 || wm.housing < 5)) {
            endDelay--;
        } else {
            endDelay = END_DELAY;
        }
        
        if(endDelay < 0) {
            setVolumes();
            endWorld = true;
            try {
                bgMusic.stop();
            } catch (Exception e){}
            WorldManagement.playSound(endSound);
            addObject(endScreen, getWidth() / 2, getHeight() / 2);
            endDelay = 200000;
        }
        
        if(endScreen.isFinished()) {
            fadeIn.start();
            addObject(fadeIn, getWidth() / 2, getHeight() / 2);
        }
        
        if(fadeIn.isFinished()) {
            Greenfoot.setWorld(new Start());
        }
    }
    
    private void checkEndWorld() {
        if(Greenfoot.mousePressed(end)) {
            wm.setDifficulty(150);
            end.update(new Color(70, 10, 10), Color.BLACK, Color.WHITE);
        }
    }
    
    /**
     * Set these volumes to 0, so they cannot be heard when world ends.
     */
    private static void setVolumes()
    {
        Human.hurtSound.setVolume(0);
        Zombie.zombieOne.setVolume(0);
    }
}

