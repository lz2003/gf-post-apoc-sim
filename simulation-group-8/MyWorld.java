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
    public WorldManagement wm;
    public MyWorld()
    {    
        super(700, 700, 1, false); 
        
        // BECAUSE GREENFOOT DOES NOT RESET VARIABLE VALUES WHEN RESET IS PRESSED,
        // ALL THE INSTIANTIATING HAS BEEN MOVED TO THE ACT METHOD
    }
    boolean runOnce = false;
    public void act() { 
        if(!runOnce) {
            wm = new WorldManagement(getWidth(), getHeight(), this);
            wm.pop = wm.POP;
            wm.food = wm.FOOD;
            wm.wood = wm.WOOD;
            wm.iron = wm.IRON;
            wm.storage = wm.STORAGE;
            wm.housing = wm.HOUSING;
            wm.camX = 0; wm.camY = 0;
            wm.humans = new ArrayList<Human>();
            wm.enemies = new ArrayList<EnemyData>();
            wm.buildings = new ArrayList<BuildingSlot>();
            wm.trees = new ArrayList<Tree>();
            wm.init();
            runOnce = true;
        }
         
        wm._update();
    }
}
