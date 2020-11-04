import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Building here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Building extends Actor
{
    protected static final int
        DEFAULT_HP = 300;
        
    protected int 
        staff = 0,
        hp = DEFAULT_HP;
    
    protected GreenfootImage sprite;
    
    public abstract void destroy();
    public abstract void _update();  
    
    public GreenfootImage getSprite()
    {
        return sprite;
    }
    
}
