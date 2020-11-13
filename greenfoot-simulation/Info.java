import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Info here.
 * 
 * @author Young Chen
 * @version (a version number or a date)
 */
public class Info extends World
{
    private LZTextBox 
         back = new LZTextBox(350, 350, Color.WHITE, 30, 
        "center", 3, 200, 50, Color.BLACK, new Color(130, 130, 190))
        , cancel;
            
    /**
     * Constructor for objects of class Info.
     * 
     */
    public Info()
    {    
        super(700, 700, 1); 
        
        addObject(back, 350, 120 + 150 + 150 + 150 + 80);
        back.update(" Back");
        back.updateText();
    }
    
    /** 
     * Act method for Info Class
     */
    public void act() {
        if(Greenfoot.mousePressed(back)) {
            Greenfoot.setWorld(new Start());
        }   
    }
}
