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
    
    protected static final int DEFAULT_DAMAGE = 25;
    
    protected int damage, range;
    
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
     * @param x Location in x axis to search from
     * @param y Location in y axis to search from
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
     * @param x Location in x axis to search from
     * @param y Location in y axis to search from
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
    
    
    /**
     * Get the trees within specified range
     * 
     * @param x Location in x axis to search from
     * @param y Location in y axis to search from
     * @param range Range to search for trees
     */
    protected ArrayList<Tree> getTreesWithinRange(int x, int y, int range) {
        ArrayList<Tree> list = new ArrayList<Tree>();
        for(int i = 0, n = WorldManagement.trees.size(); i < n; i++) {
            Tree tree = (Tree)(WorldManagement.trees.get(i));
            int xLoc = tree.getX(), yLoc = tree.getY();
            int distance = Utils.calcDist(x, xLoc, y, yLoc);  
            if(distance <= range) {
                list.add(tree);
            }
        }
        return list;
    }
    
    /**
     * Damages the nearby humans, trees, and buildings
     * 
     * @param xLoc Location in x axis to search for objects that are to be damaged from 
     * @param yLoc Location in y axis to search for objects that are to be damaged from 
     * @param range The range of the object search
     * @param damage How much to damage the objects found
     */
    
    protected void killNearbyThings(int xLoc, int yLoc, int range, int damage) {
        ArrayList<BuildingSlot> buildings = getBuildingsWithinRange(xLoc, yLoc, range);
        for(int i = 0; i < buildings.size(); i++) {
            ((BuildingSlot)buildings.get(i)).damage(damage);
        }
        
        ArrayList<Human> humans = getHumansWithinRange(xLoc, yLoc, range);
        for(int i = 0; i < humans.size(); i++) {
            ((Human)humans.get(i)).damage(damage);
        }
        
        ArrayList<Tree> trees = getTreesWithinRange(xLoc, yLoc, range);
        for(int i = 0; i < trees.size(); i++) {
            ((Tree)trees.get(i)).destroy();
        }
    }
    
    public abstract void _update();
}
