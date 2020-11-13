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
    private GreenfootImage 
        clicked = new GreenfootImage("clicked.png"),
        unclicked = new GreenfootImage("unclicked.png");
    
    private static final int CLICK_DELAY = 10;
    private int xSize = 100, ySize = 100;

    private int clickDelay;
    private boolean imageSet = false, shouldResize = false;
    
    /**
     * Creates a new arrow ui pointing either left or right
     * 
     * @param isRight Whether or not this is point right. If not, it will point left.
     */
    
    public ArrowUI(boolean isRight) {
        setImage(unclicked);
        // Flips the arrow left
        if (!isRight)
        {
            this.setRotation(180);
        }
    }
    
    /**
     * Alternative constructor for the ArrowUI
     * 
     * @param isRight   Whether or not this is point right. If not, it will point left.
     * @param xSize     the custom width 
     * @param ySize     the custom height
     */
    public ArrowUI(boolean isRight, int xSize, int ySize) {
        setImage(unclicked);
        // Flips the arrow left
        if (!isRight)
        {
            this.setRotation(180);
        }
        
        shouldResize = true;
        this.xSize = xSize;
        this.ySize = ySize;
        getImage().scale(xSize, ySize);
    }
    
    /**
     * Actor act method
     */
    public void act() {

    }
    
    /**
     * Changes arrow to clicked sprite
     */
    public void click() {
        setImage(clicked);
        if(shouldResize)
        getImage().scale(xSize, ySize);
    }
    
    /**
     * Changes arrow to unclicked sprite
     */
    public void unClick() {
        setImage(unclicked);
        if(shouldResize)
        getImage().scale(xSize, ySize);
    }
}