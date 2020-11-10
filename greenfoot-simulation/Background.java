import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The background sprite of the game that moves with the camera
 * 
 * @author Young Chen
 * @version 2020-10-10
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
