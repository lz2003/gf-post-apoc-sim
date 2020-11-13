import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The background sprite of the game that moves with the camera
 * 
 * @author Young Chen
 * @version 2020-11-10
 */
public class Background extends Actor
{
    private int xPos, yPos;
    private GreenfootImage sprite;
    
    /**
     * Constructor for Background class.
     * 
     * @param x     the x position
     * @param y     the y position
     */
    public Background(int x, int y) {
        xPos = x;
        yPos = y;
        sprite = new GreenfootImage("grass-tile.png");
        sprite.scale(700, 700);
        setImage(sprite);
    }
    
    /**
     * Returns the x position
     * 
     * @return int  the x position
     */
    public int getX() {
        return xPos;
    }
    
    /**
     * Returns the y position
     * 
     * @return int  the y position
     */
    public int getY() {
        return yPos;
    }
}
