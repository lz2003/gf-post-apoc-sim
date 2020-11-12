import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Fade here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Fade extends Actor
{
    private static final int SPEED = 9;
    private int opacity = 255, goal = 0;
    private boolean finished = false, in = false, start = false;
    /**
     * Creates a sprite that fades in or out
     * 
     * @param in Whether or not to fade in. If not, it will fade out.
     */
    public Fade(boolean in) {
        this.in = in;
        setImage(new GreenfootImage("black.png"));
        if(in) {
            opacity = 0;
            goal = 255;
            getImage().setTransparency(0);
        }
        getImage().scale(700,700);
    }
    
    /**
     * Act - do whatever the Fade wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(!start) return;
        
        if(in) {
            opacity += SPEED;
            getImage().setTransparency(opacity > 255 ? 255 : opacity);
        } else {
            opacity -= SPEED;
            getImage().setTransparency(opacity < 0 ? 0 : opacity);
        }
    }    
    
    /**
     * Starts the fade
     */
    public void start() {
        start = true;
    }
    
    /**
     * Whether or not the fade has finished
     * 
     * @return Boolean value of whether or not the fade has finished
     */
    public boolean isFinished() {
        if(in) return opacity > goal;
        else return opacity < goal;
    }
}
