import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Displays the arrows for switching between options
 * 
 * @author Lucy Zhao
 * @author Young Chen
 * @version 2020-11-10
 */
public class ArrowUI extends Actor
{
    static final GreenfootImage 
        CLICKED = new GreenfootImage("clicked.png"),
        UNCLICKED = new GreenfootImage("unclicked.png");
    
    private static final int CLICK_DELAY = 10;
       
    private int clickDelay;
    private boolean imageSet = false;
    
    /**
     * Creates a new arrow ui pointing either left or right
     * 
     * @param isRight Whether or not this is point right. If not, it will point left.
     */
    
    public ArrowUI(boolean isRight) {
        setImage(UNCLICKED);
        // Flips the arrow left
        if (!isRight)
        {
            this.setRotation(180);
        }
    }
    
    /**
     * Actor act method
     */
    public void act() {
        if(clickDelay > 0) {
            clickDelay--;
        } else {
            if(!imageSet) {
                setImage(UNCLICKED);
                imageSet = true;
            }
        }
    }
    
    /**
     * Changes the UI to visually show a click for a set amount of time
     */
    public void click() {
        setImage(CLICKED);
        clickDelay = CLICK_DELAY;
        imageSet = false;
    }
}
