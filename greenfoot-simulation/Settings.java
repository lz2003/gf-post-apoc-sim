import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Settings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Settings extends World
{
    private String[] options = {"0", "1", "2", "3", "4", "5", "6"};
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
     * 
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
    
    public void act() {
        for(int i = 0, n = selections.length; i < n; i++) {
            selections[i]._update();
        }
        
        if(Greenfoot.mousePressed(save)) {
            for(int i = 0; i < WorldManagement.TYPES_OF_HUMANS; i++) {
                Simulation.startHumans[i] = selections[i].getSelected();
            }
            Greenfoot.setWorld(new Start());
        }   
    }
    
    private class SelectionBar extends Actor{
        private String name;
        private YCWidget selection = selection = new YCWidget(options,
            new Color(130,130,190), new Color(150, 210, 210), new Color(20,20,50), Color.WHITE, new Color(20,20,20), 
            280, 50, 1, 0, -120, "right", true, false, null);;
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
        public SelectionBar(String name, int x, int y) {
            this.name = name;

            
            arrow = new ArrowUI(true, 25, 50);
            
            addObject(selection, x, y);
            addObject(arrow, x + 160, y);
            addObject(heading, x, y - 70);
            heading.update(" " + name);
            heading.updateText();
        }
        
        public void _update() {
            if(Greenfoot.mousePressed(arrow)) {
                selection.shiftSelect();
                arrow.click();
            }
            
            if(Greenfoot.mouseClicked(arrow)){
                arrow.unClick();
            }
        }
        
        public void shiftSelect() {
            selection.shiftSelect();
        }
        
        public String getName() {
            return name;
        }
        
        public int getSelected() {
            return selection.getActive();
        }
    }
}
