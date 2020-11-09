import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * A simple, modular class that implements a customizable text box.
 * <p>
 * Can be used for dialogue, descriptions or as a button. Text boxes can
 * have unique border width, box height/width and unique colors.
 * <p>
 * Colors can be any valid Greenfoot Color instance. Height, width and 
 * border width can be any positive number, so inputting a negative 
 * number will just cause the instance variable to have the default 
 * value instead.
 * <p>
 * <strong>Implementation Notes -</strong>
 * <ul><li>
 * Use the newline character (\n) to represent multiple lines
 * </li><li>
 * Images can be used as the text box instead. Their size is based on
 * their width and height, but can be changed with the update() method
 * </li><li>
 * The class will automatically centers text on the y axis, but if the 
 * total height of the text exceeds the height of the text box, the text
 * will be cut off
 * </li><li>
 * To make text boxes appear before clicking run, make sure to use 
 * a constructor that takes x and y coordinates. Then use the update()
 * method to add the text and finally use the updateText() method to
 * display it on the world
 * </li></ul>
 * <p>
 * <strong>Potential Future Additions- (I plan to add on)</strong>
 * <ul><li>
 * Padding customization on x axis and y axis
 * </li><li>
 * Ability to add an array of strings, each element representing
 * a newline
 * </li><li>
 * More font customization (but may require more manual alignment, since
 * fonts have different sizes, assuming monospaced fonts are used)
 * </li><li>
 * Ability to use GreenfootImage shapes (ovals, etc) 
 * </li></ul>
 * 
 * @author Lucy Zhao 
 * @version 1.0.0
 */
public class LZTextBox extends Actor
{    
    // Instance variables
    private boolean useImage = false;
    private boolean displayingText = false;
    private boolean start = false;
    private boolean startCoords = false;
    
    private GreenfootImage textBox;
    private GreenfootImage textImg;
    
    private ArrayList<String> texts = new ArrayList<String>();
    
    // Related to dialog box itself
    private int borderWidth;
    private int boxWidth;
    private int boxHeight;
    private Color borderColor;
    private Color boxColor;
    private int x;
    private int y;
    private GreenfootImage image;
    
    // Related to the text
    private String text;
    private Color textColor;
    private String alignment;
    private String speaker;
    private int fontSize;
    
    /**
     * The default constructor for default text box.
     */
    public LZTextBox()
    {
        this(Color.BLACK, 10, "center");
    }
    
    /**
     * Displays the default text box before the scenario starts.
     * 
     * @param x     the x coordinate of the text box
     * @param y     the y coordinate of the text box
     */
    public LZTextBox(int x, int y)
    {
        this(x, y, Color.BLACK, 10, "center", 1, 200, 50, Color.BLACK,Color.WHITE);
    }
    
    /**
     * Add more customization to text, including color, font size.
     * 
     * @param textColor color of the message displayed
     * @param fontSize  font size of the message
     * @param aligment  the alignment of the text (left, right, center)
     */
    public LZTextBox(Color textColor, int fontSize, String alignment)
    {
        this(textColor, fontSize, alignment, 1, 200, 50);
    }
    
    /**
     * Add more customization to border width, plus width and height of
     * the text box.
     * 
     * @param fontSize    size of the font
     * @param borderWidth width of box border
     * @param width       width of text box
     * @param height      height of text box
     */
    public LZTextBox(int fontSize, int borderWidth, int width, int height)
    {
        this(Color.BLACK, fontSize, "center", borderWidth, width, height);
    }
    
    /**
     * Control the size of the text box, as well as the its border. 
     * Also controls all text customization.
     * 
     * @param textColor   color of the message displayed
     * @param fontSize    font size of the message
     * @param aligment    the alignment of the text
     * @param borderWidth width of box border
     * @param width       width of text box
     * @param height      height of text box
     */
    public LZTextBox(Color textColor, int fontSize, String alignment, int borderWidth, int width, int height)
    {
        this(textColor, fontSize, alignment, borderWidth, width, height, Color.BLACK, Color.WHITE); 
    }
    
