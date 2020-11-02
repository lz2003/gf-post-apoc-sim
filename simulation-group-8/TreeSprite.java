import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TreeSprite here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TreeSprite extends Actor
{
    /**
     * Act - do whatever the TreeSprite wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public TreeSprite() {
        //setImage(new GreenfootImage("tree.png"));
    }
    
    boolean runOnce = false;
    public void act() 
    {
        if(!runOnce) {
            setImage(new GreenfootImage("tree.png"));
            runOnce = true;
        }
        // Add your action code here.
    }    
}
