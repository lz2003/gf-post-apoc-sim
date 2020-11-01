/**
 * Write a description of class Tree here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tree  
{
    private Tree tree;
    private boolean chopped;
    private int xLoc, yLoc;
    private TreeSprite sprite;
    /**
     * Constructor for objects of class TreeData
     */
    public Tree(float seed)
    {
        xLoc = ((int) (seed * 1352)) % WorldManagement.WORLD_SIZE - (int)((float) WorldManagement.WORLD_SIZE * 0.2f);
        yLoc = ((int) (seed * 6112)) % WorldManagement.WORLD_SIZE - (int)((float) WorldManagement.WORLD_SIZE * 0.2f);
        sprite = new TreeSprite();
        WorldManagement.world.addObject(sprite, xLoc, yLoc);
    }

    public void chop() {
        if(chopped) return;
        WorldManagement.wood += ((float)(Math.random() * 2000) % 15) + 5;
        WorldManagement.trees.remove(this);
        WorldManagement.world.removeObject(sprite);
        chopped = true;
    }
    
    public TreeSprite getSprite() {
        return sprite;
    }
    
    public int getX() {
        return xLoc;
    }
    
    public int getY() {
        return yLoc;
    }
}
