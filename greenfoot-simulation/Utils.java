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
}
