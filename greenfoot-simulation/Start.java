import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Start here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Start extends World
{
    private LZTextBox start = new LZTextBox(30, 1, 300, 100);
    
    private RightArrowUI right = new RightArrowUI();
    
    private boolean md, pressed; // mouse Down
    
    private String[] options = {"Normal", "Easy", "Hard"};
    YCWidget widget = new YCWidget(options,
    new Color(130,130,190), new Color(150, 210, 210), Color.WHITE, Color.WHITE, new Color(20,20,20), 
    360, 75, 1, 0, -120, 
    "right", true, false, null);
    
    private int difficulty = 10;
    
    public Start()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(700, 700, 1); 
        
        addObject(widget, 350, 350);
        addObject(start, 350, 500);
        addObject(right, 600, 350);

        widget.shiftSelect();
        widget.shiftSelect();
        
        start.update("START");
    }
    
    public void act() {
        if(Greenfoot.mouseClicked(start)) {
            Greenfoot.setWorld(new MyWorld(difficulty));
        }
        
        if(Greenfoot.mouseClicked(right)) {
            pressed = true;
        } else {
            pressed = false;
        }
        handleWidget();
        
        //throw new Error("change the horrible colour theme");
    }
    
    /**
     * Handle's widget
     */
    private void handleWidget() {
        if(pressed && widget.isShowing()) {
            if(!md) {
                options = shiftOneRight(options);
                widget.update(options);
                widget.resetDelay();
                widget.shiftSelect();
                widget.shiftSelect();
                md = true;
            }
        } else {
            md = false;
        }
        
        switch(widget.getActiveName()) {
            case "Easy":
                difficulty = MyWorld.EASY;
                break;
            case "Normal":
                difficulty = MyWorld.NORMAL;
                break;
            case "Hard":   
                difficulty = MyWorld.HARD;
                break;
        }
    }
    
    // Shifts array one element to the right (first element goes to last)
    private String[] shiftOneRight(String[] arr) {
        String[] newArr = new String[arr.length];
        String last = arr[arr.length - 1];
        for(int i = 0, n = arr.length - 1; i < n; i++) {
            newArr[i + 1] = arr[i];
        }
        newArr[0] = last;
        return newArr;
    }
}
