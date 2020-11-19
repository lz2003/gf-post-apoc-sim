import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * A selection widget that can be used to visually select one choice from an array of choices through key presses or any other user input method. This widget has a customisable
 * background colour, a customisable highlight colour, and customisable dimensions.
 * Other features of this class than can be adjusted include parenting to an actor's location, mutable selections,
 * auto hide after a certain period of inactivity, and using showing images as the selection choices.
 * <br> <br>
 * Note: If setting custom dimensions, the dimension must be divisable by the number of selections to avoid spacing issues.
 * 
 * @author Young Chen
 * @version 2020-10-10
 */
public class YCWidget extends Actor
{
    private static final int DIV_WIDTH = 2, OUTLINE_WIDTH = 5;
    private int width, height = 0, divs, curSelected, actualSelected = -2, inputTimer = 0, autoHideTime = 0, xOffSet, yOffSet;
    private long lastTime;
    private char loopDir;
    private String moveKey, dir;
    private Color bgColour, selectColour, divColour, selectTextColour, regularTextColour;
    private GreenfootImage image;
    private GreenfootImage[] optionsImg;
    private boolean keySelectDown = false, keyHideDown = false, isHidden = false, useImage = false, autoHide;
    private String[] options;
    private Actor parent;
    
    /**
     * Create a basic YCWidget with the default elements, colours, and settings.
     * <br> <br>
     *   
     *   
     *  
     */
    
    public YCWidget() {
        String[] s = {"1","2","3","4"};
        init(s,
             Color.GRAY, Color.BLACK, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE, 
             400, 100, 0, 
             "right",   
             false, false, null);
    }
    
    /**
     * Create a basic YCWidget with custom elements and the default colours, and settings.
     * <br> <br>
     *   
     *   
     *  
     * 
     * @param options String array containing the element names to be added to the YCWidget
     */
    
    public YCWidget(String[] options) {
        int width = options.length * 75;
        int height = options.length * 10;
        height = height > 100 ? 100 : height;
        init(options,
             Color.GRAY, Color.BLACK, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE, 
             width, height, 0, 
             "right",   
             false, false,  null);
    }
    
    /**
     * Create a basic YCWidget with custom elements, custom background and highlight background colours,
     * and the default settings.
     * <br> <br>
     *   
     *   
     *  
     * 
     * @param options String array containing the element names to be added to the YCWidget
     * @param backgroundColour The colour of the element box when it is not selected
     * @param highlightColour The colour of the element box when it is selected
     */
    
    public YCWidget(String[] options, Color backgroundColour, Color highlightColour) {
        int width = options.length * 75;
        int height = options.length * 10;
        height = height > 100 ? 100 : height;
        
        init(options,
             backgroundColour, Color.BLACK, highlightColour, Color.BLACK, Color.WHITE, 
             width, height, 0, 
             "right",   
             false, false,  null);
    }
    
    /**
     * Create a basic YCWidget with custom elements and dimensions, and the default colours, and settings.
     * <br> <br>
     *   
     *   
     *  
     * 
     * @param options String array containing the element names to be added to the YCWidget
     * @param length Size of the YCWidget in the x direction
     * @param height Size of the YCWidget in the y direction
     */
    
    public YCWidget(String[] options, int length, int height) {
        init(options,
             Color.GRAY, Color.BLACK, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE, 
             length, height, 0, 
             "right",   
             false, false, null);
    }
    
    /**
     * Create a basic YCWidget with custom elements, with custom dimensions and that is parented to another actor's location, 
     * as well as the default colours, and settings.
     * <br> <br>
     *   
     *   
     *  
     * 
     * @param options String array containing the element names to be added to the YCWidget
     * @param length Size of the YCWidget in the x direction
     * @param height Size of the YCWidget in the y direction
     * @param parent The actor that the YCWidget is parented to. Input null if no parent is desired
     */
    
    public YCWidget(String[] options, int length, int height, Actor parent) {
        init(options,
             Color.GRAY, Color.BLACK, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE, 
             length, height, 0, 
             "right",   
             false, false,  parent);
    }
    
