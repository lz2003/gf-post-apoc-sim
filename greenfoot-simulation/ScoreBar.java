import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * A simple class that displays the data and statistics of the world.
 * 
 * @author Lucy Zhao 
 * @version 1.0
 */
public class ScoreBar extends Actor
{
    private GreenfootImage display;
    
    private Color bgColor;
    private Color fgColor;
    
    private Font wordFont;
    
    private int width;
    private int height;
    
    private ArrayList<String> stats;
    private ArrayList<String> textDisplay;
    
    public ScoreBar(int width, int height)
    {
        stats = new ArrayList<String>();
        textDisplay = new ArrayList<String>();
        this.width = width;
        this.height = height;
        display = new GreenfootImage(width, height);
        
        wordFont = new Font("Arial", 12);
        bgColor = new Color(0, 0 ,0);
        fgColor = new Color(255, 255, 255);
        
        this.setImage(display);
        display.setFont(wordFont);
    }
    
    /**
     * Act - do whatever the ScoreBar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }  
    
    /**
     * Adds a stat to be displayed on the scorebar.
     */
    public void addStat(String statName, int statValue)
    {
        stats.add(statName);
        textDisplay.add(statName + ": " + addZeroes(statValue) + statValue);
        drawStats();
    }
    
    /**
     * Updates a specified stat with the right value
     */
    public void updateStat(String statName, int statValue)
    {
        int index = stats.indexOf(statName);
        textDisplay.set(index, statName + ": " + addZeroes(statValue) + statValue);
        drawStats();
    }
    
    /**
     * Method that draws the stats onto the scorebar
     */
    private void drawStats()
    {
        display.setColor(bgColor);
        display.fill();
        display.setColor(fgColor);
        
        int xLoc = 0;
        int yLoc = height/2;
        String allStats = "";
        for (int i = 0; i < textDisplay.size(); i++)
        {
            allStats += " " + textDisplay.get(i) + " ";
        }
        display.drawString(allStats, xLoc, yLoc);
    }
    
    /**
     * Method that hides the scorebar.
     */
    public void hideStats(boolean hide)
    {
        if (hide == true)
        {
            display.setTransparency(0);
        }
        else
            display.setTransparency(255);
    }
    
    /** 
     * Method that returns the number of zeroes needed.
     */
    private String addZeroes(int value)
    {
        int digits = getDigits(value);
        // Eh not very modular, but we can make a numDigits variable
        if (digits > 4)
        {
            return "";
        }
        else
        {
            String zeroes = "";
            for (int i = 0; i < (5 - digits); i++)
            {
                zeroes += "0";
            }
            return zeroes;
        }
    }
    
    /** 
     * Method that calculates total digits of a number.
     */
    private int getDigits(int value)
    {
        int count = 1;
        while (value > 9)
        {
            value /= 10;
            count++;
        }
        return count;
    }
}
