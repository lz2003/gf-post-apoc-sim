import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Tree that lumberjacks can murder
 * 
 * @author Lucy Zhao
 * @author Young Chen
 * @version 2020-10-09
 */
public class Tree extends Actor
{
    private static GreenfootImage TREE_SPRITE = new GreenfootImage("tree.png");
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
    
    /**
     * Updates the tree
     */
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
            WorldManagement.getTrees().remove(this);
            WorldManagement.getWorld().removeObject(this);
        } 
        else
        {
            sprite = TREE_SPRITE;
        }
    }
    
    /**
     * Removed chopped down trees and updates wood resources
     */
    public void chop() {
        if(chopped) return;
        WorldManagement.updateWood(((float)(Math.random() * 2000) % 15) + 5);
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
    
    /**
     * Set the targeted status of a tree
     * 
     * @param status    true if its targeted by a lumberjack, otherwise false
     */
    public void setTargetStatus(boolean status)
    {
        targeted = status;
    }
    
    /**
     * Returns the targeted status of the tree
     * 
     * @return boolean  true if being targeted, otherwise false
     */
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