    /**
     * Create a basic YCWidget that has custom elements, has custom dimensions and is parented 
     * to another actor's location with a custom x-axis offset and a custom y-axis offset.
     * <br> <br>
     *   
     *   
     *  
     * 
     * @param options String array containing the element names to be added to the YCWidget
     * @param length Size of the YCWidget in the x direction
     * @param height Size of the YCWidget in the y direction
     * @param parent The actor that the YCWidget is parented to in regards to its location. Input null if no parent desired
     * @param xOffSet The x-axis offset of the widget in relation to the actor. Input any number if no parent desired
     * @param yOffSet The y-axis offset of the widget in relation to the actor. Input any number if no parent desired
     */
    
    public YCWidget(String[] options, int length, int height, Actor parent, int xOffSet, int yOffSet) {
        init(options,
             Color.GRAY, Color.BLACK, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE, 
             length, height, 0, 
             "right",   
             false, false, parent);
        this.xOffSet = xOffSet;
        this.yOffSet = yOffSet;
    }
    
    /**
     * Create a basic YCWidget that has custom elements, has custom dimensions and is parented 
     * to another actor's location with a custom x-axis offset and a custom y-axis offset.
     * <br> <br>
     *   
     *   
     *  
     * 
     * @param options String array containing the element names to be added to the YCWidget
     * @param length Size of the YCWidget in the x direction
     * @param height Size of the YCWidget in the y direction
     * @param autoHide Whether or not to automatically hide the YCWidget after a certain period of inactivity 
     * @param hideDelay The time of which the widget will hide itself in milliseconds if there has been 
     * no new key presses and that autohide has been enabled. Input any number of autohide will not be enabled
     * @param parent The actor that the YCWidget is parented to in regards to its location
     * @param xOffSet The x-axis offset of the widget in relation to the parent. Input any number if no parent desired
     * @param yOffSet The y-axis offset of the widget in relation to the parent. Input any number if no parent desired
     */
    
    public YCWidget(String[] options, int length, int height, boolean autoHide, int hideDelay, Actor parent, int xOffSet, int yOffSet) {
        init(options,
             Color.GRAY, Color.BLACK, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE, 
             length, height, hideDelay, 
             "right",   
             false, autoHide,parent);
        this.xOffSet = xOffSet;
        this.yOffSet = yOffSet;
    }
    
    /**
     * Create a basic YCWidget with custom elements, custom dimensions, and the ability to use images to represent
     * each element.
     * <br> <br>
     *   
     *   
     *  
     * 
     * @param options String array containing the element names to be added to the YCWidget, or if use image is enabled, the
     * string array containing the image file names of the images to be used contained in the image
     * directory of the greenfoot project to represent each element. If no image is found, then the string representation
     * of the element will be used instead
     * @param length Size of the YCWidget in the x direction
     * @param height Size of the YCWidget in the y direction
     * @param useImage Whether or not to use images to represent elements
     */
    
    public YCWidget(String[] options, int length, int height, boolean useImage) {
        init(options,
             Color.GRAY, Color.BLACK, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE, 
             length, height, 0, 
             "right",   
             useImage, false,  null);
    }
    
    /**
     * Create a basic YCWidget with custom elements, fully custom colours,
     * and the default settings.
     * <br> <br>
     *   
     *   
     *  
     * 
     * @param options String array containing the element names to be added to the YCWidget
     * @param backgroundColour The colour of the element box when it is not selected
     * @param highlightColour The colour of the element box when it is selected
     * @param dividerColour The colour of the dividing line in between each element
     * @param regularTextColour The colour of the name of each element that has not been selected
     * @param highlightTextColour The colour of the name of the element that has been selected
     */
    
    public YCWidget(String[] options, Color backgroundColour, Color highlightColour, Color dividerColour, Color regularTextColour, Color highlightTextColour) {
        init(options,
             backgroundColour, dividerColour, highlightColour, highlightTextColour, regularTextColour, 
             400, 100, 0, 
             "right",   
             false, false, null);
    }
    
    /**
     * Create a basic YCWidget with custom elements, fully custom colours, custom dimensions
     * and the default settings.
     * 
     * @param options String array containing the element names to be added to the YCWidget
     * @param backgroundColour The colour of the element box when it is not selected
     * @param highlightColour The colour of the element box when it is selected
     * @param dividerColour The colour of the dividing line in between each element
     * @param regularTextColour The colour of the name of each element that has not been selected
     * @param highlightTextColour The colour of the name of the element that has been selected
     * @param length Size of the YCWidget in the x direction
     * @param height Size of the YCWidget in the y direction
     *  
     *  
     */
    
