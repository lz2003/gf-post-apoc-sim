import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tree here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tree extends Actor
{
    private Tree tree;
    private boolean chopped;
    private int xLoc, yLoc;
    private GreenfootImage sprite;
    /**
     * Constructor for objects of class Tree
     */
    public Tree(int x, int y)
    {
        xLoc = x;
        yLoc = y;
        sprite = new GreenfootImage("tree.png");
        setImage(sprite);
        //WorldManagement.world.addObject(sprite, xLoc, yLoc);
    }
    
    /**
     * Removed chopped down trees and updates wood resources
     */
    public void chop() {
        if(chopped) return;
        WorldManagement.wood += (((float)(Math.random() * 22300)) % 15) + 5;
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
