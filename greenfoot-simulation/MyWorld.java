import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    public static final int EASY = 0, NORMAL = 1, HARD = 2;
    
    private WorldManagement wm;
    private int difficulty;
    
    public MyWorld(int difficulty)
    {    
        super(700, 700, 1, false); 
        
        this.difficulty = difficulty;
        setPaintOrder(ScoreBar.class, StatBar.class, Event.class, Human.class, Tree.class, BuildingSlot.class);
        // BECAUSE GREENFOOT DOES NOT RESET VARIABLE VALUES WHEN RESET IS PRESSED,
        // ALL THE INSTIANTIATING HAS BEEN MOVED TO THE ACT METHOD
    }
    boolean runOnce = false;
    public void act() { 
        if(!runOnce) {
            wm = new WorldManagement(getWidth(), getHeight(), this);
            wm.pop = wm.START_POP;
            wm.food = wm.START_FOOD;
            wm.wood = wm.START_WOOD;
            wm.iron = wm.START_IRON;
            wm.storage = wm.START_STORAGE;
            wm.housing = wm.START_HOUSING;
            wm.zombieSpawnRate = wm.ZOMBIE_SPAWN_RATE;
            wm.camX = 0; wm.camY = 0;
            wm.humans = new ArrayList<Human>();
            wm.enemies = new ArrayList<Enemy>();
            wm.buildings = new ArrayList<BuildingSlot>();
            wm.trees = new ArrayList<Tree>();
            wm.events = new ArrayList<Event>();
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
        }
         
        wm._update();
    }
}