    public YCWidget(String[] options, Color backgroundColour, Color highlightColour, Color dividerColour, Color regularTextColour, Color highlightTextColour, 
    int length, int height) {
        init(options,
             backgroundColour, dividerColour, highlightColour, highlightTextColour, regularTextColour, 
             length, height, 0, 
             "right",   
             false, false, null);
    }
    
    /**
     * Create a basic YCWidget with custom elements, fully custom colours, custom dimensions,
     * and partially custom settings.
     * 
     * @param options String array containing the element names to be added to the YCWidget
     * @param backgroundColour The colour of the element box when it is not selected
     * @param highlightColour The colour of the element box when it is selected
     * @param dividerColour The colour of the dividing line in between each element
     * @param regularTextColour The colour of the name of each element that has not been selected
     * @param highlightTextColour The colour of the name of the element that has been selected
     * @param length Size of the YCWidget in the x direction
     * @param height Size of the YCWidget in the y direction
     * @param scrollDirection The name of the direction of which the next selected element will be. Valid directions: "right", "left"
     *  
     *  
     */
    
    public YCWidget(String[] options, Color backgroundColour, Color highlightColour, Color dividerColour, Color regularTextColour, Color highlightTextColour, 
                    int length, int height, 
                    String scrollDirection) {
        init(options,
             backgroundColour, dividerColour, highlightColour, highlightTextColour, regularTextColour, 
             length, height, 0, 
             scrollDirection,   
             false, false, null);
    }
    
    /**
     * Create a basic YCWidget with custom elements, fully custom colours, custom dimensions,
     * and fully custom settings.
     * 
     * @param options String array containing the element names to be added to the YCWidget, or if use image is enabled, the
     * string array containing the image file names of the images to be used contained in the image
     * directory of the greenfoot project to represent each element. If no image is found, then the string representation
     * of the element will be used instead
     * @param backgroundColour The colour of the element box when it is not selected
     * @param highlightColour The colour of the element box when it is selected
     * @param dividerColour The colour of the dividing line in between each element
     * @param regularTextColour The colour of the name of each element that has not been selected
     * @param highlightTextColour The colour of the name of the element that has been selected
     * @param length Size of the YCWidget in the x direction
     * @param height Size of the YCWidget in the y direction
     * @param hideDelay The time of which the widget will hide itself in milliseconds if there has been 
     * no new key presses and that autohide has been enabled. Input any number of autohide will not be enabled
     * @param xOffSet The x-axis offset of the widget in relation to the parent. Input any number if no parent desired
     * @param yOffSet The y-axis offset of the widget in relation to the parent. Input any number if no parent desired
     * @param scrollDirection The name of the direction of which the next selected element will be. Valid directions: "right", "left"
     * @param useImage Whether or not to use images to represent elements
     * @param autoHide Whether or not to automatically hide the YCWidget after a certain period of inactivity 
     * @param parent The actor that the YCWidget will be parented to in relation to its location. Input null if no parent is desired.
     */
    
    public YCWidget(String[] options, Color backgroundColour, Color highlightColour, Color dividerColour, Color regularTextColour, Color highlightTextColour, 
                    int length, int height, int hideDelay, int xOffSet, int yOffSet,
                    String scrollDirection,
                    boolean useImage, boolean autoHide,    Actor parent) {
        init(options,
             backgroundColour, dividerColour, highlightColour, highlightTextColour, regularTextColour, 
             length, height, hideDelay, 
             scrollDirection,   
             useImage, autoHide,    parent);
        this.xOffSet = xOffSet;
        this.yOffSet = yOffSet;
    }
    
    /**
     * Actor act method.
     */
    
    public void act() {
        int delta = calcDelta(); // change in time in milliseconds 
        
        if(!this.isHidden) {
            autoHide(delta);
        }
        
        followParent();
    }    
    
