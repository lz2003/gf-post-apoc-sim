import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * World for the simulation settings
 * 
 * @author Young Chen 
 * @version 2020-11-12
 */
public class Settings extends World
{
    private String[] options = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private SelectionBar lumberjacks;
    private LZTextBox 
        save = new LZTextBox(350, 350, Color.WHITE, 30, 
        "center", 3, 200, 50, Color.BLACK, new Color(130, 130, 190));
            
    private SelectionBar[] selections = {
        new SelectionBar("Builders", 350, 120),
        new SelectionBar("Farmers", 350, 120 + 150),
        new SelectionBar("Lumberjacks", 350, 120 + 150 + 150),
        new SelectionBar("Miners", 350, 120 + 150 + 150 + 150)
    };
    
    private int[] defaultHumans = Simulation.startHumans; // Corresponding starting values to the selectionbar values
    /**
     * Constructor for objects of class Settings.
     */
    public Settings()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(700, 700, 1); 
        
        for(int i = 0, n = selections.length; i < n; i++) {
            selections[i].shiftSelect();
            for(int j = 0; j < defaultHumans[i]; j++) {
                selections[i].shiftSelect();
            }
        }
        
        addObject(save, 350, 120 + 150 + 150 + 150 + 80);
        save.update("Save");
        save.updateText();
        setBackground("settings.jpg");
        setPaintOrder(LZTextBox.class, YCWidget.class);
    }
    
    /**
     * Act method for Settings.
     */
    public void act() {
        for(int i = 0, n = selections.length; i < n; i++) {
            selections[i]._update();
        }
        
        if(Greenfoot.mousePressed(save)) {
            for(int i = 0; i < WorldManagement.TYPES_OF_HUMANS; i++) {
                Simulation.startHumans[i] = selections[i].getSelected();
            }
            Start.playClick();
            Greenfoot.setWorld(new Start());
        }   
    }
    
    private class SelectionBar extends Actor{
        private String name;
        private YCWidget selection = selection = new YCWidget(options,
            new Color(130,130,190), new Color(150, 210, 210), new Color(20,20,50), Color.WHITE, new Color(20,20,20), 
            440, 50, 1, 0, -120, "right", true, false, null);;
        private ArrowUI arrow; 
        
        private LZTextBox heading = new LZTextBox(350, 350, Color.WHITE, 30, 
            "center", 3, 200, 50, Color.BLACK, new Color(150, 210, 210));
    
        /**
         * Creates a new SelectionBar with given name and coordinates
         * 
         * @param name Name of SelectionBar
         * @param x Location in x-axis of selection bar
         * @param y Location in y-axis of selection bar
         */
        private SelectionBar(String name, int x, int y) {
            this.name = name;

            
            arrow = new ArrowUI(true, 25, 50);
            
            addObject(selection, x, y);
            addObject(arrow, x + 240, y);
            addObject(heading, x, y - 70);
            heading.update(" " + name);
            heading.updateText();
        }
        
        /**
         * Checks if the arrow is pressed
         */
        public void _update() {
            if(Greenfoot.mousePressed(arrow)) {
                selection.shiftSelect();
                arrow.click();
            }
            
            if(Greenfoot.mouseClicked(arrow)){
                arrow.unClick();
            }
        }
        
        /**
         * Shift the selection
         */
        public void shiftSelect() {
            selection.shiftSelect();
        }
        
        /**
         * Get the name of setting/selection bar
         * 
         * @return String   the name
         */
        public String getName() {
            return name;
        }
        
        /**
         * Returns which selection is chosen (number of humans)
         * 
         * @return int  the value of the selection 
         */
        public int getSelected() {
            return selection.getActive();
        }
    }
}
