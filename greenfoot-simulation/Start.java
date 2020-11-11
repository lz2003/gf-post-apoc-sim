import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Start class controls the starting screen of the simulation.
 * 
 * @author Young Chen
 * @author Lucy Zhao
 * @version 2020-11-09
 */
public class Start extends World
{
    private LZTextBox start = new LZTextBox(350, 350, Color.WHITE, 30, 
    "center", 3, 300, 100, Color.BLACK, new Color(150, 210, 210));
    private ArrowUI right = new ArrowUI(true);
    private ArrowUI left = new ArrowUI(false);
    
    private boolean rightClicked;
    
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
        addObject(left, 100, 350);

        widget.shiftSelect();
        widget.shiftSelect();
        
        start.update("START");
        start.updateText();
        setBackground("start.jpg");
    }
    
    public void act() {
        if(Greenfoot.mousePressed(start)) {
            Greenfoot.setWorld(new MyWorld(difficulty));
        }
        
        if(Greenfoot.mousePressed(right)) {
            rightClicked = true;
            right.click();
            handleWidget();
        }
        else if(Greenfoot.mousePressed(left)) {
            rightClicked = false;
            left.click();
            handleWidget();
        }  
        
        if(Greenfoot.mouseClicked(right) || Greenfoot.mouseClicked(left)){
            right.unClick();
            left.unClick();
        }
    }
    
    /**
     * Handles the difficulty selection widget
     */
    private void handleWidget() {
        if (rightClicked) options = shiftOneRight(options);
        else options = shiftOneLeft(options);
        widget.update(options);
        widget.resetDelay();
        widget.shiftSelect();
        widget.shiftSelect();
        
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
    
    /**
     * Shifts all elements in an array one element right
     */
    private String[] shiftOneRight(String[] arr) {
        String[] newArr = new String[arr.length];
        String last = arr[arr.length - 1];
        for(int i = 0, n = arr.length - 1; i < n; i++) {
            newArr[i + 1] = arr[i];
        }
        newArr[0] = last;
        return newArr;
    }
    
    /**
     * Shifts all elements in an array one element left
     */
    private String[] shiftOneLeft(String[] arr) {
        String[] newArr = new String[arr.length];
        String first = arr[0];
        for(int i = arr.length-1, n = 0; i > n; i--) {
            newArr[i - 1] = arr[i];
        }
        newArr[arr.length-1] = first;
        return newArr;
    }
}