    // initialise settings with parameters passed in through the constructor
    private void init(String[] sections, 
                      Color bgColour, Color divColour, Color selectColour, Color selectTextColour, Color regularTextColour,
                      int width, int height, int autoHideTime,
                      String dir,
                      boolean useImage, boolean autoHide,
                      Actor parent) {
        this.bgColour = bgColour;
        this.width = width;
        this.height = height;
        this.divs = sections.length;
        this.selectColour = selectColour;
        this.divColour = divColour;
        this.dir = dir;
        this.options = sections;
        this.useImage = useImage;
        this.selectTextColour = selectTextColour;
        this.regularTextColour = regularTextColour;
        this.autoHide = autoHide;
        this.autoHideTime = autoHideTime;
        this.parent = parent;
        
        switch(dir) {
            case "right": 
                this.curSelected = -1;
                this.loopDir = 'r';
                break;
            case "left":
                this.curSelected = this.divs;
                this.loopDir = 'l';
                break;
            default: 
                this.curSelected = -1; // default: right
                this.loopDir = 'r';
                break;
        }
        
        if(useImage) {
            updateImages();
        }
        show();
        drawImages();
    }
    
    /**
     * Update the number of sections and the elements contained in the sections.
     * The new number of sections must divide evenly with the width.
     * 
     * @param sections The String array with the new elements to be inserted into the YCWidget, or the new file names of the image representations of each element if use image has been enabled
     */
    
    public void update(String[] sections) {
        init(sections, 
        this.bgColour, this.divColour, this.selectColour, this.selectTextColour, this.regularTextColour,
        this.width, this.height, this.autoHideTime,
        this.dir,
        this.useImage, this.autoHide, this.parent);
    }
    
    /**
     * Update the number of sections and the elements contained in the sections, along with the width and height.
     * The new number of sections must divide evenly with the specified width.
     * 
     * @param sections The String array with the new elements to be inserted into the YCWidget, or the new file names of the image representations of each element if use image has been enabled
     * @param width The new width of the YCWidget. Input -1 to keep original width
     * @param height The new height of the YCWidget. Input -1 to keep original height
     */
    
    public void update(String[] sections, int width, int height) {
        width = width < 0 ? this.width : width;
        height = height < 0 ? this.height : height;
        
        init(sections, 
        this.bgColour, this.divColour, this.selectColour, this.selectTextColour, this.regularTextColour,
        width, height, this.autoHideTime,
        this.dir,
        this.useImage, this.autoHide, this.parent);
    }
    
    /**
     * Replace one element with a new element.
     * 
     * @param value The new value of the element
     * @param index The index of the new value
    */
    
    public void update(String value, int index) {
        if(this.options.length <= index || index < 0) {
            return;
        }
        this.options[index] = value;
        if(this.useImage) {
            updateImages();
        }
    }
    
    /**
     * Set a new x-axis offset and y-axis offset if the YCWidget has a parent Actor.
     * 
     * @param xOffset new x-axis offset
     * @param yOffset new y-axis offset
    */
    public void update(int xOffset, int yOffset) {
        this.xOffSet = xOffset;
        this.yOffSet = yOffset;
    }
    
    /**
     * Check if an element is selected using the name of the element
     * 
     * @param name The name of the element
     * @return Whether the element is active. False if element is not selected and true if element is selected
    */
   
    public boolean isActive(String name) {
        int foundIndex = getStringIndex(this.options, name);
        return foundIndex == this.actualSelected ? true : false;
    }
    
    /**
     * Check if an element is selected using the index of the element
     * 
     * @param index The index of the element
     * @return Whether the element is active. False if element is not selected and true if element is selected
    */
    
    public boolean isActive(int index) {
        return index == actualSelected;
    }
    
    /**
     * Get the index of the current selected element
     * 
     * @return Index of the current selected element. Will return -1 if no element has been selected.
    */
    public int getActive() {
        return actualSelected < 0 ? -1 : actualSelected;
    }
    
    /**
     * Get the name of the current selected element
     * 
     * @return Name of the current selected element. Will return "" if no element has been selected.
    */
    public String getActiveName() {
        return this.actualSelected < 0 ? "" : this.options[this.actualSelected];
    }
    
    /**
     * Hide the YCWidget
     * 
    */
    
