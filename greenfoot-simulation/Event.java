import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Events superclass
 * 
 * @author Young Chen
 * @version 2020-11-04
 */
public abstract class Event extends Actor
{
    public static final int
    METEOR = 0,
    TORNADO = 1;
    
    protected int xLoc, yLoc, rot;
    
    public int getX() {
        return xLoc;
    }
    
    public int getY() {
        return yLoc;
    }
    
    /**
     * Get the buildings within specified range
     * 
     * @param x Location in x axis of location to search from
     * @param y Location in y axis of location to search from
     * @param range Range to search for buildings
     */
    
    protected ArrayList<BuildingSlot> getBuildingsWithinRange(int x, int y, int range) {
        ArrayList<BuildingSlot> list = new ArrayList<BuildingSlot>();
        for(int i = 0, n = WorldManagement.buildings.size(); i < n; i++) {
            BuildingSlot building = (BuildingSlot)(WorldManagement.buildings.get(i));
            int xLoc = building.getX(), yLoc = building.getY();
            int distance = Utils.calcDist(x, xLoc, y, yLoc);  
            if(distance <= range) {
                list.add(building);
            }
        }
        return list;
    }
    
    /**
     * Get the humans within specified range
     * 
     * @param x Location in x axis of location to search from
     * @param y Location in y axis of location to search from
     * @param range Range to search for humans
     */
    protected ArrayList<Human> getHumansWithinRange(int x, int y, int range) {
        ArrayList<Human> list = new ArrayList<Human>();
        for(int i = 0, n = WorldManagement.humans.size(); i < n; i++) {
            Human human = (Human)(WorldManagement.humans.get(i));
            int xLoc = human.getX(), yLoc = human.getY();
            int distance = Utils.calcDist(x, xLoc, y, yLoc);  
            if(distance <= range) {
                list.add(human);
            }
        }
        return list;
    }
    
    public abstract void _update();
}
