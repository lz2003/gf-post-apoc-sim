import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Main world class where the simulation takes place
 * 
 * @author Young Chen
 * @version 2020-10-11
 */
public class MyWorld extends World
{
    public static final int EASY = 0, NORMAL = 1, HARD = 2, END_DELAY = 300;
    
    private LZTextBox end = new LZTextBox(350, 350, Color.WHITE, 19, 
    "center", 1, 150, 25, Color.BLACK, new Color(240, 40, 40));
    
    
    private WorldManagement wm;
    private int endDelay = END_DELAY;
    private int difficulty;
    
    public MyWorld(int difficulty)
    {    
        super(700, 700, 1, false); 
        
        this.difficulty = difficulty;
        setPaintOrder(EndScreen.class, LZTextBox.class, ScoreBar.class, House.class, Storage.class, StatBar.class, Event.class, Human.class, Tree.class, Sentry.class, Farm.class, Mine.class, BuildingSlot.class);
  
        end.update("End the World");
        end.updateText();
        addObject(end, 620, 16);
    }
    boolean runOnce = false;
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
            wm.camX = 0; wm.camY = 0;
            wm.humans = new ArrayList<Human>();
            wm.enemies = new ArrayList<Enemy>();
            wm.buildings = new ArrayList<BuildingSlot>();
            wm.trees = new ArrayList<Tree>();
            wm.events = new ArrayList<Event>();
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
        }
        
        wm._update();
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
            addObject(new EndScreen(), getWidth() / 2, getHeight() / 2);
            endDelay = 200000;
        }
    }
    
    private void checkEndWorld() {
        if(Greenfoot.mousePressed(end)) {
            wm.setDifficulty(150);
            end.update(new Color(70, 10, 10), Color.BLACK, Color.WHITE);
        }
    }
}

