import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Enemy extends Event
{
    /**
     * Gets the nearest human from specified location
     * 
     * @param x Location in x-axis to search from
     * @param y Location in y-axis to search from
     * 
     * @return The closest human that was found
     */
    protected Human getNearestHuman(int x, int y) {
        int lowest = 9999, index = 0;
        for(int i = 0, n = WorldManagement.humans.size(); i < n; i++) {
            Human human = (Human)(WorldManagement.humans.get(i));
            int xLoc = human.getX(), yLoc = human.getY();
            int distance = Utils.calcDist(x, xLoc, y, yLoc);
            if(distance < lowest) {
                lowest = distance;
                index = i;
            }
        }
        return (Human) WorldManagement.humans.get(index);
    }   
}