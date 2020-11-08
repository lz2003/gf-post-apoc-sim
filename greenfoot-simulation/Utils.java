/**
 * Write a description of class Utils here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Utils  
{
    /**
     * Calculates the distance of two locations.
     * 
     * @param ax        the first x location
     * @param bx        the second x location
     * @param ay        the first y location
     * @param by        the second y location
     * @return int      the distance
     */
    public static int calcDist(int ax, int bx, int ay, int by) {
        return (int) Math.sqrt(Math.pow(ax - bx, 2) + Math.pow(ay - by, 2));
    }
    
    /**
     * Calculates the distance of two locations.
     * 
     * @param fromX       the x location to calculate from
     * @param toX         the x location to calculate the angle to
     * @param fromY       the y location to calculate from
     * @param toX         the y location to calculate the angle to
     * @return float      the angle in radians
     */
    public static float getAngleTo(int fromX, int toX, int fromY, int toY) {
        return (float) Math.atan2( (float) (toY - fromY), (float) (toX - fromX));
    }
}