    public void hide() {
        GreenfootImage img = new GreenfootImage(1,1);
        this.setImage(img);
        this.isHidden = true;
    }
    
    /**
     * Show the YCWidget
     * 
    */
   
    public void show() {
        GreenfootImage img = drawBGRect(this.bgColour, this.width, this.height);
        drawDivisions(img, divColour, this.divs);
        this.setImage(img);
        this.image = img;
        this.isHidden = false;
        
        switch(dir) {
            case "right": 
                this.curSelected = -1;
                this.loopDir = 'r';
                break;
            case "left":
                this.curSelected = this.divs;
                this.loopDir = 'l';
                break;
            default: 
                this.curSelected = -1; // default: right
                this.loopDir = 'r';
                break;
        }
        
        resetDelay();
        assignDivTexts();
        drawImages();
    }
    
    /**
     * Gets whether or not the YCWidget is showing
     * 
     * @return Whether or not the YCWidget is showing
     */
    public boolean isShowing() {
        return !this.isHidden;
    }
    
    /**
     * Temporarily increases the delay if autohide is on
     * 
     * @param time Amount of milliseconds the current increase the delay by
     */
    public void prolongShowing(int time) {
        this.inputTimer += time;
    }
    
    /**
     * Resets the hide delay if autohide is on
     * 
     */
    public void resetDelay() {
        this.inputTimer = this.autoHideTime;
    }
    
    /**
     * Get the width of the YCWidget object
     * 
     * @return Width of widget
     */
    public int getWidth() {
        return this.width;
    }
    
    /**
     * Get the height of the YCWidget object
     * 
     * @return Height of widget
     */
    public int getHeight() {
        return this.height;
    }
    
    /**
     * Shift the selection one to the right or left, depending on the YCWidget's scroll direction
     * 
     */
    public void shiftSelect() {
        switch(loopDir) {
            case 'r':
                loopRight();
                break;
            case 'l':
                loopLeft();
                break;
        }
        resetDelay();
    }
    
    /**
     * Makes the widget visible if hidden and hidden if visible
     * 
     */
    public void switchVisibility() {
        if(isHidden) {
            show();
            switch(loopDir) {
                case 'r':
                    this.curSelected = -1;
                    break;
                case 'l':
                    this.curSelected = this.divs;
                    break;
            }
        } else {
            hide();
        }
    }
    
    private void updateImages() {
        this.optionsImg = new GreenfootImage[this.divs];
        for(int i = 0, n = this.divs; i < n; i++) {
            try{
                optionsImg[i] = new GreenfootImage(options[i]);
            } catch (Exception e) {}
        }
    }
    // set current location to parent's location
    private void followParent() {
        if(this.parent == null) {
            return;
        }
        this.setLocation(this.parent.getX() + xOffSet, this.parent.getY() + yOffSet);
    }
    
    // hide after certain amount of inactivity
    private void autoHide(int delta) {
        if(!autoHide) {
            return;
        }

        if(inputTimer > 0) {
            inputTimer -= delta;
            return;
        }
        hide();
    }
    
    // get change in time per update
    private int calcDelta() {
        long curT = System.currentTimeMillis();
        long delta = curT - lastTime;
        lastTime = curT;
        // initial calculation will return the time from epoch to the current time, so if its more than 10s, return 1
        if(delta > 10000) {
            return 1;
        }
        return (int) delta;
    }
    
    // draw the texts in each section
    private void assignDivTexts() {
        for(int i = 0, n = this.options.length; i < n; i++) {
            Color c = (i == this.curSelected ? this.selectTextColour : this.regularTextColour);
            drawDivText(this.options[i], i, c);
        }
    }
    
    // draw the text in one section
    private void drawDivText(String text, int div, Color c) {
        this.image.setColor(c);
        int divWidth = calculateDivWidth(this.image, this.divs);
        int xOffSet = (divWidth * div + DIV_WIDTH / 2) + 5;
        this.image.drawString(text, xOffSet, this.height / 2 + 5);
    }
    
