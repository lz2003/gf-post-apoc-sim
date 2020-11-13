import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Screen that fades in at the end of the game
 * 
 * @author Young Chen
 * @version 2020-11-10
 */
public class EndScreen extends Actor
{ 
    private int transparency;
    private static int END_TIME = 500;
    
    /**
     * Constructor of EndScreen
     */
    public EndScreen() {
        transparency = 0;
        setImage(new GreenfootImage("end.png"));
        getImage().setTransparency(transparency);
    }
    
    /**
     * Act method of EndScreen
     */
    public void act() 
    {
        int t = transparency++;
        getImage().setTransparency(Math.min(t, 255));
    }    
    
    /**
     * Returns if the end screen is finished
     * 
     * @return boolean  true is finished, otherwise false
     */
    public boolean isFinished() {
        return transparency > END_TIME;
    }
}
