import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tree here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tree extends Actor
{
    private boolean chopped;
    private int xLoc, yLoc;
    private GreenfootImage sprite;
    private boolean targeted, spriteSet = false;
    private int showDelay = 5;
    /**
     * Constructor for objects of class Tree
     */
    public Tree(int x, int y)
    {
        xLoc = x;
        yLoc = y;
    }
    
    public void _update() {
        if(showDelay > 0) {
            showDelay--;
        } else if (!spriteSet) {
            setImage(sprite);
            spriteSet = true;
        }
    }
    
    /**
     * Makes sure trees don't spawn on top of other trees/buildings
     */
    protected void addedToWorld(World world)
    {
        if (Event.getBuildingsWithinRange(xLoc, yLoc, 50).size() > 0)
        {
            WorldManagement.trees.remove(this);
            WorldManagement.world.removeObject(this);
        } 
        else
        {
            sprite = new GreenfootImage("tree.png");
        }
    }
    
    /**
     * Removed chopped down trees and updates wood resources
     */
    public void chop() {
        if(chopped) return;
        WorldManagement.wood += ((float)(Math.random() * 2000) % 15) + 5;
        chopped = true;
        destroy();
    }
    
    /**
     * Removes the tree from the world
     */
    public void destroy() {
        WorldManagement.trees.remove(this);
        WorldManagement.world.removeObject(this);
    }
    
    public void setTargetStatus(boolean status)
    {
        targeted = status;
    }
    
    public boolean getTargetStatus()
    {
        return targeted;
    }
    
    /**
     * Returns the x location
     * 
     * @return int  the x location of the building
     */   
    public int getX() {
        return xLoc;
    }
    
    /**
     * Returns the y location
     * 
     * @return int  the y location of the building
     */
    public int getY() {
        return yLoc;
    }  
}
