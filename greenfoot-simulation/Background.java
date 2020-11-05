import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Background here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Background extends Actor
{
    private int xPos, yPos;
    private GreenfootImage sprite;
    public Background(int x, int y) {
        xPos = x;
        yPos = y;
        sprite = new GreenfootImage("grass-tile.png");
        sprite.scale(700, 700);
        setImage(sprite);
    }
    
    public int getX() {
        return xPos;
    }
    
    public int getY() {
        return yPos;
    }
    /*
    public BackgroundSprite getSprite() {
        return sprite;
    } */ 
}