    /**
     * Allows for a custom image background and allows specific 
     * coordinates, which means it will appear before run is clicked. 
     * 
     * @param x           the x coordinate of the text box
     * @param y           the y coordinate of the text box
     * @param textColor   color of the message displayed
     * @param fontSize    font size of the message
     * @param aligment    the alignment of the text
     * @param image       GreenfootImage of an image used
     */
    public LZTextBox(int x, int y, Color textColor, int fontSize, String alignment, GreenfootImage image)
    {
        this.x = x;
        this.y = y;
        this.textColor = textColor;
        this.alignment = alignment;
        this.fontSize = fontSize;
        
        this.image = image;
        boxWidth = image.getWidth();
        boxHeight = image.getHeight();
        
        useImage = true;
        startCoords = true;
        drawBox();
        checkTextList();
    }
    
    /**
     * Ultimate constructor that allows customization of text color,
     * font size, coordinates, as well as customization for the text box. 
     * 
     * @param x           the x coordinate of the text box
     * @param y           the y coordinate of the text box
     * @param textColor   color of the message displayed
     * @param fontSize    font size of the message
     * @param aligment    the alignment of the text
     * @param borderWidth width of box border
     * @param width       width of text box
     * @param height      height of text box
     * @param borderColor color of the text box border
     * @param boxColor    color of the text box
     */
    public LZTextBox(int x, int y, Color textColor, int fontSize, String alignment, int borderWidth, int width, int height, Color borderColor, Color boxColor)
    {
        // Uses default dimensions if negative values are added
        this.borderWidth = borderWidth > 0 ? borderWidth: 0;
        this.boxWidth =  width > 0 ? width: 200;
        this.boxHeight = height > 0 ? height: 50;
        
        this.borderColor = borderColor;
        this.boxColor = boxColor;
     
        this.textColor = textColor;
        this.alignment = alignment;
        this.fontSize = fontSize;
        
        startCoords = true;
        drawBox();
        checkTextList();
    }
    
    /**
     * Allows for a custom image background. The size of the text box 
     * is the same of the image chosen. However, height and width of
     * image can be changed with the update() method.
     * 
     * @param textColor   color of the message displayed
     * @param fontSize    font size of the message
     * @param aligment    the alignment of the text
     * @param image       GreenfootImage of an image used
     */
    public LZTextBox(Color textColor, int fontSize, String alignment, GreenfootImage image)
    {
        this.textColor = textColor;
        this.alignment = alignment;
        this.fontSize = fontSize;
        
        this.image = image;
        boxWidth = image.getWidth();
        boxHeight = image.getHeight();
        
        useImage = true;
    }
    
    /**
     * Ultimate constructor that allows customization of text color,
     * font size, as well as customization for the text box.
     * 
     * @param textColor   color of the message displayed
     * @param fontSize    font size of the message
     * @param aligment    the alignment of the text
     * @param borderWidth width of box border
     * @param width       width of text box
     * @param height      height of text box
     * @param borderColor color of the text box border
     * @param boxColor    color of the text box
     */
    public LZTextBox(Color textColor, int fontSize, String alignment, int borderWidth, int width, int height, Color borderColor, Color boxColor)
    {
        // Uses default dimensions if negative values are added
        this.borderWidth = borderWidth > 0 ? borderWidth: 0;
        this.boxWidth =  width > 0 ? width: 200;
        this.boxHeight = height > 0 ? height: 50;
        
        this.borderColor = borderColor;
        this.boxColor = boxColor;
     
        this.textColor = textColor;
        this.alignment = alignment;
        this.fontSize = fontSize;
    }
    
    /**
     * Act method for text box instances.
     */
    public void act() 
    {
        if (!start)
        {
            start = true;
            if (!startCoords)
            {
                drawBox();
                checkTextList();
            }
            else
                startCoords = false;
        }
    }    
    
    /**
     * Draws the text box.
     */
    private void drawBox()
    {
        if (!startCoords)
        {
            x = getX();
            y = getY();
        }
        if (!useImage)
        {
            textBox = new GreenfootImage(boxWidth+borderWidth*2, boxHeight+borderWidth*2);
            if (borderWidth > 0)
            {
                textBox.setColor(borderColor);
                textBox.fill();
                textBox.setColor(boxColor);
                textBox.fillRect(borderWidth, borderWidth, boxWidth, boxHeight);
            }
            else
            {
                textBox.setColor(boxColor);
                textBox.fill();
            }
        }
        else
        {
            textBox = new GreenfootImage(boxWidth, boxHeight);
            textBox.drawImage(image, 0, 0);
        }
        setImage(textBox);
    }
    
