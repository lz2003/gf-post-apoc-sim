import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Screen that fades in at the end of the game
 * 
 * @author Young Chen
 * @version 2020-10-10
 */
public class EndScreen extends Actor
{ 
    private int transparency;
    private static int END_TIME = 1000;
    public EndScreen() {
        transparency = 0;
        setImage(new GreenfootImage("end.png"));
        getImage().setTransparency(transparency);
    }
    
    public void act() 
    {
        int t = transparency++;
        getImage().setTransparency(Math.min(t, 255));
        
        if(transparency > END_TIME) {
            Greenfoot.setWorld(new Start());
        }
    }    
}
