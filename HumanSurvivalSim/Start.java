import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * Start class controls the starting screen of the simulation.
 * 
 * @author Young Chen
 * @author Lucy Zhao
 * @author Leo Foo
 * @version 2020-11-09
 */
public class Start extends World
{
    private static GreenfootSound bgMusic = new GreenfootSound("start_music.wav");
    
    private static GreenfootSound clickSound = new GreenfootSound("button_click.wav");    
    private LZTextBox 
    start = new LZTextBox(350, 350, Color.WHITE, 45, 
    "center", 3, 300, 100, Color.BLACK, new Color(150, 210, 210)),
    
    settings = new LZTextBox(350, 350, Color.WHITE, 25, 
    "center", 3, 200, 40, Color.BLACK, new Color(130,130,190)),
    
    info = new LZTextBox(350, 350, Color.WHITE, 25, 
    "center", 3, 200, 40, Color.BLACK, new Color(130,130,190));
    
    private ArrowUI right = new ArrowUI(true);
    private ArrowUI left = new ArrowUI(false);
    
    private Fade fadeIn = new Fade(true), fadeOut = new Fade(false);
    
    private boolean rightClicked;
    
    private String[] options = {"normal.png", "easy.png", "hard.png"};
    YCWidget widget = new YCWidget(options,
    new Color(130,130,190), new Color(150, 210, 210), new Color(130,130,190), Color.WHITE, new Color(20,20,20), 
    360, 75, 1, 0, -120, 
    "right", true, false, null);
    
    private int difficulty = 10;
    private boolean runOnce = false, started = false;
    
    public Start()
    {    
       // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(700, 700, 1); 
        
        addObject(widget, 350, 350);
        addObject(start, 350, 500);
        addObject(settings, 350, 600);
        addObject(info, 350, 650);
        addObject(right, 600, 350);
        addObject(left, 100, 350);
        
        addObject(fadeOut, 350, 350);
 
        widget.shiftSelect();
        widget.shiftSelect();
        
        start.update(" START");
        start.updateText();
        
        settings.update(" Settings");
        settings.updateText();
        
        info.update(" Controls & Info");
        info.updateText();
        
        setBackground("start.jpg");
        
        clickSound.setVolume(80);
    }
    
    /**
     * pauses music if Greenfoot is paused. 
     */
    public void stopped()
    {
        bgMusic.pause();
    }
    
    /**
     * music becomes disoriented if you press start and reset and press start again. somehow music player isnt connected to greenfoot start stop. so when
     * when scenario starts bgMusic should be null since if its not, the jplayer or javazoom whatever will 
     */
    public void started()
    {
        bgMusic = null;
    }
    
    // somehow greenfoot is calling act method despite being paused?
    public void act() {
        if(bgMusic == null)
        {
            bgMusic = new GreenfootSound("start_music.wav");
        }
        if (!bgMusic.isPlaying())
        {
            playMusic();
        }
        if(!runOnce) {
            fadeOut.start();
            runOnce = true;
            // bgMusic = new GreenfootSound("start_music.mp3");
        }
        
        if(fadeOut.isFinished()) {
            removeObject(fadeOut);
        }
        
        if(Greenfoot.mousePressed(start) && !started) {
            playClick();
            addObject(fadeIn, 350, 350);
            fadeIn.start();
            start.update(new Color(130,130,190), Color.BLACK, Color.WHITE);
            started = true;
        }
        
        if(fadeIn.isFinished()) {
            stopMusic();
            Greenfoot.setWorld(new Simulation(difficulty));
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
        
        if(Greenfoot.mousePressed(settings)) {
            playClick();
            Greenfoot.setWorld(new Settings());
        }
        
        if(Greenfoot.mousePressed(info)) {
            playClick();
            Greenfoot.setWorld(new Info());
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
            case "easy.png":
                difficulty = Simulation.EASY;
                break;
            case "normal.png":
                difficulty = Simulation.NORMAL;
                break;
            case "hard.png":   
                difficulty = Simulation.HARD;
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
    
    private void playMusic() // Really sus and sketch...
    {
        try {
            bgMusic.play();
        } catch(Exception e) {
            // do nothing
        }
    }
    
    /**
     * Plays the click noise for buttons
     */
    public static void playClick()
    {
        try {
            if (!clickSound.isPlaying())
            {
                clickSound.play();
            }
        } catch (Exception e) {
            // do nothing
        }
    }
    
    private void stopMusic()
    {
        try {
            bgMusic.stop();
        } catch(Exception e) {
        }
    }
}