    /**
     * Prepares the next text to be display from the ArrayList.
     */
    private void checkTextList()
    {
        if (texts.size() > 0)
        {
            text = texts.get(0);
            texts.remove(0);
            drawText();
        }
    }
    
    /**
     * Draws the text onto the text box.
     */
    private void drawText()
    {
        textImg = new GreenfootImage(text, fontSize, textColor, null);
        // Don't bother looping through each line since it's draws it centered
        if (alignment == "center")
        {
            int centeredX = (boxWidth+(borderWidth*2))/2 - textImg.getWidth()/2;
            int centeredY = (boxHeight+(borderWidth*2))/2 - textImg.getHeight()/2;
            textBox.drawImage(textImg, centeredX, centeredY);
        }
        else // Have to loop to center it on the y axis
        {
            drawUncenterText();
        }
    }
    
    /**
     * Draws text that is not aligned to the center. Since drawString()
     * from the GreenfootImage class automatically centers each line,
     * this method seperates each line, calculates the proper height 
     * and draws each line. 
     */
    private void drawUncenterText()
    {
        // Split by newline characters
        String lines[] = text.split("\\r?\\n");
        textImg = new GreenfootImage(lines[0], fontSize, textColor, null);
        int paddingY = (boxHeight+(borderWidth*2))/2 - (textImg.getHeight()*lines.length)/2;
        int centeredX = (boxWidth+(borderWidth*2))/2 - textImg.getWidth()/2;
        for (String text : lines)
        {
            textImg = new GreenfootImage(text, fontSize, textColor, null);
            if (alignment == "right")
                textBox.drawImage(textImg, textBox.getWidth()-textImg.getWidth()-10, paddingY);
            else
                textBox.drawImage(textImg, 10, paddingY);
            paddingY += textImg.getHeight();
        }
    }
    
    /**
     * Changes the displayed text to the next one. Public method so 
     * that the user can update text with any key.
     */
    public void updateText()
    {
        if (texts.size() > 0)
        {
            drawBox();
            checkTextList();
        }
        else
        {
            displayingText = false;
            texts.clear();
            textBox.clear();
        }
    }
    
    /**
     * Updates the colors of the text box. If given a null value for
     * color, then the color will be the same as before.
     * 
     * @param boxColor      the new bg color for the text box
     * @param borderColor   the new color for the border
     * @param textColor     the new color for the text
     */
    public void update(Color boxColor, Color borderColor, Color textColor)
    {
        // Utilizes one line if else statements!
        this.boxColor = boxColor != null ? boxColor: this.boxColor;
        this.borderColor = borderColor != null ? borderColor: this.borderColor;
        this.textColor = textColor != null ? textColor: this.textColor;
        drawBox();
        drawText();
    }
    
    /**
     * Updates the location of the text box. Text box can be placed
     * anywhere, including outside the world.
     * 
     * @param x     the new x coordinate 
     * @param y     the new y coordinate
     */
    public void update(int x, int y)
    {
        setLocation(x, y);
        this.x = x;
        this.y = y;
    }
    
    /**
     * Updates size of the text box. If negative values are added, the
     * dimensions will not change at all.
     * 
     * @param boxWidth      new width of the text box
     * @param boxHeight     new height of the text box
     * @param borderWidth   new border width of the text box
     */
    public void update(int boxWidth, int boxHeight, int borderWidth)
    {
        if (boxWidth > 0 && boxHeight > 0 && borderWidth >= 0)
        {
            this.boxWidth = boxWidth;
            this.boxHeight = boxHeight;
            this.borderWidth = borderWidth;
            if (useImage)
            {
                image.scale(boxWidth, boxHeight);
            }
        }
        drawBox();
        drawText();
    }
    
    /**
     * Adds another line(s) of text to be displayed on the text box.
     * Use \n to represent a newline. 
     * 
     * @param text  string to be displayed
     */
    public void update(String text)
    {
        texts.add(text + " ");
        if (!displayingText && start)
        {
            start = false;
        }
        displayingText = true;
    }
    
    /**
     * Returns whether or not the text box is displaying.
     * 
     * @return boolean  true if displaying currently, else false
     */
    public boolean getDisplay()
    {
        return displayingText;
    }
}