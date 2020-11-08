import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Building here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Building extends Actor
{
    protected GreenfootImage sprite;
    
    public static final GreenfootImage
        EMPTY_SPRITE = new GreenfootImage("empty.png"),
        FARM_SPRITE = new GreenfootImage("farm.png"),
        HOUSE_SPRITE = new GreenfootImage("house.png"), 
        MINE_SPRITE = new GreenfootImage("mine.png"),
        SENTRY_SPRITE = new GreenfootImage("sentry.png"), 
        STORAGE_SPRITE = new GreenfootImage("storage.png");
    
    public abstract void destroy();
    public abstract void _update();  
    
    public GreenfootImage getSprite()
    {
        return sprite;
    }
    
}