    // draw the images of each section
    private void drawImages() {
        if(!useImage) {
            return;
        }
        for(int i = 0, n = optionsImg.length; i < n; i++) {
            GreenfootImage img = optionsImg[i];
            if(img == null) {
                continue;
            }
            
            int secWidth = calculateDivWidth(this.image, this.divs);
            int xOffSet = secWidth * i + DIV_WIDTH / 2;
            resize(optionsImg[i]);
            
            this.image.drawImage(this.optionsImg[i], xOffSet ,0);
            
            if(i == this.curSelected) {
                this.image.setColor(selectColour);
                //this.image.drawRect(xOffSet, 0, secWidth - DIV_WIDTH, this.height - 1);
                drawOutline(xOffSet, 0, secWidth - DIV_WIDTH, this.height - 1, secWidth);
            }
        }
    }
    
    // draw the outline around an image if it is currently selected
    private void drawOutline(int x, int y, int w, int h, int off) {
        this.image.fillRect(x,                                   y,                       w,             OUTLINE_WIDTH);
        this.image.fillRect(x,                                   y,                       OUTLINE_WIDTH, h);
        this.image.fillRect(x + off - OUTLINE_WIDTH - DIV_WIDTH, y,                       OUTLINE_WIDTH, h);
        this.image.fillRect(x,                                   y + h - OUTLINE_WIDTH + 1, w,             OUTLINE_WIDTH);
    }
    
    // resize an image to fit selection box
    private void resize(GreenfootImage img) {
        int width = calculateDivWidth(this.image, this.divs);
        img.scale(width - DIV_WIDTH, this.height);
    }
    
    // select one element to the left
    private int loopLeft() {
        this.curSelected -= 1;
        this.curSelected = this.curSelected < 0 ? this.divs - 1 : this.curSelected;
        int lastSelected = this.curSelected + 1 > this.divs - 1 ? 0 : this.curSelected + 1;
        setSelected(curSelected);
        setDeselected(lastSelected);
        this.actualSelected = curSelected;
        assignDivTexts();
        drawImages();
        return curSelected;
    }
    
    // select one element to the right
    private int loopRight() {
        this.curSelected += 1;
        this.curSelected = this.curSelected > this.divs - 1 ? 0 : this.curSelected;
        int lastSelected = this.curSelected - 1 < 0 ? this.divs - 1 : this.curSelected - 1;
        setSelected(curSelected);
        setDeselected(lastSelected);
        this.actualSelected = curSelected;
        assignDivTexts();
        drawImages();
        return curSelected;
    }
    
    // set background of selected section to the highlight colour
    private void setSelected(int div) {
        setDivColour(this.image, this.selectColour, div);
    }
    
    // set background of previous selected section back to its original colour
    private void setDeselected(int div) {
        setDivColour(this.image, this.bgColour, div);
    }
    
    // draw the background of the section with input colour and section index
    private void setDivColour(GreenfootImage img, Color selectColour, int div) {
        img.setColor(selectColour);
        int divWidth = calculateDivWidth(img, this.divs);
        int xOffSet = divWidth * div + DIV_WIDTH / 2;
        img.fillRect(xOffSet, 0, divWidth - DIV_WIDTH, img.getHeight());
    }
    
    // draw the dividing lines between sections
    private GreenfootImage drawDivisions(GreenfootImage img, Color divColour, int divisions) {
        int widthPerDivision = calculateDivWidth(img, divisions);
        img.setColor(divColour);
        for(int i = 0, n = divisions; i <= n; i++) {
            int xOffSet = widthPerDivision * i;
            img.fillRect(xOffSet - DIV_WIDTH / 2, 0, DIV_WIDTH, img.getHeight());
        }
        return img;
    }
    
    // draw the background rectangle
    private GreenfootImage drawBGRect(Color colour, int width, int height) {
        GreenfootImage img = new GreenfootImage(width,height);
        img.setColor(colour);
        img.fillRect(0,0,width,height);
        this.setImage(img);
        return img;
    }
    
    // linear search for string in string array and return -2 if not found
    private int getStringIndex(String[] s, String key) {
        for(int i = 0, n = s.length; i < n; i++) {
            if(s[i] == key) {
                return i;
            }
        }
        return -2;
    }
    
    // calculate the width of each division
    private int calculateDivWidth(GreenfootImage img, int divisions) {
        return img.getWidth() / divisions;
    }
}