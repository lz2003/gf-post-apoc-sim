/**
 * Write a description of class BackgroundData here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BackgroundData  
{
    private int xPos, yPos;
    private BackgroundSprite sprite;
    public BackgroundData(int x, int y) {
        xPos = x;
        yPos = y;
        sprite = new BackgroundSprite();
        WorldManagement.world.addObject(sprite, x, y);
    }
    
    public int getX() {
        return xPos;
    }
    
    public int getY() {
        return yPos;
    }
    
    public BackgroundSprite getSprite() {
        return sprite;
    }
}
