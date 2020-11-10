import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Displays the arrows for switching between options
 * 
 * @author Young Chen
 * @version 2020-11-09
 */
public class ArrowUI extends Actor
{
    static final GreenfootImage 
        CLICKED = new GreenfootImage("clicked.png");
    public ArrowUI(boolean isRight) {
        setImage(CLICKED);
        // Flips the arrow left
        if (!isRight)
        {
            this.setRotation(180);
        }
    }
}
