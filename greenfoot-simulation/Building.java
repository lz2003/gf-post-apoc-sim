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
    
    GreenfootImage storage = new GreenfootImage("storage.png");
    GreenfootImage sentry = new GreenfootImage("sentry.png");
    GreenfootImage mine = new GreenfootImage("mine.png");
    GreenfootImage house = new GreenfootImage("house.png");
    GreenfootImage farm = new GreenfootImage("farm.png");
    GreenfootImage empty = new GreenfootImage("empty.png");
    
    public abstract void destroy();
    public abstract void _update();  
    
    public GreenfootImage getSprite()
    {
        sprite.rotate((int)Math.round(Math.random()*4.0)*90);
        return sprite;
    }
    
}
