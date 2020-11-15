import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Main world class where the simulation takes place
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

