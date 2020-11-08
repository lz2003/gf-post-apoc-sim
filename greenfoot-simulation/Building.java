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
    
    public abstract void destroy();
    public abstract void _update();  
    
    public GreenfootImage getSprite()
    {
        return sprite;
    }
    
    public void setRot(int rot) {
        setRotation(rot);
    }
    
}
