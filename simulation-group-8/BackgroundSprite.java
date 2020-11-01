import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BackgroundSprite here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BackgroundSprite extends Actor
{
    /**
     * Act - do whatever the BackgroundSprite wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        getImage().scale(WorldManagement.world.getWidth(), WorldManagement.world.getHeight());
    }    
}
